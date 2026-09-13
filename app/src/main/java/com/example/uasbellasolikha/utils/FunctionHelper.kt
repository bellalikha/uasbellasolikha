package com.example.uasbellasolikha.utils

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object FunctionHelper {

    // Format Rupiah (Manual seperti kode Java kamu)
    fun rupiahFormat(price: Int): String {
        val formatter = DecimalFormat("#,###")
        // Di Kotlin 'replaceAll' cukup pakai 'replace'
        return "Rp " + formatter.format(price).replace(",", ".")
    }

    // Ambil Tanggal Hari Ini
    fun getToday(): String {
        val date = Date()
        // Saya tambahkan Locale("id", "ID") agar nama bulan jadi Bahasa Indonesia (misal: Agustus)
        // walau HP user settingannya Bahasa Inggris.
        val format = SimpleDateFormat("d MMMM yyyy", Locale("id", "ID"))
        return format.format(date)
    }
}