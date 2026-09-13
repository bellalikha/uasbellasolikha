package com.example.uasbellasolikha.order

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.uasbellasolikha.database.AppDatabase
import com.example.uasbellasolikha.database.DatabaseModel
import com.example.uasbellasolikha.database.dao.DatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope // Wajib Import Ini
import kotlinx.coroutines.launch

class OrderViewModel(application: Application) : AndroidViewModel(application) {

    private val databaseDao: DatabaseDao

    init {
        val db = AppDatabase.getDatabase(application)
        databaseDao = db.databaseDao()
    }

    // Fungsi ini dipakai EventBookingActivity & OrderActivity
    fun addDataOrder(strMenu: String, strJmlItems: Int, strHarga: Int) {

        // PENTING: Gunakan GlobalScope.launch (bukan viewModelScope)
        // Agar proses simpan TETAP HIDUP walaupun Activity sudah finish/mati
        GlobalScope.launch(Dispatchers.IO) {

            val databaseModel = DatabaseModel()
            databaseModel.nama_menu = strMenu
            databaseModel.jml_items = strJmlItems
            databaseModel.harga = strHarga

            databaseDao.insertData(databaseModel)

            fun updateDataOrder(model: DatabaseModel) {
                GlobalScope.launch(Dispatchers.IO) {
                    databaseDao.updateData(model)
                }
            }
        }
    }
}