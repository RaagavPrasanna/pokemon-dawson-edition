package com.example.pokemon_daws.pokemon.Storable

import com.example.pokemon_daws.pokemon.Pokemon

interface IStorable {
    val pokemons : MutableList<Pokemon>
    fun addPK(pk: Pokemon)
    fun removePK(pk: Pokemon)
}