package com.example.pokemon_daws.pokemon
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.pokemon_daws.Controllers.ApiController
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import com.example.pokemon_daws.Controllers.PokemonMath.calculateHP

class PokemonFactory {
    private val api = ApiController()

    suspend fun createPokemon(level: Int, species: String, name: String? = null): Pokemon{

        val pkEntry = api.getPokemon(species) ?: throw IOException("Could not connect")
        val allMoves = mutableListOf<Move>()
        val moves = createMove(species, level, allMoves)
        var frontImage: Bitmap? = null
        var backImage: Bitmap? = null


        var url = URL(pkEntry.front_sprite)
        var conn = url.openConnection() as HttpURLConnection
        try {
            conn.requestMethod = "GET"
            conn.connect()
            if (conn.responseCode == HttpsURLConnection.HTTP_OK) {
                val inputStream = conn.inputStream
                frontImage = BitmapFactory.decodeStream(inputStream)
                inputStream.close()
            } else {
                throw java.lang.IllegalArgumentException("fetch did not work for front sprite")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("NETWORK ERROR", e.toString())
        }

        conn.disconnect()

        url = URL(pkEntry.back_sprite)
        conn = url.openConnection() as HttpURLConnection
        try {
            conn.requestMethod = "GET"
            conn.connect()
            if (conn.responseCode == HttpsURLConnection.HTTP_OK) {
                val inputStream = conn.inputStream
                backImage = BitmapFactory.decodeStream(inputStream)
                inputStream.close()
            } else {
                throw java.lang.IllegalArgumentException("fetch did not work for back sprite")
            }
        } catch(e: Exception) {
            e.printStackTrace()
            Log.e("NETWORK ERROR", e.toString())
        }finally {

        }

        conn.disconnect()

        return Pokemon(
            species,
            name?: species,
            getExperience(level),
            pkEntry.base_exp_reward,
            pkEntry.types,
            calculateHP(pkEntry.base_maxHp.toDouble(), level),
            pkEntry.base_maxHp,
            pkEntry.base_attack,
            pkEntry.baseSpecialAttack,
            pkEntry.base_defense,
            pkEntry.baseSpecialDefense,
            pkEntry.base_speed,
            moves,
            allMoves,
            frontImage!!,
            backImage!!,
            pkEntry.front_sprite,
            pkEntry.back_sprite)
    }

    private suspend fun createMove(species: String, lvl: Int, allMoves: MutableList<Move>): MutableList<Move>{
        val moveListData = api.getPkMoves(species)
        var moves = mutableListOf<Move>()
        for (moveData in moveListData){
            val apiMoveData = api.getMove(moveData.move)!!
            val move = Move(
                apiMoveData.name,
                apiMoveData.accuracy,
                apiMoveData.maxPP,
                apiMoveData.maxPP,
                apiMoveData.power,
                apiMoveData.healing,
                if(apiMoveData.damage_class == "special") DamageClass.SPECIAL else DamageClass.PHYSICAL,
                apiMoveData.type,
                apiMoveData.target,
                apiMoveData.description,
                moveData.level
            )
            if(moveData.level <= lvl){
                moves.add(move)
            }
            allMoves.add(move)
        }
        moves = moves.subList(0, if(moves.size >= 4) 4 else (moves.size))
        return moves
    }

    private fun getExperience(lvl: Int) = lvl * lvl * lvl
}