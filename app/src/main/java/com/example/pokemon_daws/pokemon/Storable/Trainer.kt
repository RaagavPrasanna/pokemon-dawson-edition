package com.example.pokemon_daws.pokemon.Storable

import com.example.pokemon_daws.pokemon.Pokemon

class Trainer(
    override val pokemons: MutableList<Pokemon>,
    val name: String,
    ) : IStorable{

    private val pc: Collection = Collection();

    override fun addPK(pk: Pokemon) {
        if(this.pokemons.size < 6){
            pokemons.add(pk);
        }else{
            throw IndexOutOfBoundsException("Cannot have more than 6 pokemon in team")
        }
    }

    override fun removePK(pk: Pokemon) {
        if(this.pokemons.size > 1){
            pokemons.remove(pk);
        }else{
            throw IndexOutOfBoundsException("Cannot have an empty team")
        }
    }

}