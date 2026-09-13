package com.example.uasbellasolikha.history

import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uasbellasolikha.R
import com.example.uasbellasolikha.database.DatabaseModel
import java.util.ArrayList

class HistoryOrderActivity : AppCompatActivity() {

    private var modelDatabaseList: MutableList<DatabaseModel> = ArrayList()
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var rvHistory: RecyclerView
    private lateinit var tvNotFound: TextView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_order)

        setToolbar()
        setInitLayout()
        setViewModel()
        setSwipeToDelete()
    }

    private fun setToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setInitLayout() {
        rvHistory = findViewById(R.id.rvHistory)
        tvNotFound = findViewById(R.id.tvNotFound)

        tvNotFound.visibility = View.GONE

        // Listener edit tetap sama, memanggil showDialogEdit
        historyAdapter = HistoryAdapter(this, modelDatabaseList) { data ->
            showDialogEdit(data)
        }

        rvHistory.setHasFixedSize(true)
        rvHistory.layoutManager = LinearLayoutManager(this)
        rvHistory.adapter = historyAdapter
    }

    private fun setViewModel() {
        historyViewModel = ViewModelProvider(this)[HistoryViewModel::class.java]

        historyViewModel.getDataList().observe(this) { modelDatabases ->
            if (modelDatabases.isNotEmpty()) {
                historyAdapter.setDataAdapter(modelDatabases)
                rvHistory.visibility = View.VISIBLE
                tvNotFound.visibility = View.GONE
            } else {
                historyAdapter.setDataAdapter(emptyList())
                tvNotFound.visibility = View.VISIBLE
                rvHistory.visibility = View.GONE
            }
        }
    }

    // --- FUNGSI EDIT YANG SUDAH DIMODIFIKASI (LOGIKA BATAS EDIT) ---
    private fun showDialogEdit(data: DatabaseModel) {

        // 1. CEK KUOTA EDIT (Max 2x)
        if (data.editCount >= 2) {
            Toast.makeText(this, "Kesempatan edit (2x) sudah habis!", Toast.LENGTH_LONG).show()
            return // Stop, jangan buka dialog
        }

        // 2. CEK BATAS WAKTU (Max 5 Menit)
        val waktuSekarang = System.currentTimeMillis()
        val batasWaktu = 5 * 60 * 1000 // 5 menit dalam milidetik

        // Jika selisih waktu > 5 menit
        if (waktuSekarang - data.timestamp > batasWaktu) {
            Toast.makeText(this, "Waktu edit 5 menit sudah berlalu!", Toast.LENGTH_LONG).show()
            return // Stop, jangan buka dialog
        }

        // --- JIKA LOLOS PENGECEKAN, BUKA DIALOG ---
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Edit Pesanan")

        // Layout Container
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(50, 40, 50, 10)

        // --- TAMBAHAN: TEKS REMINDER ---
        val tvReminder = TextView(this)
        val sisaEdit = 2 - data.editCount
        tvReminder.text = "⚠️ Perhatian:\n• Anda hanya bisa edit pesanan sebelum 5 menit.\n• Sisa kesempatan edit: $sisaEdit kali."
        tvReminder.setTextColor(Color.RED) // Pastikan import android.graphics.Color
        tvReminder.textSize = 12f
        tvReminder.setPadding(0, 0, 0, 20) // Jarak ke bawah
        layout.addView(tvReminder)

        // Input Nama Menu
        val etNama = EditText(this)
        etNama.hint = "Nama Menu"
        etNama.setText(data.nama_menu)
        layout.addView(etNama)

        // Input Jumlah Items
        val etJumlah = EditText(this)
        etJumlah.hint = "Jumlah Items"
        etJumlah.inputType = InputType.TYPE_CLASS_NUMBER
        etJumlah.setText(data.jml_items.toString())
        layout.addView(etJumlah)

        builder.setView(layout)

        // Tombol Simpan
        builder.setPositiveButton("Simpan") { dialog, _ ->
            val namaBaru = etNama.text.toString()
            val jumlahBaruStr = etJumlah.text.toString()

            if (namaBaru.isNotEmpty() && jumlahBaruStr.isNotEmpty()) {
                // Update Data Object
                data.nama_menu = namaBaru
                data.jml_items = jumlahBaruStr.toInt()

                // PENTING: Tambah Counter Edit +1 setiap kali disimpan
                data.editCount = data.editCount + 1

                // Panggil ViewModel untuk Update ke Database
                historyViewModel.updateData(data)

                Toast.makeText(this, "Data berhasil diubah! (Edit ke-${data.editCount})", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Data tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Batal") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    private fun setSwipeToDelete() {
        val simpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START or ItemTouchHelper.END) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val databaseModel = historyAdapter.getData()[position]

                val alertDialogBuilder = AlertDialog.Builder(this@HistoryOrderActivity)
                alertDialogBuilder.setMessage("Hapus riwayat ini?")

                alertDialogBuilder.setPositiveButton("Ya, Hapus") { dialogInterface, i ->
                    val uid = databaseModel.uid
                    historyViewModel.deleteDataById(uid)
                    historyAdapter.setSwipeRemove(position)
                    Toast.makeText(this@HistoryOrderActivity, "Data yang dipilih sudah dihapus", Toast.LENGTH_SHORT).show()
                }

                alertDialogBuilder.setNegativeButton("Batal") { dialogInterface, i ->
                    historyAdapter.restoreItem(databaseModel, position)
                    rvHistory.scrollToPosition(position)
                    dialogInterface.cancel()
                }

                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(rvHistory)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}