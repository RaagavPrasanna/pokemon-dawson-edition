package com.example.pokemon_daws.pokemon
import android.content.Context
import  com.example.pokemon_daws.utils.Json

class PokemonFactory(context: Context) {
    val json = Json(context)
    fun createPokemon(level: Int, species: String, name: String? = null): Pokemon{
        //TODO("Add check to see if species is in list")
        val pkData = json.readJsonPokemon("$species.json")
        val moves = createMove(species, level)
        return Pokemon(
            species,
            name?: species,
            getExperience(level),
            pkData.baseExperienceReward,
            pkData.types.map { type -> Type.getType(type)!! },
            pkData.baseStateMaxHp,
            pkData.baseStateMaxHp,
            pkData.baseStateAttack,
            pkData.baseStatSpecialAttack,
            pkData.baseStatDefense,
            pkData.baseStatSpecialDefense,
            pkData.baseStatSpeed,
            moves,
            )
    }

    private fun createMove(species: String, lvl: Int): MutableList<Move>{
        val moveListData = json.readJsonMoveList("$species.json")
        val moves = mutableListOf<Move>()
        for (move in moveListData){
            val moveData = json.readJsonMove("${move.move}.json")
            if(moveData != null && move.level <= lvl){
                moves.add(Move(
                    move.move,
                    moveData.accuracy,
                    moveData.maxPP,
                    moveData.maxPP,
                    moveData.power,
                    moveData.heal,
                    if(moveData.damageClass == "SPECIAL") DamageClass.SPECIAL else DamageClass.PHYSICAL,
                    Type.getType(moveData.type)!!,
                    moveData.target,
                ))
            }
        }
        moves.subList(0, if(moves.size >= 4) 3 else moves.size - 1)
        return moves
    }

    private fun getExperience(lvl: Int) = lvl * lvl * lvl
}