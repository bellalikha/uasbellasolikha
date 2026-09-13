package com.example.uasbellasolikha.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.uasbellasolikha.database.AppDatabase
import com.example.uasbellasolikha.database.DatabaseModel
import com.example.uasbellasolikha.database.dao.DatabaseDao

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val databaseDao: DatabaseDao
    private lateinit var modelDatabase: LiveData<List<DatabaseModel>>

    init {
        val db = AppDatabase.getDatabase(application)
        databaseDao = db.databaseDao()
    }

    fun getDataUser(username: String, password: String): LiveData<List<DatabaseModel>> {
        modelDatabase = databaseDao.getUserByName(username, password)
        return modelDatabase
    }
}