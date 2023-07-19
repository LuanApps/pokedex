package com.example.android.pokedex.adapter

import com.example.android.pokedex.R
import com.example.android.pokedex.model.Pokemon

class RecyclerViewAdapter(callBack: (pokemon: Pokemon) -> Unit) :
    BaseRecyclerViewAdapter<Pokemon>(callBack) {

    override fun getLayoutRes(viewType: Int) = R.layout.item_pokemon
}