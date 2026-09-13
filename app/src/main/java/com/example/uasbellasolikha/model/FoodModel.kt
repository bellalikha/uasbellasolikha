package com.example.uasbellasolikha.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class FoodModel(
    val nama: String,
    val harga: String,
    val imageResourceId: Int,
    val category: String
) : Parcelable