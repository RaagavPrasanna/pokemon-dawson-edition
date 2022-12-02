package com.example.pokemon_daws.utils
import PokeApiEndpoint
import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import simplifyPokedexEntries
import simplifyTypeRelations
import java.net.URL
import javax.net.ssl.HttpsURLConnection

private val GSON: Gson = GsonBuilder().setPrettyPrinting().serializeNulls().create()

suspend fun fetchAllPokemon(): List<PokedexEntry> {
    val url = URL(PokeApiEndpoint.POKEDEX.url + "/2")
    val response = connect(url, Array<PokedexEntry>::class.java, ::simplifyPokedexEntries)!!.toList();
    Log.i("POKEDEX TEST", response.toString())
    return response
}

suspend fun fetchTypeRelations(type: String): TypeRelation {
    val url = URL(PokeApiEndpoint.TYPE.url + "/${type}")
    val response = connect(url, TypeRelation::class.java, ::simplifyTypeRelations)
    Log.i("TYPE RELATION TEST", response.toString())
    return response!!

}

private suspend fun<T> connect(url: URL, jsonClass: Class<T>, simplifier: (apiResponse:String) -> String):T? {
    var pokedex : T? = null
    withContext(Dispatchers.IO) {
        val conn = url.openConnection() as HttpsURLConnection
        try {
            conn.requestMethod = "GET"
            conn.connect()
            if (conn.responseCode == HttpsURLConnection.HTTP_OK) {
                val response = conn.inputStream.bufferedReader().use { it.readText() }
                pokedex = GSON.fromJson(simplifier(response), jsonClass)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("NETWORK ERROR", e.toString())
            throw e
        } finally {
            conn.disconnect()
        }
    }
    return pokedex
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