package com.example.uasbellasolikha.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tbl_catering")
data class DatabaseModel(
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0, // Default 0 supaya auto-increment jalan

    @ColumnInfo(name = "nama_menu")
    var nama_menu: String? = null,

    @ColumnInfo(name = "jml_items")
    var jml_items: Int = 0,

    @ColumnInfo(name = "harga")
    var harga: Int = 0,

    @ColumnInfo(name = "email")
    var email: String? = null,

    @ColumnInfo(name = "username")
    var username: String? = null,

    @ColumnInfo(name = "password")
    var password: String? = null,

    // --- TAMBAHAN BARU (FITUR BATAS EDIT) ---
    // Jangan lupa Uninstall aplikasi dulu sebelum Run ulang!

    @ColumnInfo(name = "edit_count")
    var editCount: Int = 0, // Menghitung sudah berapa kali edit

    @ColumnInfo(name = "timestamp")
    var timestamp: Long = System.currentTimeMillis() // Mencatat waktu pembuatan pesanan

) : Serializable