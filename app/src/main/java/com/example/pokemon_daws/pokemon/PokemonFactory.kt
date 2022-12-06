package com.example.pokemon_daws.pokemon
import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.pokemon_daws.Controllers.ApiController
import com.example.pokemon_daws.Controllers.PokemonEntry
import  com.example.pokemon_daws.utils.Json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokemonFactory(context: Context, private val lifecycleScope: LifecycleCoroutineScope) {
    val api = ApiController(lifecycleScope)

    suspend fun createPokemon(level: Int, species: String, name: String? = null): Pokemon{
        val pkEntry = api.getPokemon(species)!!

        val moves = createMove(species, level)
        return Pokemon(
            species,
            name?: species,
            getExperience(level),
            pkEntry.base_exp_reward,
            pkEntry.types.map { type -> Type.getType(type)!! },
            pkEntry.base_maxHp,
            pkEntry.base_maxHp,
            pkEntry.base_attack,
            pkEntry.baseSpecialAttack,
            pkEntry.base_defense,
            pkEntry.baseSpecialDefense,
            pkEntry.base_speed,
            moves,
            )
    }

    private suspend fun createMove(species: String, lvl: Int): MutableList<Move>{
        val moveListData = api.getPkMoves(species)
        val moves = mutableListOf<Move>()
        for (moveData in moveListData){
            var move = api.getMove(moveData.move)!!
            if(moveData.level <= lvl){
                moves.add(Move(
                    move.name,
                    move.accuracy,
                    move.maxPP,
                    move.maxPP,
                    move.power,
                    move.healing,
                    if(move.damage_class == "special") DamageClass.SPECIAL else DamageClass.PHYSICAL,
                    Type.getType(move.type)!!,
                    move.target,
                ))
            }
        }
        moves.subList(0, if(moves.size >= 4) 3 else moves.size - 1)
        return moves
    }

    private fun getExperience(lvl: Int) = lvl * lvl * lvl
}