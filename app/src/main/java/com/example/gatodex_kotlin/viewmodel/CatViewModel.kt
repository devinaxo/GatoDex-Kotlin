package com.example.gatodex_kotlin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.gatodex_kotlin.data.models.Cat
import com.example.gatodex_kotlin.data.local.CatDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CatViewModel(application: Application) : AndroidViewModel(application) {
    private val catDao = CatDatabase.getDatabase(application).catDao()

    val allCats: LiveData<List<Cat>> = catDao.getAllCats().asLiveData()

    fun insertCat(cat: Cat) {
        viewModelScope.launch(Dispatchers.IO) {
            catDao.insert(cat)
        }
    }

    suspend fun getCatById(catId: Long): Cat? {
        return catDao.getCatById(catId)
    }
}