package com.toren.producthub.presentation.likes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.toren.producthub.databinding.FragmentLikesBinding
import com.toren.producthub.domain.model.Product
import com.toren.producthub.presentation.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LikesFragment : Fragment(),
ProductAdapter.OnItemClickListener{

    private var _binding : FragmentLikesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LikesViewModel by viewModels()
    private val productAdapter = ProductAdapter(mutableListOf(), this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLikesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.likesRv.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = productAdapter
            setHasFixedSize(true)
        }
        observeProducts()
        observeLikes()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getLikes()
    }

    private fun observeProducts() {
        viewModel.products.observe(viewLifecycleOwner) { products ->
            productAdapter.updateProducts(products.data ?: emptyList())
        }
    }

    private fun observeLikes() {
        viewModel.likes.observe(viewLifecycleOwner) { likes ->
            productAdapter.updateLikedItems(likes.data?.map { it.productId } ?: emptyList())
        }
    }

    override fun onItemClick(position: Product) {
        val action = LikesFragmentDirections.actionNavLikesToDetailFragment(position)
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