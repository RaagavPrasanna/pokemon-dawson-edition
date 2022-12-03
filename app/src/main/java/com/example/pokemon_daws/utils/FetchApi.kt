package com.example.pokemon_daws.utils
import PokeApiEndpoint
import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import simplifyMove
import simplifyMoves
import simplifyPokedexEntries
import simplifyPokemon
import simplifyTypeRelations
import simplifyTypes
import java.net.URL
import javax.net.ssl.HttpsURLConnection

private val GSON: Gson = GsonBuilder().setPrettyPrinting().serializeNulls().create()
//Todo: Change classes to entity classes to be put in db
suspend fun fetchAllPokemon(): List<PokedexEntry> {
    val url = URL(PokeApiEndpoint.POKEDEX.url + "/2")
    val response = connect(url, Array<PokedexEntry>::class.java, ::simplifyPokedexEntries)!!.toList();
    Log.i("POKEDEX TEST", response.toString())
    return response
}

suspend fun fetchTypes(): List<String>{
    val url = URL(PokeApiEndpoint.GENERATION.url + "/generation-i")
    val response = connect(url, Array<String>::class.java, ::simplifyTypes)!!.toList()
    Log.i("TYPES TEST", response.toString())
    return response
}

suspend fun fetchPokemon(species: String): PokemonEntry{
    val url = URL(PokeApiEndpoint.POKEMON.url + "/${species}")
    val response = connect(url, PokemonEntry::class.java, ::simplifyPokemon);
    Log.i("Pokemon TEST", response.toString())
    return response!!
}

suspend fun fetchPkMoves(species: String): List<PkMove>{
    val url = URL(PokeApiEndpoint.POKEMON.url + "/${species}")
    val response = connect(url, Array<PkMove>::class.java, ::simplifyMoves)!!.toList()
    Log.i("Pokemon Moves TEST", response.toString())
    return response
}

suspend fun fetchMove(species: String): Move{
    val url = URL(PokeApiEndpoint.MOVE.url + "/${species}")
    val response = connect(url, Move::class.java, ::simplifyMove)
    Log.i("Move TEST", response.toString())
    return response!!
}

suspend fun fetchTypeRelations(type: String): TypeRelation {
    val url = URL(PokeApiEndpoint.TYPE.url + "/${type}")
    val response = connect(url, TypeRelation::class.java, ::simplifyTypeRelations)
    Log.i("TYPE RELATION TEST", response.toString())
    return response!!
}



private suspend fun<T> connect(url: URL, jsonClass: Class<T>, simplifier: (apiResponse:String) -> String):T? {
    var result : T? = null
    withContext(Dispatchers.IO) {
        val conn = url.openConnection() as HttpsURLConnection
        try {
            conn.requestMethod = "GET"
            conn.connect()
            if (conn.responseCode == HttpsURLConnection.HTTP_OK) {
                val response = conn.inputStream.bufferedReader().use { it.readText() }
                result = GSON.fromJson(simplifier(response), jsonClass)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("NETWORK ERROR", e.toString())
            throw e
        } finally {
            conn.disconnect()
        }
    }
    return result
}

@Entity(tableName = "pokedex_entry")
data class PokedexEntry(
    @ColumnInfo(name = "number")val number: Int,
    @PrimaryKey @ColumnInfo(name = "name")val name: String
)

data class TypeRelation(
    val fire : String,
    val electric: String,
    val grass: String,
    val poison: String,
    val flying: String,
    val bug: String,
    val rock: String,
    val steel: String,
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

data class Move(
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
    val healing: Int
)
data class Type(
    val type: String
)