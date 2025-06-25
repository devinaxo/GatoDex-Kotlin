package com.example.gatodex_kotlin.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.gatodex_kotlin.data.models.Cat
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {
    @Insert
    suspend fun insert(cat: Cat)

    @Query("SELECT * FROM Cats ORDER BY date_met DESC")
    fun getAllCats(): Flow<List<Cat>>

    @Query("SELECT * FROM Cats WHERE id = :catId")
    suspend fun getCatById(catId: Long): Cat?

    @Delete
    suspend fun delete(cat: Cat)
}