package com.example.android.pokedex.api

import com.example.android.pokedex.model.Pokemon
import org.json.JSONObject

//I choose to fetch only the data needed from the jsonResponse. So I have made this method to extract only the necessary data from json response.

fun parsePokemonJsonResult(jsonResult: JSONObject): Pokemon {
    val id = jsonResult.getLong("id")
    val baseExperience = jsonResult.getInt("base_experience")
    val height = jsonResult.getInt("height")
    val name = jsonResult.getString("name")
    val weight = jsonResult.getInt("weight")
    val frontDefault = jsonResult.getJSONObject("sprites").getJSONObject("other").getJSONObject("official-artwork").getString("front_default")
    val frontShiny = jsonResult.getJSONObject("sprites").getJSONObject("other").getJSONObject("official-artwork").getString("front_shiny")

    val statsJsonArray = jsonResult.getJSONArray("stats")
    val hp = statsJsonArray.getJSONObject(0).getInt("base_stat")
    val attack = statsJsonArray.getJSONObject(1).getInt("base_stat")
    val defense = statsJsonArray.getJSONObject(2).getInt("base_stat")
    val special_attack = statsJsonArray.getJSONObject(3).getInt("base_stat")
    val special_defence = statsJsonArray.getJSONObject(4).getInt("base_stat")
    val speed = statsJsonArray.getJSONObject(5).getInt("base_stat")
    val typesJsonArray = jsonResult.getJSONArray("types")
    val types = mutableListOf<String>()
    for (i in 0 until typesJsonArray.length()) {
        val typeJson = typesJsonArray.getJSONObject(i)
        val typeDetailsJson = typeJson.getJSONObject("type")
        val typeName = typeDetailsJson.getString("name")
        types.add(typeName)
    }

    return Pokemon(id, baseExperience, height, name, weight,frontDefault, frontShiny, hp, attack, defense, special_attack, special_defence, speed, types)
}