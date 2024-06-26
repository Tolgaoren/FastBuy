package com.toren.producthub.presentation.category_products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.toren.producthub.databinding.FragmentCategoryProductsBinding
import com.toren.producthub.domain.model.Product
import com.toren.producthub.presentation.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryProductsFragment : Fragment(),
ProductAdapter.OnItemClickListener{

    private var _binding: FragmentCategoryProductsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CategoryProductsViewModel by viewModels()
    private val args: CategoryProductsFragmentArgs by navArgs()
    private val adapter = ProductAdapter(mutableListOf(), this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCategoryProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.categoryProductRw.layoutManager = GridLayoutManager(context, 2)
        binding.categoryProductRw.adapter = adapter
        binding.categoryProductRw.setHasFixedSize(true)

        observeProducts()
        observeLikes()

        args.category.let {
            viewModel.getProducts(it)
            (activity as? AppCompatActivity)?.supportActionBar?.title = it
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getLikes()
    }

    private fun observeProducts() {
        viewModel.products.observe(viewLifecycleOwner) { products ->
            adapter.updateProducts(products.data ?: emptyList())
        }
    }

    private fun observeLikes() {
        viewModel.likes.observe(viewLifecycleOwner) { likes ->
            adapter.updateLikedItems(likes.data?.map { it.productId } ?: emptyList())
        }
    }

    override fun onItemClick(position: Product) {
        val action = CategoryProductsFragmentDirections.actionCategoryProductsFragmentToDetailFragment(position)
        findNavController().navigate(action)
    }

    override fun onLikeClick(position: Product) {
        viewModel.likeBtnClicked(position.id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

}