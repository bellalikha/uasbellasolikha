package com.example.uasbellasolikha.order

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uasbellasolikha.R
import com.example.uasbellasolikha.history.HistoryOrderActivity
import com.example.uasbellasolikha.model.FoodData
import com.example.uasbellasolikha.model.OrderModel
import com.google.android.material.button.MaterialButton
import java.text.NumberFormat
import java.util.Locale

class OrderActivity : AppCompatActivity() {

    private lateinit var rvOrder: RecyclerView
    private lateinit var tvTotalItems: TextView
    private lateinit var tvTotalPrice: TextView
    private lateinit var btnCheckout: MaterialButton
    private lateinit var toolbar: Toolbar

    private lateinit var orderViewModel: OrderViewModel
    private val listData = ArrayList<OrderModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        setStatusbar()
        initView()

        // Hidupkan Database
        orderViewModel = ViewModelProvider(this)[OrderViewModel::class.java]

        // --- BAGIAN INI YANG KEMARIN BELUM ADA/TERLEWAT ---
        val searchQuery = intent.getStringExtra("SEARCH_QUERY") // Cek ada kata kunci pencarian?
        val namaKategori = intent.getStringExtra("NAMA_MENU")   // Cek ada kategori?

        if (searchQuery != null) {
            // A. JIKA DIBUKA DARI HASIL SEARCH
            supportActionBar?.title = "Hasil: \"$searchQuery\""
            setupDataSearch(searchQuery) // Jalankan fungsi pencarian
        } else {
            // B. JIKA DIBUKA DARI KLIK KATEGORI BIASA
            supportActionBar?.title = namaKategori ?: "Menu Paket"
            setupDataFromFoodData(namaKategori ?: "Complete")
        }

        setupRecyclerView()
    }

    private fun initView() {
        toolbar = findViewById(R.id.toolbar)
        tvTotalItems = findViewById(R.id.tvJumlahPorsi)
        tvTotalPrice = findViewById(R.id.tvTotalPrice)
        btnCheckout = findViewById(R.id.btnCheckout)
        rvOrder = findViewById(R.id.rvOrder)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnCheckout.setOnClickListener {
            var adaPesanan = false
            for (model in listData) {
                if (model.quantity > 0) {
                    adaPesanan = true
                    orderViewModel.addDataOrder(model.name, model.quantity, model.price)
                }
            }

            if (adaPesanan) {
                Toast.makeText(this, "Pesanan Berhasil Disimpan!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HistoryOrderActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Ups, pilih minimal 1 menu dulu ya!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // --- FUNGSI PENCARIAN (BARU) ---
    private fun setupDataSearch(keyword: String) {
        listData.clear()

        val allFood = FoodData.getAllFood() // Ambil semua data makanan
        var ketemu = false

        for (food in allFood) {
            // Cek apakah nama makanan mengandung kata kunci (tidak peduli huruf besar/kecil)
            if (food.nama.contains(keyword, ignoreCase = true)) {

                // Bersihkan harga
                val hargaBersih = food.harga.replace("[^0-9]".toRegex(), "").toIntOrNull() ?: 0

                listData.add(OrderModel(
                    name = food.nama,
                    price = hargaBersih,
                    imageResId = food.imageResourceId
                ))
                ketemu = true
            }
        }

        if (!ketemu) {
            // Kalau tidak ketemu, tampilkan info kosong
            listData.add(OrderModel("Tidak ditemukan", 0, R.drawable.ic_launcher_foreground))
            Toast.makeText(this, "Menu '$keyword' tidak ditemukan 😔", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupDataFromFoodData(kategoriDipilih: String) {
        listData.clear()

        val keyCategory = when {
            kategoriDipilih.contains("Complete", ignoreCase = true) -> "complete"
            kategoriDipilih.contains("Saving", ignoreCase = true) -> "saving"
            kategoriDipilih.contains("Healthy", ignoreCase = true) -> "healty"
            kategoriDipilih.contains("Fast", ignoreCase = true) -> "fastfood"
            kategoriDipilih.contains("Event", ignoreCase = true) -> "event"
            else -> "others"
        }

        val allFood = FoodData.getAllFood()

        for (food in allFood) {
            if (food.category == keyCategory) {
                val hargaBersih = food.harga.replace("[^0-9]".toRegex(), "").toIntOrNull() ?: 0
                listData.add(OrderModel(
                    name = food.nama,
                    price = hargaBersih,
                    imageResId = food.imageResourceId
                ))
            }
        }

        if (listData.isEmpty()) {
            listData.add(OrderModel("Menu Belum Tersedia", 0, R.drawable.ic_launcher_foreground))
        }
    }

    private fun setupRecyclerView() {
        rvOrder.layoutManager = GridLayoutManager(this, 2)
        rvOrder.setHasFixedSize(true)

        val adapter = OrderAdapter(listData) {
            hitungTotalBelanja()
        }
        rvOrder.adapter = adapter
    }

    private fun hitungTotalBelanja() {
        var totalItem = 0
        var totalPrice = 0

        for (order in listData) {
            totalItem += order.quantity
            totalPrice += (order.price * order.quantity)
        }

        tvTotalItems.text = "$totalItem items"
        val localeID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        tvTotalPrice.text = formatRupiah.format(totalPrice.toDouble())
    }

    private fun setStatusbar() {
        if (Build.VERSION.SDK_INT >= 21) {
            window.statusBarColor = Color.WHITE
            if (Build.VERSION.SDK_INT >= 23) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}