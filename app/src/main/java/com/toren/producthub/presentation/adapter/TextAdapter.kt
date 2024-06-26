package com.toren.producthub.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.toren.producthub.data.local.entity.SearchHistory
import com.toren.producthub.databinding.TextRowBinding

class TextAdapter(
    private val textList: MutableList<SearchHistory>,
    private val listener: OnItemClickListener
): RecyclerView.Adapter<TextAdapter.TextViewHolder>() {

    inner class TextViewHolder(val binding: TextRowBinding) :
            RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

            init {
                binding.root.setOnClickListener(this)
                binding.textRowHistoryDeleteImg.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(textList[position])
                        textList.removeAt(position)
                        notifyItemRemoved(position)
                    }
                }
            }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(textList[position])
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: SearchHistory)
        fun onDeleteClick(position: SearchHistory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val binding = TextRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TextViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return textList.size
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        holder.binding.apply {
            textRowTxtV.text = textList[position].searchQuery
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateTexts(newTexts: List<SearchHistory>) {
        textList.clear()
        textList.addAll(newTexts)
        notifyDataSetChanged()
    }
}