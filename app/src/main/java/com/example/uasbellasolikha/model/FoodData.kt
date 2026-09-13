package com.example.uasbellasolikha.model

import com.example.uasbellasolikha.R

object FoodData {
    fun getAllFood(): List<FoodModel> {
        return listOf(
            // --- Kategori Complete Package ---
            FoodModel(
                nama = "Nasi Kuning",
                harga = "Rp. 17.500",
                imageResourceId = R.drawable.paket_1,
                category = "complete"
            ),
            FoodModel(
                nama = "Sate Ayam",
                harga = "Rp. 25.000",
                imageResourceId = R.drawable.paket_2,
                category = "complete"
            ),
            FoodModel(
                nama = "Chiken Katsu",
                harga = "Rp. 28.500",
                imageResourceId = R.drawable.paket_3,
                category = "complete"
            ),
            FoodModel(
                nama = "Ayam Serundeng",
                harga = "Rp. 15.000",
                imageResourceId = R.drawable.paket_4,
                category = "complete"
            ),
            FoodModel(
                nama = "Ayam Geprek",
                harga = "Rp. 12.000",
                imageResourceId = R.drawable.paket_5,
                category = "complete"
            ),
            FoodModel(
                nama = "Nasi Goreng",
                harga = "Rp. 16.000",
                imageResourceId = R.drawable.paket_6,
                category = "complete"
            ),

            // --- Kategori Saving Package ---
            FoodModel(
                nama = "Chiken Wing",
                harga = "Rp. 15.000",
                imageResourceId = R.drawable.pakethe1,
                category = "saving"
            ),
            FoodModel(
                nama = "Ayam Suwir Kemangi",
                harga = "Rp. 15.000",
                imageResourceId = R.drawable.pakethe2,
                category = "saving"
            ),
            FoodModel(
                nama = "Ayam Suwir Sambal Geprek",
                harga = "Rp. 15.000",
                imageResourceId = R.drawable.pakethe3,
                category = "saving"
            ),
            FoodModel(
                nama = "Nugget Telor",
                harga = "Rp. 15.000",
                imageResourceId = R.drawable.pakethe4,
                category = "saving"
            ),
            FoodModel(
                nama = "Ayam Suwir Sambal Matah",
                harga = "Rp. 15.000",
                imageResourceId = R.drawable.pakethe5,
                category = "saving"
            ),
            FoodModel(
                nama = "Ayam Tepung",
                harga = "Rp. 15.000",
                imageResourceId = R.drawable.pakethe6,
                category = "saving"
            ),

            // --- Kategori Healthy Package ---
            FoodModel(
                nama = "Healthy Rice Bowl",
                harga = "Rp. 45.000",
                imageResourceId = R.drawable.paket_1h,
                category = "healty"
            ),
            FoodModel(
                nama = "Grilled Chicken Avocado Salad",
                harga = "Rp. 52.500",
                imageResourceId = R.drawable.paket_2h,
                category = "healty"
            ),
            FoodModel(
                nama = "Roasted Chiken",
                harga = "Rp. 47.000",
                imageResourceId = R.drawable.paket_3h,
                category = "healty"
            ),
            FoodModel(
                nama = "Meatballs & Mashed Potato",
                harga = "Rp. 46.200",
                imageResourceId = R.drawable.paket_4h,
                category = "healty"
            ),
            FoodModel(
                nama = "Grilled Salmon",
                harga = "Rp. 85.000",
                imageResourceId = R.drawable.paket_5h,
                category = "healty"
            ),
            FoodModel(
                nama = "Cobb Salad",
                harga = "Rp. 35.000",
                imageResourceId = R.drawable.paket_6h,
                category = "healty"
            ),

            // --- Kategori Fast Food Package ---
            FoodModel(
                nama = "Corndog",
                harga = "Rp. 15.000",
                imageResourceId = R.drawable.pfast1,
                category = "fastfood"
            ),
            FoodModel(
                nama = "Burger",
                harga = "Rp. 25.000",
                imageResourceId = R.drawable.pfast2,
                category = "fastfood"
            ),
            FoodModel(
                nama = "French Fries",
                harga = "Rp. 10.000",
                imageResourceId = R.drawable.pfast3,
                category = "fastfood"
            ),
            FoodModel(
                nama = "Nugget",
                harga = "Rp. 15.000",
                imageResourceId = R.drawable.pfast4,
                category = "fastfood"
            )

        )
    }
}