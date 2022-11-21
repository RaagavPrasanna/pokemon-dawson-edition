package com.example.pokemon_daws.utils

import android.content.Context
import com.google.gson.Gson
import java.io.IOException
import java.net.URL

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

    fun readJsonMove(fileName: String): Move{
        val jsonString = readFile(fileName, "moves")
        return gson.fromJson(jsonString, Move::class.java)
    }

    fun readJsonMoveList(fileName: String): List<MoveList>{
        val jsonString = readFile(fileName, "move_lists")
        return gson.fromJson(jsonString, Array<MoveList>::class.java).toList()
    }

    fun readJsonPokemon(fileName: String): Pokemon{
        val jsonString = readFile(fileName, "pokemon")
        return gson.fromJson(jsonString, Pokemon::class.java)
    }

    fun readJsonTypeRelations(fileName: String): TypeRelations{
        val jsonString = readFile(fileName, "type_relations")
        return gson.fromJson(jsonString, TypeRelations::class.java)
    }
}
data class Move(
    val accuracy : Int,
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

data class Pokemon(
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

data class MoveList(
    val move: String,
    val level: Int
){}

data class TypeRelations(
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
