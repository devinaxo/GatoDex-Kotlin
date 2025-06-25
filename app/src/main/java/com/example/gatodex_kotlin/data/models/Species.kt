package com.example.gatodex_kotlin.data.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Species",
    indices = [Index(value = ["name"], unique = true)]
)
data class Species(
    @PrimaryKey(autoGenerate = true) val speciesId: Long = 0,
    val name: String,
    val description: String
)