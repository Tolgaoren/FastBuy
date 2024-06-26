package com.toren.producthub.presentation.search_result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.toren.producthub.databinding.FragmentSearchResultBinding
import com.toren.producthub.domain.model.Product
import com.toren.producthub.presentation.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultFragment : Fragment(),
ProductAdapter.OnItemClickListener{

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchResultViewModel by viewModels()
    private val args: SearchResultFragmentArgs by navArgs()
    private val searchResultAdapter = ProductAdapter(mutableListOf(), this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchResultRv.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = searchResultAdapter
            setHasFixedSize(true)
        }

        observeProducts()
        observeLikes()
        args.query.let {
            (activity as? AppCompatActivity)?.supportActionBar?.title = it
            viewModel.searchProducts(it)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getLikes()
    }

    private fun observeProducts() {
        viewModel.searchProducts.observe(viewLifecycleOwner) { products ->
            searchResultAdapter.updateProducts(products ?: emptyList())
        }
    }

    private fun observeLikes() {
        viewModel.likes.observe(viewLifecycleOwner) { likes ->
            searchResultAdapter.updateLikedItems(likes.data?.map { it.productId } ?: emptyList())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Product) {
        val action = SearchResultFragmentDirections.actionSearchResultFragmentToDetailFragment(position)
        findNavController().navigate(action)
    }

    override fun onLikeClick(position: Product) {
        viewModel.likeBtnClicked(position.id)
    }
}