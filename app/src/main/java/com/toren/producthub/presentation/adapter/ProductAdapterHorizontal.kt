package com.toren.producthub.presentation.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.toren.producthub.R
import com.toren.producthub.databinding.ProductRowHorizontalBinding
import com.toren.producthub.domain.model.Product

class ProductAdapterHorizontal(
    private val productList: MutableList<Product>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ProductAdapterHorizontal.ProductViewHolder>()
{

    inner class ProductViewHolder(val binding: ProductRowHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(productList[position])
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductRowHorizontalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(productList[position].images[0])
            .into(holder.binding.productImgV)
        holder.binding.apply {
            productNameTxtV.text = productList[position].title
            productBrandTxtV.text = productList[position].brand
            if (productList[position].discountPercentage > 1.0) {
                val value = productList[position].price - (productList[position].discountPercentage * productList[position].price / 100)
                productPriceTxtV.text = String.format("%.2f", value) + " $"
                productPriceTxtV.setTextColor(holder.itemView.resources.getColor(R.color.discount_color, null))
                productPriceWithoutDiscountTxtV.visibility = View.VISIBLE
                productDiscountPercentageTxtV.visibility = View.VISIBLE
                productDiscountPercentageTxtV.text = "-${productList[position].discountPercentage.toInt()}%"
                productPriceWithoutDiscountTxtV.text = "${productList[position].price} $"
                productPriceWithoutDiscountTxtV.paintFlags = productPriceWithoutDiscountTxtV.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            } else {
                productPriceWithoutDiscountTxtV.visibility = View.GONE
                productDiscountPercentageTxtV.visibility = View.INVISIBLE
                productPriceTxtV.text = "${productList[position].price} $"
            }
            productRatingBar.rating = productList[position].rating.toFloat()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateProducts(newProducts: List<Product>) {
        productList.clear()
        productList.addAll(newProducts)
        notifyDataSetChanged()
    }
}