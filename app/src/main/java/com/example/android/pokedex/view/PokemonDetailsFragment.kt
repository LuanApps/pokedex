package com.example.android.pokedex.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.android.pokedex.R
import com.example.android.pokedex.databinding.FragmentPokemonDetailsBinding
import com.example.android.pokedex.databinding.FragmentPokemonSearchBinding
import com.example.android.pokedex.model.Pokemon

class PokemonDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPokemonDetailsBinding
    private lateinit var pokemon: Pokemon

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pokemon_details, container, false)
        // Retrieve the argument value from the arguments bundle
        arguments?.let {
            pokemon = it.getParcelable<Pokemon>("pokemon") ?: return null
            loadPokemonImage(binding.pokemonImg, pokemon.frontDefault)
        }

        binding.pokemon = pokemon

        binding.switchImg.setOnCheckedChangeListener{ _, isChecked ->
            val imageView = binding.pokemonImg
            if (isChecked) {
                loadPokemonImage(imageView, pokemon.frontShiny)
            } else {
                loadPokemonImage(imageView, pokemon.frontDefault)
            }
        }

        return binding.root
    }

    private fun loadPokemonImage(image: ImageView, url:String){
        Glide.with(requireContext())
            .load(url)
            .fitCenter()
            .into(image)
    }

}