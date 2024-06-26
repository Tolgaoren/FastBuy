package com.toren.producthub.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.toren.producthub.R
import com.toren.producthub.databinding.FragmentHomeBinding
import com.toren.producthub.presentation.adapter.ProductAdapter
import com.toren.producthub.domain.model.Product
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(),
    ProductAdapter.OnItemClickListener,
    MenuProvider {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private val adapter = ProductAdapter(mutableListOf(),this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.apply {
            homeRecyclerView.layoutManager = GridLayoutManager(context, 2)
            homeRecyclerView.adapter = adapter
            homeRecyclerView.setHasFixedSize(true)
        }
        observeProducts()
        observeLikes()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getLikes()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.home, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        val products = viewModel.products.value?.data ?: emptyList()
        when (menuItem.itemId) {
            R.id.sortByNameAsc -> {
                adapter.updateProducts(products.sortedBy { it.title })
                return true
            }
            R.id.sortByNameDesc -> {
                adapter.updateProducts(products.sortedByDescending { it.title })
                return true
            }
            R.id.sortByPriceAsc -> {
                adapter.updateProducts(products.sortedBy { it.price })
                return true
            }
            R.id.sortByPriceDesc -> {
                adapter.updateProducts(products.sortedByDescending { it.price })
                return true
            }
            R.id.sortByRatingAsc -> {
                adapter.updateProducts(products.sortedByDescending { it.rating })
                return true
            }
            R.id.sortByDiscount -> {
                adapter.updateProducts(products.sortedByDescending { it.discountPercentage })
                return true
            }
            else -> return false
        }
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
        val action = HomeFragmentDirections.actionNavHomeToDetailFragment(position)
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