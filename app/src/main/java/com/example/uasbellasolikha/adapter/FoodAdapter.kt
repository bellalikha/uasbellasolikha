package com.example.uasbellasolikha.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uasbellasolikha.R
import com.example.uasbellasolikha.model.FoodModel

class FoodAdapter(
    private val foodList: List<FoodModel>
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgFood: ImageView = itemView.findViewById(R.id.imgMenu)
        val tvName: TextView = itemView.findViewById(R.id.tvMenuName)
        val tvPrice: TextView = itemView.findViewById(R.id.tvMenuPrice)
        val tvQty: TextView = itemView.findViewById(R.id.tvQuantity)
        val btnPlus: ImageView = itemView.findViewById(R.id.imgAdd)
        val btnMinus: ImageView = itemView.findViewById(R.id.imgMinus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_row, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foodList[position]
        var quantity = 0

        holder.imgFood.setImageResource(food.imageResourceId)
        holder.tvName.text = food.nama       // Menggunakan nama
        holder.tvPrice.text = food.harga     // Menggunakan harga

        holder.tvQty.text = quantity.toString()

        holder.btnPlus.setOnClickListener {
            quantity++
            holder.tvQty.text = quantity.toString()
        }

        holder.btnMinus.setOnClickListener {
            if (quantity > 0) {
                quantity--
                holder.tvQty.text = quantity.toString()
            }
        }
    }

    override fun getItemCount(): Int = foodList.size
}