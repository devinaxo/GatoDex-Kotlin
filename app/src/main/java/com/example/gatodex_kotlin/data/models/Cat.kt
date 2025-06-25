package com.example.gatodex_kotlin.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.gatodex_kotlin.data.models.Species

@Entity(
    tableName = "Cats",
    foreignKeys = [
        ForeignKey(
            entity = Species::class,
            parentColumns = ["speciesId"],
            childColumns = ["species_id"],
            onDelete = ForeignKey.Companion.RESTRICT,
        )
    ]
)
data class Cat (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    @ColumnInfo(name = "species_id", index = true) val speciesId: Long,
    val photoUri: String,
    val latitude: Double,
    val longitude: Double,
    @ColumnInfo(name = "date_met") val dateMet: Long
)