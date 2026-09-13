package com.example.uasbellasolikha.database

import android.content.Context
import androidx.room.Room

class DatabaseClient private constructor(context: Context) {

    // Variable untuk menampung database
    val appDatabase: AppDatabase

    init {
        appDatabase = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "catering_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    companion object {
        @Volatile
        private var mInstance: DatabaseClient? = null

        // Function untuk membuat/mengambil instance (Singleton)
        fun getInstance(context: Context): DatabaseClient {
            return mInstance ?: synchronized(this) {
                val instance = DatabaseClient(context)
                mInstance = instance
                instance
            }
        }
    }
}