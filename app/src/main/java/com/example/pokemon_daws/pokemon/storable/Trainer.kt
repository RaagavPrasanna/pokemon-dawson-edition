package com.example.pokemon_daws.pokemon.storable

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.pokemon_daws.pokemon.Pokemon


@Entity
class Trainer(
    @ColumnInfo(name="pokemons") override val pokemons: MutableList<Pokemon>,
    @ColumnInfo(name="name") val name: String,
    @ColumnInfo(name="collection") val collection: Collection
    ) : IStorable {

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

    fun storePK(pk:Pokemon){
        if(this.pokemons.size > 1){
            this.collection.addPK(pk)
            this.pokemons.remove(pk)
        }else{
            throw IndexOutOfBoundsException("Cannot put last pokemon in PC")
        }
    }

}