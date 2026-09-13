package com.example.uasbellasolikha.model

data class OrderModel(
    val name: String,
    val price: Int,
    val imageResId: Int,
    var quantity: Int = 0
)