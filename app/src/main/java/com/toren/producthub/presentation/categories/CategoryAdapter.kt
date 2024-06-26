package com.toren.producthub.presentation.categories

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.toren.producthub.data.remote.dto.category.Category
import com.toren.producthub.databinding.CategoryRowBinding

class CategoryAdapter(
    private val categoryList: MutableList<Category>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>()
{

    inner class CategoryViewHolder(val binding: CategoryRowBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(categoryList[position])
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.categoryTxtV.text = categoryList[position].name
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCategories(categories: List<Category>) {
        categoryList.clear()
        println("newProducts: $categories")
        categoryList.addAll(categories)
        notifyDataSetChanged()
    }
}