package com.example.android.pokedex.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.pokedex.model.Pokemon

@Dao
interface PokemonDatabaseDao {

    //Since the main goal of app is to fill the pokedex with all pokemon, methods like delete has no utility here, since we dont will delete pokemons from pokedex.

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePokemon(pokemon: Pokemon)

    @Query("SELECT * FROM pokemon_table WHERE id = :id")
    suspend fun getPokemon(id: Long): Pokemon

    @Query("SELECT * FROM pokemon_table")
    suspend fun getAllPokemons(): List<Pokemon>
}