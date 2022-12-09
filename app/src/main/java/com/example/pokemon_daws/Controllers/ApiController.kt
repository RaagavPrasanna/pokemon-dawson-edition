package com.example.pokemon_daws.Controllers

import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokemon_daws.utils.*
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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


class ApiController(private val lifecycleScope: LifecycleCoroutineScope) {
    private val GSON: Gson = GsonBuilder().setPrettyPrinting().serializeNulls().create()

    suspend fun getAllPokemon(): List<PokedexEntry> {
        var entries = listOf<PokedexEntry>()
        val url = URL(PokeApiEndpoint.POKEDEX.url + "/2")
        lifecycleScope.launch(Dispatchers.IO) {
            entries = connect(url, Array<PokedexEntry>::class.java, ::simplifyPokedexEntries)!!.toList();
        }.join()
        Log.i("POKEDEX TEST", entries.toString())
        return entries
    }
    suspend fun getTypes(): List<String>{
        var types = listOf<String>()
        val url = URL(PokeApiEndpoint.GENERATION.url + "/generation-i")
        lifecycleScope.launch(Dispatchers.IO) {
            types= connect(url, Array<String>::class.java, ::simplifyTypes)!!.toList()
        }.join()
        Log.i("TYPES TEST", types.toString())
        return types
    }

    suspend fun getPokemon(species:String):PokemonEntry?{
        var pk :PokemonEntry? = null
        val url = URL(PokeApiEndpoint.POKEMON.url + "/${species}")
        lifecycleScope.launch(Dispatchers.IO) {
            pk = connect(url, PokemonEntry::class.java, ::simplifyPokemon);
        }.join()
        Log.i("Pokemon TEST", pk.toString())
        return pk
    }

    suspend fun getPkMoves(species: String): List<PkMove>{
        var pkMoves = listOf<PkMove>()
        val url = URL(PokeApiEndpoint.POKEMON.url + "/${species}")
        lifecycleScope.launch(Dispatchers.IO){
            pkMoves = connect(url, Array<PkMove>::class.java, ::simplifyMoves)!!.toList()
        }.join()
        Log.i("Pokemon Moves TEST", pkMoves.toString())
        return pkMoves
    }

    suspend fun getMove(moveName: String): MoveEntry?{
        var move: MoveEntry? = null
        val url = URL(PokeApiEndpoint.MOVE.url + "/${moveName}")
        lifecycleScope.launch(Dispatchers.IO){
            move = connect(url, MoveEntry::class.java, ::simplifyMove)
        }.join()
        Log.i("Move TEST", move.toString())
        return move
    }

    suspend fun getTypeRelations(type: String): TypeRelation? {
        var relations: TypeRelation? = null
        val url = URL(PokeApiEndpoint.TYPE.url + "/${type}")
        lifecycleScope.launch(Dispatchers.IO) {
            relations = connect(url, TypeRelation::class.java, ::simplifyTypeRelations)
        }.join()
        Log.i("TYPE RELATION TEST", relations.toString())
        return relations
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
                }else{}
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("NETWORK ERROR", e.toString())
            } finally {
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

data class Type(
    val type: String
)