package com.example.uasbellasolikha.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uasbellasolikha.R
import com.example.uasbellasolikha.model.OrderModel
import java.text.NumberFormat
import java.util.Locale

// Kita butuh callback (updateTotal) agar Activity tahu kalau harga berubah
class OrderAdapter(
    private val listOrder: ArrayList<OrderModel>,
    private val updateTotal: () -> Unit
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvMenuName)
        val tvPrice: TextView = view.findViewById(R.id.tvMenuPrice)
        val tvQty: TextView = view.findViewById(R.id.tvQuantity)
        val imgMenu: ImageView = view.findViewById(R.id.imgMenu)
        val imgMinus: ImageView = view.findViewById(R.id.imgMinus)
        val imgAdd: ImageView = view.findViewById(R.id.imgAdd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order_row, parent, false)
        return OrderViewHolder(view)
    }

    override fun getItemCount(): Int = listOrder.size

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val item = listOrder[position]

        holder.tvName.text = item.name
        holder.tvQty.text = item.quantity.toString()
        holder.imgMenu.setImageResource(item.imageResId)

        // Format Rupiah
        val localeID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        holder.tvPrice.text = formatRupiah.format(item.price.toDouble())

        // LOGIKA TOMBOL TAMBAH
        holder.imgAdd.setOnClickListener {
            item.quantity++ // Tambah jumlah di data
            holder.tvQty.text = item.quantity.toString() // Update teks angka
            updateTotal() // Lapor ke Activity buat hitung total harga
        }

        // LOGIKA TOMBOL KURANG
        holder.imgMinus.setOnClickListener {
            if (item.quantity > 0) {
                item.quantity--
                holder.tvQty.text = item.quantity.toString()
                updateTotal()
            }
        }
    }
}