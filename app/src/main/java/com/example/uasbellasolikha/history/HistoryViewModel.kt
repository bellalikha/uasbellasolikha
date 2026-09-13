package com.example.uasbellasolikha.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.uasbellasolikha.database.AppDatabase
import com.example.uasbellasolikha.database.DatabaseModel
// Pastikan import DAO ini benar. Jika merah, hapus dan Alt+Enter di kata 'DatabaseDao' di bawah.
import com.example.uasbellasolikha.database.dao.DatabaseDao

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.* // PENTING: Pakai bintang (*) untuk menghindari error 'launch'

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val databaseDao: DatabaseDao
    private val modelDatabase: LiveData<List<DatabaseModel>>

    init {
        val db = AppDatabase.getDatabase(application)
        databaseDao = db.databaseDao()
        modelDatabase = databaseDao.getAllOrder()
    }

    fun getDataList(): LiveData<List<DatabaseModel>> {
        return modelDatabase
    }

    // Fungsi Hapus Data
    fun deleteDataById(uid: Int) {
        // SOLUSI EROR: Tambahkan "context =" di depannya
        viewModelScope.launch(context = Dispatchers.IO) {
            databaseDao.deleteSingleData(uid)
        }
    }

    // Fungsi Update Data
    fun updateData(modelDatabase: DatabaseModel) {
        // SOLUSI EROR: Tambahkan "context =" di depannya
        viewModelScope.launch(context = Dispatchers.IO) {
            databaseDao.updateData(modelDatabase)
        }
    }
}