package com.example.android.pokedex.viewmodel

import android.app.Application
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.pokedex.api.PokemonApi
import com.example.android.pokedex.api.parsePokemonJsonResult
import com.example.android.pokedex.database.PokemonDatabase
import com.example.android.pokedex.database.PokemonLocalRepository
import com.example.android.pokedex.model.Pokemon
import com.example.android.pokedex.pokemonNameList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject

class PokemonSearchViewModel(application: Application) : AndroidViewModel(application) {

    private val _pokemon: MutableLiveData<Pokemon> = MutableLiveData()
    val pokemon: LiveData<Pokemon>
        get() = _pokemon

    private val database = PokemonDatabase.getInstance(application)
    private val repository = PokemonLocalRepository(database.pokemonDatabaseDao)

    suspend fun fetchPokemon() {
        withContext(Dispatchers.IO) {
            try {
                val pokemonName = getRandomPokemonName()
                val responseBody: ResponseBody = PokemonApi.pokemonApiService.getPokemon(pokemonName)
                val jsonString = responseBody.string()
                val jsonObject = JSONObject(jsonString)
                val pokemon = parsePokemonJsonResult(jsonObject)
                pokemon.let {
                    _pokemon.postValue(pokemon)
                    Log.d("fetchPokemon", "Pokemon: ${it.name}, ID: ${it.id}, HP: : ${it.hp}")
                    // Access other attributes as needed
                }
            } catch (e: Exception) {
                Log.e("fetchPokemon", "Failed to fetch the Pokémon data: $e")
            }
        }
    }

    fun getRandomPokemonName():String{
        val randomIndex = (0 until pokemonNameList.size).random()
        return pokemonNameList[randomIndex].lowercase()
    }

    suspend fun savePokemon(pokemon: Pokemon?): Boolean {
        return try {
            pokemon?.let { repository.savePokemon(it) }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun getPokedexSize(): Int{
        return repository.getAllPokemons().size
    }

}