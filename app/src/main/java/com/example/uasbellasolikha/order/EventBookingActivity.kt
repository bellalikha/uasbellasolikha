package com.example.uasbellasolikha.order

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.uasbellasolikha.R
import com.example.uasbellasolikha.history.HistoryOrderActivity
import com.google.android.material.button.MaterialButton

class EventBookingActivity : AppCompatActivity() {

    private lateinit var tvFileName: TextView
    private lateinit var btnUpload: MaterialButton
    private lateinit var btnKirim: MaterialButton
    private lateinit var etNama: EditText
    private lateinit var etTanggal: EditText
    private lateinit var etCatatan: EditText

    // 1. TAMBAHAN PENTING: Panggil OrderViewModel
    private lateinit var orderViewModel: OrderViewModel

    private var selectedFileUri: Uri? = null

    private val filePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            selectedFileUri = uri
            tvFileName.text = "File Terpilih: ${uri.lastPathSegment}"
            tvFileName.setTextColor(resources.getColor(android.R.color.holo_green_dark))
        } else {
            Toast.makeText(this, "Batal memilih file", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_booking)

        // 2. Hidupkan Mesin Database
        orderViewModel = ViewModelProvider(this)[OrderViewModel::class.java]

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvFileName = findViewById(R.id.tvFileName)
        btnUpload = findViewById(R.id.btnUpload)
        btnKirim = findViewById(R.id.btnKirimBooking)
        etNama = findViewById(R.id.etNama)
        etTanggal = findViewById(R.id.etTanggal)
        etCatatan = findViewById(R.id.etCatatan)

        btnUpload.setOnClickListener {
            filePickerLauncher.launch("*/*")
        }

        btnKirim.setOnClickListener {
            val nama = etNama.text.toString()
            val tanggal = etTanggal.text.toString()
            // val catatan = etCatatan.text.toString() // Jika ada

            if (nama.isEmpty() || tanggal.isEmpty()) {
                Toast.makeText(this, "Mohon lengkapi Nama & Tanggal!", Toast.LENGTH_SHORT).show()
            } else if (selectedFileUri == null) {
                Toast.makeText(this, "Wajib upload file konsep/menu!", Toast.LENGTH_SHORT).show()
            } else {

                // 1. Simpan ke Database
                val judulBooking = "Event: $nama ($tanggal)"
                orderViewModel.addDataOrder(judulBooking, 1, 0)

                // 2. Beri jeda sedikit (500ms) baru pindah halaman
                // Supaya database selesai menulis dulu
                android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({

                    Toast.makeText(this, "Booking Berhasil Tersimpan!", Toast.LENGTH_LONG).show()

                    val intent = Intent(this, HistoryOrderActivity::class.java)
                    // Hapus activity lama dari stack biar tombol back aman
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()

                }, 500) // Jeda 0.5 detik
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