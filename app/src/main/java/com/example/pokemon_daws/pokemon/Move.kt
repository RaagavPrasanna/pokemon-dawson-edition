package com.example.pokemon_daws.pokemon

class Move(
        val name: String,
        val accuracy: Int,
        var pp: Int,
        val maxPP: Int,
        val power: Int,
        val heal: Int,
        val damageClass: DamageClass,
        val type: Type,
        //Make target enum ?
        val Target: String,
        val ailment: IStatus? = null,
        val ailmentChance: Int,
){

}