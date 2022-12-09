package com.example.pokemon_daws.pokemon
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.pokemon_daws.Controllers.ApiController
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import com.example.pokemon_daws.Controllers.Pokemon_Math.CalculateHP

class PokemonFactory(private val lifecycleScope: LifecycleCoroutineScope) {
    private val api = ApiController(lifecycleScope)

    suspend fun createPokemon(level: Int, species: String, name: String? = null): Pokemon{

        val pkEntry = api.getPokemon(species) ?: throw IOException("Could not connect")
        val allMoves = mutableListOf<Move>()
        val moves = createMove(species, level, allMoves)
        var frontImage: Bitmap
        var backImage: Bitmap


        var url = URL(pkEntry.front_sprite)
        var conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.connect()
        if(conn.responseCode == HttpsURLConnection.HTTP_OK) {
            val inputStream = conn.inputStream
            frontImage = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
        } else {
            throw java.lang.IllegalArgumentException("fetch did not work for front sprite")
        }

        conn.disconnect()

        url = URL(pkEntry.back_sprite)
        conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.connect()
        if(conn.responseCode == HttpsURLConnection.HTTP_OK) {
            val inputStream = conn.inputStream
            backImage = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
        } else {
            throw java.lang.IllegalArgumentException("fetch did not work for back sprite")
        }

        conn.disconnect()

        return Pokemon(
            species,
            name?: species,
            getExperience(level),
            pkEntry.base_exp_reward,
            pkEntry.types,
            CalculateHP(pkEntry.base_maxHp.toDouble(), level),
            pkEntry.base_maxHp,
            pkEntry.base_attack,
            pkEntry.baseSpecialAttack,
            pkEntry.base_defense,
            pkEntry.baseSpecialDefense,
            pkEntry.base_speed,
            moves,
            allMoves,
            frontImage,
            backImage)
    }

    private suspend fun createMove(species: String, lvl: Int, allMoves: MutableList<Move>): MutableList<Move>{
        val moveListData = api.getPkMoves(species)
        val moves = mutableListOf<Move>()
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
                apiMoveData.description
            )
            if(moveData.level <= lvl){
                Log.i("move",move.name)
                moves.add(move)
            }
            allMoves.add(move)
        }
        moves.subList(0, if(moves.size >= 4) 3 else moves.size - 1)
        return moves
    }

    private fun getExperience(lvl: Int) = lvl * lvl * lvl
}