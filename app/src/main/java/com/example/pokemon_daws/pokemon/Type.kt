package com.example.pokemon_daws.pokemon

enum class Type {
    NORMAL,
    FIRE,
    WATER,
    ELECTRIC,
    GRASS,
    ICE,
    FIGHTING,
    POISON,
    GROUND,
    FLYING,
    PSYCHIC,
    BUG,
    ROCK,
    GHOST,
    DRAGON;
    companion object {
        fun getType(type: String): Type?{
            return when(type){
                "normal" -> Type.NORMAL
                "fighting" -> Type.FIGHTING
                "flying" -> Type.FLYING
                "poison" -> Type.POISON
                "ground" -> Type.GROUND
                "rock" -> Type.ROCK
                "bug" -> Type.BUG
                "ghost" -> Type.GHOST
                "fire" -> Type.FIRE
                "water" -> Type.WATER
                "grass" -> Type.GRASS
                "electric" -> Type.ELECTRIC
                "psychic" -> Type.PSYCHIC
                "ice" -> Type.ICE
                "dragon" -> Type.DRAGON
                else -> null
            }
        }
    }
}