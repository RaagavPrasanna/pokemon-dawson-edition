package com.example.pokemon_daws.pokemon
import android.content.Context
import  com.example.pokemon_daws.utils.Json

class PokemonFactory(context: Context) {
    val json = Json(context)
    fun createPokemon(level: Int, species: String, name: String? = null): Pokemon{
        //TODO("Add check to see if species is in list")
        val pkData = json.readJsonPokemon("$species.json")
        val moves = createMove(species)
        return Pokemon(
            species,
            name?: species,
            level,
            TODO("experience calls math method"),
            pkData.baseExperienceReward,
            TODO("Types"),
            pkData.baseStateMaxHp,
            pkData.baseStateMaxHp,
            pkData.baseStateAttack,
            pkData.baseStatSpecialAttack,
            pkData.baseStatDefense,
            pkData.baseStatSpecialDefense,
            pkData.baseStatSpeed,
            moves,
            null
            )
    }

    private fun createMove(species: String): MutableList<Move>{
        val moveListData = json.readJsonMoveList("$species.json")
        val moves = mutableListOf<Move>()
        for (move in moveListData){
            val moveData = json.readJsonMove("${move.move}.json")
            moves.add(Move(
                moveData.accuracy,
                moveData.maxPP,
                moveData.maxPP,
                moveData.power,
                moveData.heal,
                if(moveData.damageClass == "SPECIAL") DamageClass.SPECIAL else DamageClass.PHYSICAL,
                Type.getType(moveData.type)!!,
                moveData.target,
                null,//TODO("Status")
                moveData.ailmentChance
            ))
        }
        return moves
    }

//    private fun create
}