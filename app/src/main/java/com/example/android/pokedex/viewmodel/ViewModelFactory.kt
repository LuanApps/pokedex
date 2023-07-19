package com.example.android.pokedex.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application) as T
        }
        if(modelClass.isAssignableFrom(PokemonSearchViewModel::class.java))
            return PokemonSearchViewModel(application) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}