package com.example.uasbellasolikha.history

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uasbellasolikha.R
import com.example.uasbellasolikha.database.DatabaseModel
import com.example.uasbellasolikha.utils.FunctionHelper

class HistoryAdapter(
    var mContext: Context,
    var modelDatabase: MutableList<DatabaseModel>,
    var onEdit: (DatabaseModel) -> Unit // Listener untuk menangkap klik tombol edit
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    fun setDataAdapter(items: List<DatabaseModel>) {
        modelDatabase.clear()
        modelDatabase.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_riwayat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = modelDatabase[position]

        // 1. Set Nama
        holder.tvNama.text = data.nama_menu

        // 2. Set Tanggal
        holder.tvDate.text = FunctionHelper.getToday()

        // 3. Set Jumlah Item
        holder.tvJml.text = "${data.jml_items} Items"

        // LOGIKA UTAMA: CEK APAKAH INI EVENT?
        if (data.nama_menu?.startsWith("Event:", ignoreCase = true) == true) {
            holder.tvPrice.visibility = View.GONE
        } else {
            holder.tvPrice.visibility = View.VISIBLE
            val totalHarga = data.harga * data.jml_items
            holder.tvPrice.text = FunctionHelper.rupiahFormat(totalHarga)
        }

        // 4. LOGIKA KLIK TOMBOL EDIT (PENTING)
        holder.btnEdit.setOnClickListener {
            // Memanggil fungsi 'showDialogEdit' yang ada di Activity
            onEdit(data)
        }
    }

    override fun getItemCount(): Int {
        return modelDatabase.size
    }

    fun getData(): MutableList<DatabaseModel> {
        return modelDatabase
    }

    fun setSwipeRemove(position: Int) {
        modelDatabase.removeAt(position)
        notifyItemRemoved(position)
    }

    fun restoreItem(databaseModel: DatabaseModel, position: Int) {
        modelDatabase.add(position, databaseModel)
        notifyItemInserted(position)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNama: TextView = itemView.findViewById(R.id.tvNama)
        var tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        var tvJml: TextView = itemView.findViewById(R.id.tvJml)
        var tvDate: TextView = itemView.findViewById(R.id.tvDate)

        // Ini kunci agar tidak Crash: ID harus sama dengan di list_item_riwayat.xml
        var btnEdit: ImageView = itemView.findViewById(R.id.btnEdit)
    }
}