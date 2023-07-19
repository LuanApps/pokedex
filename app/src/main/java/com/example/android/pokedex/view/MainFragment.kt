package com.example.android.pokedex.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.pokedex.R
import com.example.android.pokedex.adapter.RecyclerViewAdapter
import com.example.android.pokedex.databinding.FragmentMainBinding
import com.example.android.pokedex.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding // Declare the binding object

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.viewModel = viewModel
        // Set the lifecycle owner for observing LiveData updates
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the RecyclerView adapter and set it to the RecyclerView
        val adapter = RecyclerViewAdapter { pokemon ->
            // Handle the item click event here
            //Snackbar.make(requireView(), pokemon.name, Snackbar.LENGTH_SHORT).show()
            val action = MainFragmentDirections.actionMainFragmentToPokemonDetailsFragment(pokemon)
            findNavController().navigate(action)
        }

        viewModel.pokemonList.observe(viewLifecycleOwner) { pokemons ->
            adapter.updateData(pokemons.toMutableList())
        }

        with(binding){
            pokemonRecyclerView.adapter = adapter
            // Set the layout manager for the RecyclerView
            pokemonRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            buttonSearch.setOnClickListener {
                if(isInternetAvailable())
                    findNavController().navigate(MainFragmentDirections.actionMainFragmentToPokemonSearchFragment())
                else
                    Snackbar.make(requireView(), "Internet not available, connect to the internet!", Snackbar.LENGTH_SHORT).show()

                }
        }
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}