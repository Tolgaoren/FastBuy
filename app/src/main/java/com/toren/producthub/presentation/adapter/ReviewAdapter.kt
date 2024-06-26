package com.toren.producthub.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.toren.producthub.databinding.ReviewRowBinding
import com.toren.producthub.domain.model.Review

class ReviewAdapter(
    private val reviewList: MutableList<Review>
): RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(val binding: ReviewRowBinding) :
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ReviewRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReviewViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.binding.apply {
            reviewerNameTxtV.text = reviewList[position].reviewerName
            reviewCommentTxtV.text = reviewList[position].comment
            reviewRatingTxtV.text = reviewList[position].rating.toString()
            reviewDateTxtV.text = reviewList[position].date
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateReviews(newReviews: List<Review>) {
        reviewList.clear()
        reviewList.addAll(newReviews)
        notifyDataSetChanged()
    }
}