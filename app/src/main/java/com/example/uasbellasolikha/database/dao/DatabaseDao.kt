package com.example.uasbellasolikha.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.uasbellasolikha.database.DatabaseModel

@Dao
interface DatabaseDao {

    // 1. UNTUK HISTORY (Menampilkan Data Pesanan)
    @Query("SELECT * FROM tbl_catering")
    fun getAllOrder(): LiveData<List<DatabaseModel>>

    // 2. UNTUK LOGIN (Mengambil Data User) -- INI YANG TADI HILANG
    @Query("SELECT * FROM tbl_catering WHERE username = :username AND password = :password")
    fun getUserByName(username: String, password: String): LiveData<List<DatabaseModel>>

    // 3. UNTUK ORDER (Menambah Data)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(modelDatabase: DatabaseModel)

    // 4. UNTUK EDIT (Mengubah Data)
    @Update
    suspend fun updateData(modelDatabase: DatabaseModel)

    // 5. UNTUK HAPUS (Swipe Delete)
    @Query("DELETE FROM tbl_catering WHERE uid = :uid")
    suspend fun deleteSingleData(uid: Int)
}