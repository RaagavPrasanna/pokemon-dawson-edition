package com.example.pokemon_daws.utils

import android.content.Context
import com.google.gson.Gson
import java.io.IOException

class Json (private val context: Context){
    private val gson: Gson = Gson()

    private fun readFile(fileName: String, folderpath: String): String?{
        val path = "$folderpath/$fileName"
        val jsonString: String
        try {
            jsonString = context.assets.open(path).bufferedReader().use { it.readText() }
        } catch (error: IOException){
            error.printStackTrace()
            return null
        }
        return jsonString
    }

    fun readJsonMove(fileName: String): MoveData{
        val jsonString = readFile(fileName, "moves")
        return gson.fromJson(jsonString, MoveData::class.java)
    }

    fun readJsonMoveList(fileName: String): MutableList<MoveListData>{
        val jsonString = readFile(fileName, "move_lists")
        return gson.fromJson(jsonString, Array<MoveListData>::class.java).toMutableList()
    }

    fun readJsonPokemon(fileName: String): PokemonData{
        val jsonString = readFile(fileName, "pokemon")
        return gson.fromJson(jsonString, PokemonData::class.java)
    }

    fun readJsonTypeRelations(fileName: String): TypeRelationsData{
        val jsonString = readFile(fileName, "type_relations")
        return gson.fromJson(jsonString, TypeRelationsData::class.java)
    }
}
data class MoveData(
    val accuracy : Int,
    val ailment : String?,
    val ailmentChance : Int,
    val category : String,
    val damageClass : String,
    val heal : Int,
    val maxPP : Int,
    val name : String,
    val power : Int,
    val target : String,
    val type : String
){}

data class PokemonData(
    val baseExperienceReward : Int,
    val baseStateAttack : Int,
    val baseStatDefense : Int,
    val baseStateMaxHp : Int,
    val baseStatSpecialAttack : Int,
    val baseStatSpecialDefense : Int,
    val baseStatSpeed : Int,
    val species : String,
    val types : ArrayList<String>
){}

data class MoveListData(
    val move: String,
    val level: Int
){}

data class TypeRelationsData(
    val normal: String? = null,
    val fighting: String? = null,
    val flying: String? = null,
    val poison: String? = null,
    val ground: String? = null,
    val rock: String? = null,
    val bug: String? = null,
    val ghost: String? = null,
    val fire: String? = null,
    val water: String? = null,
    val grass: String? = null,
    val electric: String? = null,
    val psychic: String? = null,
    val ice: String? = null,
    val dragon: String? = null
){}
