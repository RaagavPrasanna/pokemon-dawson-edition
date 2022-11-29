package com.example.pokemon_daws.utils
import PokeApiEndpoint
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import simplifyPokedexEntries
import java.net.URL
import javax.net.ssl.HttpsURLConnection

private val GSON: Gson = GsonBuilder().setPrettyPrinting().create()

suspend fun fetchAllPokemon(): String{
    val url = URL(PokeApiEndpoint.POKEDEX.url + "/2")
    val response = connect(url, Array<PokedexEntry>::class.java, ::simplifyPokedexEntries);
    Log.i("TEST", response)
    return response
}

private suspend fun connect(url: URL, jsonClass: Class<*>, simplifier: (apiResponse:String) -> String):String {
    var response : String? = null
    return withContext(Dispatchers.IO) {
        val conn = url.openConnection() as HttpsURLConnection
        try {
            conn.requestMethod = "GET"
            conn.connect()
            if (conn.responseCode == HttpsURLConnection.HTTP_OK) {
                response = conn.inputStream.bufferedReader().use { it.readText() }
                return@withContext simplifier(response!!)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("NETWORK ERROR", e.toString())
            throw e
        } finally {
            conn.disconnect()
        }
        return@withContext response!!
    }
}

@Entity(tableName = "pokedex_entry")
data class PokedexEntry(
    @ColumnInfo(name = "number")val number: Int,
    @PrimaryKey @ColumnInfo(name = "name")val name: String
)