package com.toren.producthub.presentation.detail

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.toren.producthub.R
import com.toren.producthub.databinding.FragmentDetailBinding
import com.toren.producthub.domain.model.AddCart
import com.toren.producthub.presentation.adapter.ReviewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()
    private val reviewAdapter = ReviewAdapter(mutableListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.detailReviewsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = reviewAdapter
            setHasFixedSize(true)
        }

        args.Product.let {
            viewModel.setProduct(it)
            viewModel.getLike(productId = it.id)
            loadDetails()
            observeLikeState()
            viewModel.getLike(productId = it.id)
        }

        binding.floatingActionButton.setOnClickListener {
            viewModel.likeBtnClicked(productId = args.Product.id)
        }

        binding.detailBuyBtn.setOnClickListener {
            val product = viewModel.product.value
            actionOrder(
                AddCart(
                    id = product?.id ?: 0,
                    quantity = 1
                )
            )
        }
    }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    private fun loadDetails() {
        viewModel.product.observe(viewLifecycleOwner) {
            binding.apply {
                (activity as? AppCompatActivity)?.supportActionBar?.title = it.title
                Glide.with(requireContext())
                    .load(it.images[0])
                    .into(detailImgV)
                detailTitleTxtV.text = it.title
                detailBrandTxtV.text = it.brand
                detailStockTxtV.text = it.availabilityStatus
                detailRatingTxtV.text = it.rating.toString()
                detailRatingBar.rating = it.rating.toFloat()
                reviewAdapter.updateReviews(it.reviews)
                shippingTxtV.text = it.shippingInformation
                warrantyTxtV.text = it.warrantyInformation
                returnPolicyTxtV.text = it.returnPolicy
                if (it.discountPercentage > 1.0) {
                    detailPriceTxtV.text = String.format("%.2f", it.price - (it.discountPercentage * it.price / 100)) + " $"
                    detailPriceTxtV.setTextColor(resources.getColor(R.color.discount_color, null))
                    detailDiscountPercentageTxtV.visibility = View.VISIBLE
                    detailDiscountPercentageTxtV.text = "-${it.discountPercentage.toInt()}%"
                    detailPriceWithoutDiscountTxtV.visibility = View.VISIBLE
                    detailPriceWithoutDiscountTxtV.text = "${it.price} $"
                    detailPriceWithoutDiscountTxtV.paintFlags = detailPriceWithoutDiscountTxtV.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    detailPriceWithoutDiscountTxtV.visibility = View.GONE
                    detailDiscountPercentageTxtV.visibility = View.GONE
                    detailPriceTxtV.text = "${it.price} $"
                }

                when (it.availabilityStatus) {
                    "In Stock" -> {
                        detailStockTxtV.setTextColor(
                            resources.getColor(
                                R.color.in_stock_text,
                                null
                            )
                        )
                        stockCardView.setCardBackgroundColor(
                            resources.getColor(
                                R.color.in_stock_bg,
                                null
                            )
                        )
                    }

                    "Low Stock" -> {
                        detailStockTxtV.setTextColor(
                            resources.getColor(
                                R.color.low_stock_text,
                                null
                            )
                        )
                        stockCardView.setCardBackgroundColor(
                            resources.getColor(
                                R.color.low_stock_bg,
                                null
                            )
                        )
                    }
                }
            }
        }
    }

    private fun observeLikeState() {
        viewModel.like.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.floatingActionButton.setImageResource(R.drawable.ic_like)
                binding.floatingActionButton.imageTintList =
                    resources.getColorStateList(R.color.like_color, null)
            } else {
                binding.floatingActionButton.setImageResource(R.drawable.ic_unlike)
                binding.floatingActionButton.imageTintList =
                    resources.getColorStateList(R.color.grey_900, null)
            }
        }
    }

    private fun actionOrder(cart: AddCart) {
        val action = DetailFragmentDirections.actionDetailFragmentToOrderReceiveFragment(cart)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}