package com.example.gatodex_kotlin.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gatodex_kotlin.data.models.Species
import kotlinx.coroutines.flow.Flow

@Dao
interface SpeciesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSpecies(species: Species): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllSpecies(speciesList: List<Species>)

    @Query("SELECT * FROM Species ORDER BY name ASC")
    fun getAllSpecies(): Flow<List<Species>>

    @Query("SELECT * FROM Species WHERE speciesId = :speciesId")
    suspend fun getSpeciesById(speciesId: Long): Species?

    @Query("SELECT * FROM Species WHERE name = :name LIMIT 1")
    suspend fun getSpeciesByName(name: String): Species?

    @Query("SELECT COUNT(*) FROM Species")
    suspend fun getSpeciesCount(): Int
}