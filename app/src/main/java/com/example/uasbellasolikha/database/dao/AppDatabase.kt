package com.example.uasbellasolikha.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.uasbellasolikha.database.dao.DatabaseDao

// PENTING: Saya ubah version jadi 2 karena kamu habis nambah kolom baru
@Database(entities = [DatabaseModel::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun databaseDao(): DatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "catering_database"
                )
                    // Ini obat anti-crash: kalau tabel beda, hapus & buat baru otomatis
                    .fallbackToDestructiveMigration()
                    // Tambahan: Izinkan akses di thread utama biar tidak macet
                    .allowMainThreadQueries()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}