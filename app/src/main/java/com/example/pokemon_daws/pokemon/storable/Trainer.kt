package com.example.pokemon_daws.pokemon.storable

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.pokemon_daws.pokemon.Pokemon
import com.google.gson.Gson


@Entity
data class Trainer(
    @ColumnInfo(name="pokemons") override val pokemons: MutableList<Pokemon>,
    @ColumnInfo(name="name") val name: String,
    @ColumnInfo(name="collection") val collection: Collection,
    ) : IStorable {

    @PrimaryKey(autoGenerate = true) var id: Int = 0

    override fun addPK(pk: Pokemon) {
        if(this.pokemons.size < 6){
            pokemons.add(pk)
        }else{
            throw IndexOutOfBoundsException("Cannot have more than 6 pokemon in team")
        }
    }

    override fun removePK(pk: Pokemon) {
        if(this.pokemons.size > 1){
            pokemons.remove(pk)
        }else{
            throw IndexOutOfBoundsException("Cannot have an empty team")
        }
    }

    fun collectPK(pk:Pokemon) {
        this.collection.addPK(pk)
    }
    
    fun isTrainerDead():Boolean{
        var isDead = true
        for (pk in pokemons){
            if (pk.hp > 0){
                isDead = false
            }
        }
        return isDead
    }

    fun alivePokeCount(): Int{
        var count = 0

        for (pk in pokemons){
            if (pk.hp > 0){
                count += 1
            }
        }
        return count
    }


}

class TrainerTypeConverter {
    @TypeConverter
    fun fromPokeListToString(pokemons: MutableList<Pokemon>): String {
        return Gson().toJson(pokemons)
    }

    @TypeConverter
    fun fromStringToPokeList(json: String): MutableList<Pokemon> {
        return Gson().fromJson(json, Array<Pokemon>::class.java).toMutableList()
    }

    @TypeConverter
    fun fromCollectionToString(collection: Collection) : String {
        return Gson().toJson(collection)
    }

    @TypeConverter
    fun fromStringToCollection(json: String): Collection {
        return Gson().fromJson(json, Collection::class.java)
    }
}