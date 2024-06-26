package com.toren.producthub.presentation.order_receive

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.toren.producthub.databinding.FragmentOrderReceiveBinding
import com.toren.producthub.utils.CreditCartNumberFormatter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderReceiveFragment : Fragment() {

    private var _binding: FragmentOrderReceiveBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OrderReceiveViewModel by viewModels()
    private val args: OrderReceiveFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentOrderReceiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUserInfo()
        args.cart.let {
            println(it)
            observeCart()
            viewModel.addCart(it)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeUserInfo() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            user.data?.let {
                binding.apply {
                    orderAddressTxtV.text = "${it.address.address} ${it.address.city}, ${it.address.state} ${it.address.postalCode}, ${it.address.country}"
                    orderCreditCardNumberTxtV.text = CreditCartNumberFormatter().formatCreditCardNumber(it.bank.cardNumber)
                    orderEmailTxtV.text = it.email
                    orderPhoneNumberTxtV.text = it.phone
                }
            }
        }
    }



    @SuppressLint("DefaultLocale", "SetTextI18n")
    private fun observeCart() {
        viewModel.addCart.observe(viewLifecycleOwner) { cart ->
            cart.let {
                binding.apply {
                    (activity as? AppCompatActivity)?.supportActionBar?.title =
                        cart.products[0].title
                    orderTitleTxt.text = cart.products[0].title
                    Glide.with(requireContext())
                        .load(cart.products[0].thumbnail)
                        .into(orderThumbnailImg)
                    orderPriceTxt.text = "Total: ${String.format("%.2f", cart.discountedTotal)} $"
                    orderIdTxt.text = "Order Id: ${cart.id}"
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}