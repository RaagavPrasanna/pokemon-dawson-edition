package com.example.pokemon_daws.pokemon

class Move(
        val accuracy: Int,
        var pp: Int,
        val maxPP: Int,
        val power: Int,
        val heal: Int,
        val damageClass: DamageClass,
        val type: Type,
        val Target: Pokemon,
        val ailment: IStatus,
        val ailmentChance: Int,
){

}