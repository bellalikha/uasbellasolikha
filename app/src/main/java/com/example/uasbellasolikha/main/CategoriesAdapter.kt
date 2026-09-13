package com.example.uasbellasolikha.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uasbellasolikha.R

class CategoriesAdapter(
    private val context: Context,
    private val modelCategoriesList: List<ModelCategories>
) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_categories, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageIcon.setImageResource(R.drawable.ic_launcher_foreground)
        holder.tvName.text = "TESTING"
    }

    override fun getItemCount(): Int {
        return 6
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageIcon: ImageView = itemView.findViewById(R.id.imageIcon)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
    }
}