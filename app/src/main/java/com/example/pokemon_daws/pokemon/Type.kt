package com.example.pokemon_daws.pokemon

import com.google.gson.annotations.SerializedName

enum class Type {
    @SerializedName("1") NORMAL,
    @SerializedName("2") FIRE,
    @SerializedName("3") WATER,
    @SerializedName("4") ELECTRIC,
    @SerializedName("5") GRASS,
    @SerializedName("6") ICE,
    @SerializedName("7") FIGHTING,
    @SerializedName("8") POISON,
    @SerializedName("9") GROUND,
    @SerializedName("10") FLYING,
    @SerializedName("11") PSYCHIC,
    @SerializedName("12") BUG,
    @SerializedName("13") ROCK,
    @SerializedName("14") GHOST,
    @SerializedName("15") DRAGON;
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