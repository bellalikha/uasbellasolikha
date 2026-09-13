package com.example.uasbellasolikha.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uasbellasolikha.R

class TrendingAdapter(
    private val context: Context,
    private val modelTrendingList: List<ModelTrending>
) : RecyclerView.Adapter<TrendingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_trending, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = modelTrendingList[position]
        holder.imgThumb.setImageResource(data.imgTrending) // data.imgTrending
        holder.tvPlaceName.text = data.tvTitle             // data.tvTitle
        holder.tvVote.text = data.tvSuka                   // data.tvSuka
    }

    override fun getItemCount(): Int {
        return modelTrendingList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPlaceName: TextView = itemView.findViewById(R.id.tvPlaceName)
        val tvVote: TextView = itemView.findViewById(R.id.tvVote)
        val imgThumb: ImageView = itemView.findViewById(R.id.imgThumb)
    }
}