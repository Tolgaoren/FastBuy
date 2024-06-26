package com.toren.producthub.presentation.search

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.toren.producthub.data.local.entity.SearchHistory
import com.toren.producthub.databinding.FragmentSearchBinding
import com.toren.producthub.domain.model.Product
import com.toren.producthub.presentation.adapter.ProductAdapterHorizontal
import com.toren.producthub.presentation.adapter.TextAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(),
    TextAdapter.OnItemClickListener,
    ProductAdapterHorizontal.OnItemClickListener{

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    private val textAdapter = TextAdapter(mutableListOf(), this)
    private val searchHistoryAdapter = ProductAdapterHorizontal(mutableListOf(), this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchHistoryRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = textAdapter
            setHasFixedSize(true)
        }
        binding.visitHistoryRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = searchHistoryAdapter
            setHasFixedSize(true)
        }

        observeSearchHistory()
        observeVisitHistory()

        binding.searchView.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = v.text.toString()
                viewModel.addSearchHistory(query)
                actionSearch(query)
                true
            } else false
        }
        
        binding.searchView.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableRight: Drawable? = binding.searchView.compoundDrawables[2]
                if (drawableRight != null && event.rawX >= (binding.searchView.right - drawableRight.bounds.width())) {
                    val query = binding.searchView.text.toString()
                    viewModel.addSearchHistory(query)
                    actionSearch(query)
                    true
                } else {
                    false
                }
            } else {
                false
            }
        }
    }


    private fun observeSearchHistory() {
        viewModel.searchHistory.observe(viewLifecycleOwner) { searchHistory ->
            textAdapter.updateTexts(searchHistory.data ?: emptyList())
        }
    }

    private fun observeVisitHistory() {
        viewModel.products.observe(viewLifecycleOwner) { products ->
            searchHistoryAdapter.updateProducts(products.data ?: emptyList())
        }
    }

    private fun actionSearch(query: String) {
        val action = SearchFragmentDirections.actionNavSearchToSearchResultFragment(query)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: SearchHistory) {
        binding.searchView.setText(position.searchQuery)
    }

    override fun onDeleteClick(position: SearchHistory) {
        viewModel.deleteSearchHistory(position)
    }

    override fun onItemClick(position: Product) {
        val action = SearchFragmentDirections.actionNavSearchToDetailFragment(position)
        findNavController().navigate(action)
    }
}