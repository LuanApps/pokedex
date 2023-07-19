package com.example.android.pokedex.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.android.pokedex.database.PokemonDatabase
import com.example.android.pokedex.database.PokemonLocalRepository
import com.example.android.pokedex.model.Pokemon
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _pokemonList: MutableLiveData<MutableList<Pokemon>> = MutableLiveData()
    val pokemonList: LiveData<MutableList<Pokemon>>
    get() = _pokemonList

    private val database = PokemonDatabase.getInstance(application)
    private val repository = PokemonLocalRepository(database.pokemonDatabaseDao)

    init {
        viewModelScope.launch {
            val pokemons = repository.getAllPokemons()
            _pokemonList.postValue(pokemons.toMutableList())
        }
    }
}