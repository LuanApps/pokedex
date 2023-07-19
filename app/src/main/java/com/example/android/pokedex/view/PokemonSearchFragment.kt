package com.example.android.pokedex.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.pokedex.R
import com.example.android.pokedex.database.PokemonDatabase
import com.example.android.pokedex.database.PokemonLocalRepository
import com.example.android.pokedex.databinding.FragmentPokemonSearchBinding
import com.example.android.pokedex.model.Pokemon
import com.example.android.pokedex.sendNotification
import com.example.android.pokedex.viewmodel.PokemonSearchViewModel
import jp.wasabeef.glide.transformations.ColorFilterTransformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PokemonSearchFragment : Fragment() {

    private lateinit var pokemonSearchViewModel: PokemonSearchViewModel
    private lateinit var binding: FragmentPokemonSearchBinding
    private lateinit var repository: PokemonLocalRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pokemon_search, container, false)
        pokemonSearchViewModel = ViewModelProvider(this)[PokemonSearchViewModel::class.java]

        repository = PokemonLocalRepository(PokemonDatabase.getInstance(requireContext()).pokemonDatabaseDao)

        setupView()

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.mainFragment)
                }
            }


        requireActivity().onBackPressedDispatcher.addCallback(this, callback);

        return binding.root
    }

    private fun setupView(){
        with(binding){
            pokemonSearchViewModel.viewModelScope.launch {
                pokemonSearchViewModel.fetchPokemon()

                pokemonSearchViewModel.pokemon.observe(viewLifecycleOwner) { pokemon ->
                    pokemon?.let {
                        //Get image from pokemon
                        val frontDefault: String =
                            pokemonSearchViewModel.pokemon.value?.frontDefault ?: ""
                        loadPokemonImageWithBlack(imageView, frontDefault)
                        //get the pokemon name and three other names
                        val rightAnswer = pokemonSearchViewModel.pokemon.value!!.name
                        val wrongAnswer1 = pokemonSearchViewModel.getRandomPokemonName()
                        val wrongAnswer2 = pokemonSearchViewModel.getRandomPokemonName()
                        val wrongAnswer3 = pokemonSearchViewModel.getRandomPokemonName()
                        //Put the answers randomly
                        val options =
                            listOf(rightAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3).shuffled()
                        optionButton1.text = options[0]
                        optionButton2.text = options[1]
                        optionButton3.text = options[2]
                        optionButton4.text = options[3]

                        optionButton1.setOnClickListener {
                            checkAnswer(
                                optionButton1,
                                captureButton,
                                imageView,
                                rightAnswer,
                                frontDefault
                            )
                            disableButtons(optionButton2, optionButton3, optionButton4)
                        }

                        optionButton2.setOnClickListener {
                            checkAnswer(
                                optionButton2,
                                captureButton,
                                imageView,
                                rightAnswer,
                                frontDefault
                            )
                            disableButtons(optionButton1, optionButton3, optionButton4)
                        }

                        optionButton3.setOnClickListener {
                            checkAnswer(
                                optionButton3,
                                captureButton,
                                imageView,
                                rightAnswer,
                                frontDefault
                            )
                            disableButtons(optionButton1, optionButton2, optionButton4)
                        }

                        optionButton4.setOnClickListener {
                            checkAnswer(
                                optionButton4,
                                captureButton,
                                imageView,
                                rightAnswer,
                                frontDefault
                            )
                            disableButtons(optionButton1, optionButton2, optionButton3)
                        }

                        captureButton.setOnClickListener{
                            lifecycleScope.launch {
                                withContext(Dispatchers.IO) {
                                    val result = savePokemon(pokemon)
                                    if (result) {
                                        Log.i("repository", "Pokemon ${pokemon.name} saved successfully!")
                                        val pokedexSize = repository.getAllPokemons().size
                                        if(pokedexSize == 1){
                                            sendNotification(requireContext(), pokemon)
                                        }
                                        navigateBack()
                                    }
                                    else{
                                        Log.i("repository", "Fail to save Pokemon!")
                                    }
                                }
                            }
                        }

                        //End buttons click listeners

                    }
                }
            }
        }
    }

    private fun checkAnswer(button: Button, saveButton: Button, image: ImageView, correctAnswer: String, imageUrl: String){
        if(button.text.equals(correctAnswer)) {
            loadPokemonImage(image, imageUrl)
            saveButton.isEnabled = true
            saveButton.text = resources.getString(R.string.capture_button)
            button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_400))
        }
        else{
            button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))
            saveButton.text = getString(R.string.try_again)
            saveButton.isEnabled = true
            saveButton.setOnClickListener{
                findNavController().navigate(R.id.pokemonSearchFragment)
            }
        }
    }

    private fun navigateBack(){
        lifecycleScope.launch(Dispatchers.Main) {
            findNavController().navigate(R.id.mainFragment)
        }
    }

    private fun disableButtons(button1: Button, button2: Button, button3: Button){
        button1.isEnabled = false
        button2.isEnabled = false
        button3.isEnabled = false
    }

    private fun loadPokemonImageWithBlack(image: ImageView, url:String){
        Glide.with(requireContext())
            .load(url)
            .fitCenter()
            .apply(
                RequestOptions()
                    .transform(ColorFilterTransformation(Color.BLACK)))
            .into(image)
    }

    private fun loadPokemonImage(image: ImageView, url:String){
        Glide.with(requireContext())
            .load(url)
            .fitCenter()
            .into(image)
    }

    private suspend fun savePokemon(pokemon: Pokemon): Boolean {
        return try {
            repository.savePokemon(pokemon)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}