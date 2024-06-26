package com.toren.producthub.presentation.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.toren.producthub.data.remote.dto.cart.ProductDto
import com.toren.producthub.databinding.OrderRowBinding

class OrderAdapter(
    private val orderList: MutableList<ProductDto>
): RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(val binding: OrderRowBinding) :
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = OrderRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OrderViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(orderList[position].thumbnail)
            .into(holder.binding.orderRowImg)

        holder.binding.apply {
            orderRowTitleTxt.text = orderList[position].title
            orderRowQuantityTxt.text = "${orderList[position].quantity} pieces"

            if (orderList[position].discountPercentage > 1.0) {
                orderRowPriceTxt.text = "${String.format("%.2f", orderList[position].discountedTotal)}$"
                orderRowProfitTxt.visibility = View.VISIBLE
                orderRowProfitTxt.text = "Your profit: ${String.format("%.2f", orderList[position].total - orderList[position].discountedTotal)}$"
                orderRowNonDiscountPriceTxt.visibility = View.VISIBLE
                orderRowNonDiscountPriceTxt.text = "$${String.format("%.2f", orderList[position].total)}$"
                orderRowNonDiscountPriceTxt.paintFlags = orderRowNonDiscountPriceTxt.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                orderRowPriceTxt.text = "${String.format("%.2f", orderList[position].total)}$"
                orderRowNonDiscountPriceTxt.visibility = View.INVISIBLE
                orderRowProfitTxt.visibility = View.INVISIBLE
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateOrders(newOrders: List<ProductDto>) {
        orderList.clear()
        orderList.addAll(newOrders)
        notifyDataSetChanged()
    }
}