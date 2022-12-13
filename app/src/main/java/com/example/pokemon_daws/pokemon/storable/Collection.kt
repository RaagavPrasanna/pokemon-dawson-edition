package com.example.pokemon_daws.pokemon.storable

import com.example.pokemon_daws.pokemon.Pokemon

class Collection : IStorable{
    override val pokemons = mutableListOf<Pokemon>()

    override fun addPK(pk: Pokemon) {
        this.pokemons.add(pk)
    }

    override fun removePK(pk: Pokemon) {
        this.pokemons.remove(pk)
    }
}