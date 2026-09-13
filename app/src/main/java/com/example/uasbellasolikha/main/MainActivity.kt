package com.example.uasbellasolikha.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uasbellasolikha.R
import com.example.uasbellasolikha.history.HistoryOrderActivity
import com.example.uasbellasolikha.order.EventBookingActivity
import com.example.uasbellasolikha.order.OrderActivity
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var rvCategories: RecyclerView
    private lateinit var rvTrending: RecyclerView
    private lateinit var cvHistory: CardView
    private lateinit var cvLocation: CardView

    private var listCategories: ArrayList<ModelCategories> = ArrayList()
    private var listTrending: ArrayList<ModelTrending> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvCategories = findViewById(R.id.rvCategories)
        rvTrending = findViewById(R.id.rvTrending)
        cvHistory = findViewById(R.id.cvHistory)
        cvLocation = findViewById(R.id.cvLocation)

        val cvProfile = findViewById<CardView>(R.id.cvProfile)
        cvProfile.setOnClickListener {
            // Pindah ke Halaman ProfileActivity
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        val etSearch = findViewById<EditText>(R.id.etSearch)

        etSearch.setOnEditorActionListener { v, actionId, event ->

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                val keyword = etSearch.text.toString().trim()

                if (keyword.isNotEmpty()) {
                    // Kirim kata kunci ke OrderActivity
                    val intent = Intent(this, OrderActivity::class.java)
                    intent.putExtra("SEARCH_QUERY", keyword)
                    startActivity(intent)
                }
                return@setOnEditorActionListener true
            }
            false
        }

        cvHistory.setOnClickListener {
            try {
                startActivity(Intent(this, HistoryOrderActivity::class.java))
            } catch (e: Exception) {
                Toast.makeText(this, "Halaman History belum siap!", Toast.LENGTH_SHORT).show()
            }
        }

        cvLocation.setOnClickListener {
            val lokasi = "Universitas PGRI Wiranegara" // Lokasi Kampus

            val gmmIntentUri = Uri.parse("geo:0,0?q=$lokasi")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")

            try {
                startActivity(mapIntent)
            } catch (e: Exception) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=$lokasi")))
            }
        }

        setupCategories()
        setupTrending()
    }

    private fun setupCategories() {
        rvCategories.layoutManager = GridLayoutManager(this, 3)
        rvCategories.setHasFixedSize(true)

        listCategories.clear()
        listCategories.add(ModelCategories(R.drawable.ic_complete, "Complete Package"))
        listCategories.add(ModelCategories(R.drawable.ic_saving, "Saving Package"))
        listCategories.add(ModelCategories(R.drawable.ic_healthy, "Healthy Package"))
        listCategories.add(ModelCategories(R.drawable.ic_fast, "Fast Food"))
        listCategories.add(ModelCategories(R.drawable.ic_event, "Event"))
        listCategories.add(ModelCategories(R.drawable.ic_more_food, "Others"))

        rvCategories.adapter = AdapterKategoriInternal(listCategories)
    }

    private fun setupTrending() {
        rvTrending.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvTrending.setHasFixedSize(true)

        listTrending.clear()
        // Pastikan nama file gambar (R.drawable.xxx) sesuai dengan yang ada di project kamu
        listTrending.add(ModelTrending(R.drawable.paket_4, "Ayam Serundeng", "200 disukai"))
        listTrending.add(ModelTrending(R.drawable.paket_4h, "Meatballs & Mashed Potato", "150 disukai"))
        listTrending.add(ModelTrending(R.drawable.paket_6, "Nasi Goreng", "110 disukai"))

        rvTrending.adapter = TrendingAdapter(this, listTrending)
    }

    inner class AdapterKategoriInternal(private val dataList: ArrayList<ModelCategories>) :
        RecyclerView.Adapter<AdapterKategoriInternal.Holder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_categories, parent, false)
            return Holder(view)
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            val item = dataList[position]
            holder.imgIcon.setImageResource(item.icon)
            holder.tvName.text = item.strName

            holder.itemView.setOnClickListener {
                if (item.strName == "Event") {
                    // Masuk ke Booking Event
                    val intent = Intent(holder.itemView.context, EventBookingActivity::class.java)
                    holder.itemView.context.startActivity(intent)

                } else if (item.strName == "Others") {
                    // Fitur belum ada -> Masuk ke FeatureUnavailableActivity
                    val intent = Intent(holder.itemView.context, FeatureUnavailableActivity::class.java)
                    holder.itemView.context.startActivity(intent)

                } else {
                    // Masuk ke Order Makanan (Complete, Healthy, dll)
                    val intent = Intent(holder.itemView.context, OrderActivity::class.java)
                    intent.putExtra("NAMA_MENU", item.strName)
                    holder.itemView.context.startActivity(intent)
                }
            }
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imgIcon: ImageView = itemView.findViewById(R.id.imageIcon)
            val tvName: TextView = itemView.findViewById(R.id.tvName)
        }
    }
}