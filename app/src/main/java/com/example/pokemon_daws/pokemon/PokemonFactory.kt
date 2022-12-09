package com.example.pokemon_daws.pokemon
import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.pokemon_daws.Controllers.ApiController
import java.io.IOException
import com.example.pokemon_daws.Controllers.Pokemon_Math.CalculateHP

class PokemonFactory(private val lifecycleScope: LifecycleCoroutineScope) {
    private val api = ApiController(lifecycleScope)

    suspend fun createPokemon(level: Int, species: String, name: String? = null): Pokemon{

        val pkEntry = api.getPokemon(species) ?: throw IOException("Could not connect")
        val allMoves = mutableListOf<Move>()
        val moves = createMove(species, level, allMoves)

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
            )
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