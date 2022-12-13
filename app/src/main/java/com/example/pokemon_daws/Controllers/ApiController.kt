package com.example.pokemon_daws.Controllers

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokemon_daws.utils.*
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import PokeApiEndpoint
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import simplifyMove
import simplifyMoves
import simplifyPokedexEntries
import simplifyPokemon
import simplifyTypeRelations
import simplifyTypes
import java.io.BufferedReader


class ApiController {
    private val GSON: Gson = GsonBuilder().setPrettyPrinting().serializeNulls().create()

    suspend fun getAllPokemon(): List<PokedexEntry> {
        val entries: List<PokedexEntry>
        val url = URL(PokeApiEndpoint.POKEDEX.url + "/2")
        entries = connect(url, Array<PokedexEntry>::class.java, ::simplifyPokedexEntries)!!.toList()
        return entries
    }
    suspend fun getTypes(): List<String>{
        val types: List<String>
        val url = URL(PokeApiEndpoint.GENERATION.url + "/generation-i")
        types= connect(url, Array<String>::class.java, ::simplifyTypes)!!.toList()
        return types
    }

    suspend fun getPokemon(species:String):PokemonEntry?{
        val pk: PokemonEntry?
        val url = URL(PokeApiEndpoint.POKEMON.url + "/${species}")
        pk = connect(url, PokemonEntry::class.java, ::simplifyPokemon)
        return pk
    }

    suspend fun getPkMoves(species: String): List<PkMove>{
        val pkMoves: List<PkMove>
        val url = URL(PokeApiEndpoint.POKEMON.url + "/${species}")
        pkMoves = connect(url, Array<PkMove>::class.java, ::simplifyMoves)!!.toList()
        return pkMoves
    }

    suspend fun getMove(moveName: String): MoveEntry?{
        val move: MoveEntry?
        val url = URL(PokeApiEndpoint.MOVE.url + "/${moveName}")
        move = connect(url, MoveEntry::class.java, ::simplifyMove)
        return move
    }

    suspend fun getTypeRelations(type: String): TypeRelation? {
        val relations: TypeRelation?
        val url = URL(PokeApiEndpoint.TYPE.url + "/${type}")
        relations = connect(url, TypeRelation::class.java, ::simplifyTypeRelations)
        return relations
    }

    private suspend fun<T> connect(url: URL, jsonClass: Class<T>, simplifier: (apiResponse:String) -> String):T? {
        var result : T? = null
        withContext(Dispatchers.IO) {
            val conn = url.openConnection() as HttpsURLConnection
            var reader: BufferedReader? = null
            try {
                conn.requestMethod = "GET"
                conn.connect()
                if (conn.responseCode == HttpsURLConnection.HTTP_OK) {
                    reader = conn.inputStream.bufferedReader()
                    val response = reader.use { it.readText() }
                    result = GSON.fromJson(simplifier(response), jsonClass)
                }else{}
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("NETWORK ERROR", e.toString())
            } finally {
                reader?.close()
                conn.disconnect()
            }
        }
        return result
    }
}

@Entity(tableName = "pokedex_entry")
data class PokedexEntry(
    @ColumnInfo(name = "number")val number: Int,
    @PrimaryKey @ColumnInfo(name = "name")val name: String
)

data class TypeRelation(
    val normal: String,
    val fire: String,
    val water: String,
    val electric: String,
    val grass: String,
    val ice: String,
    val fighting: String,
    val poison: String,
    val ground: String,
    val flying: String,
    val psychic: String,
    val bug: String,
    val rock: String,
    val ghost: String,
    val dragon: String,
)

data class PokemonEntry(
    val name: String,
    val types: List<String>,
    val base_exp_reward: Int,
    val base_maxHp: Int,
    val base_attack: Int,
    val base_defense: Int,
    @SerializedName("base_special-attack") val baseSpecialAttack: Int,
    @SerializedName("base_special-defense") val baseSpecialDefense: Int,
    val base_speed: Int,
    val back_sprite: String,
    val front_sprite: String
)

data class PkMove(
    val move: String,
    val level: Int
)

data class MoveEntry(
    val name: String,
    val description: String,
    val category: String,
    val accuracy: Int,
    val power: Int,
    val damage_class: String,
    val type: String,
    val maxPP: Int,
    val ailment: String,
    val ailment_chance: Int,
    val healing: Int,
    val target: String,
)
//
//data class Type(
//    val type: String
//)