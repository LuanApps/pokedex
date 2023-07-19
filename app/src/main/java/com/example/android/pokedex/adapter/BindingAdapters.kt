package com.example.android.pokedex.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.android.pokedex.R

@BindingAdapter("pokemonType")
fun setPokemonType(layout: ConstraintLayout, pokemonType: String) {
    val context = layout.context

    val imageResId = when (pokemonType) {
        "bug" -> R.drawable.bug
        "dark" -> R.drawable.dark
        "dragon" -> R.drawable.dragon
        "electric" -> R.drawable.electric
        "fairy" -> R.drawable.fairy
        "fighting" -> R.drawable.fighting
        "fire" -> R.drawable.fire
        "flying" -> R.drawable.flying
        "ghost" -> R.drawable.ghost
        "grass" -> R.drawable.grass
        "ground" -> R.drawable.ground
        "ice" -> R.drawable.ice
        "poison" -> R.drawable.poison
        "psychic" -> R.drawable.psychic
        "rock" -> R.drawable.rock
        "steel" -> R.drawable.steel
        "water" -> R.drawable.water
        else -> R.drawable.normal // Change to your default image resource
    }

    val backgroundColorResId = when (pokemonType) {
        "bug" -> R.color.bug_color
        "dark" -> R.color.dark_color
        "dragon" -> R.color.dragon_color
        "electric" -> R.color.electric_color
        "fairy" -> R.color.fairy_color
        "fighting" -> R.color.fighting_color
        "fire" -> R.color.fire_color
        "flying" -> R.color.flying_color
        "ghost" -> R.color.ghost_color
        "grass" -> R.color.grass_color
        "ground" -> R.color.ground_color
        "ice" -> R.color.ice_color
        "poison" -> R.color.poison_color
        "psychic" -> R.color.psychic_color
        "rock" -> R.color.rock_color
        "steel" -> R.color.steel_color
        "water" -> R.color.water_color
        else -> R.color.normal_color // Change to your default background color resource
    }

    val imageView = layout.findViewById<ImageView>(R.id.typeImg)
    imageView.setImageResource(imageResId)
    imageView.setColorFilter(ContextCompat.getColor(context, backgroundColorResId))

    val textView = layout.findViewById<TextView>(R.id.pokemonNameTxt)
    textView.setTextColor(ContextCompat.getColor(context, backgroundColorResId))
}