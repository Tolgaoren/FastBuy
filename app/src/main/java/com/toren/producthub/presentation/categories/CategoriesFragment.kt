package com.toren.producthub.presentation.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.toren.producthub.data.remote.dto.category.Category
import com.toren.producthub.databinding.FragmentCategoriesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : Fragment(),
CategoryAdapter.OnItemClickListener{

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CategoriesViewModel by viewModels()
    private val categoryAdapter = CategoryAdapter(mutableListOf(), this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.categoryRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.categoryRecyclerView.adapter = categoryAdapter
        binding.categoryRecyclerView.setHasFixedSize(true)
        observeCategories()
    }

    private fun observeCategories() {
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            categoryAdapter.updateCategories(categories)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Category) {
        val action = CategoriesFragmentDirections.actionNavCategoriesToCategoryProductsFragment(position.name)
        findNavController().navigate(action)
    }
}