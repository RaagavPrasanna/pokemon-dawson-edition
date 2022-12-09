package com.example.pokemon_daws.pokemon

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


class Move(
        val name: String,
        val accuracy: Int,
        var pp: Int,
        val maxPP: Int,
        val power: Int,
        val heal: Int,
        val damageClass: DamageClass,
        val type: String,
        val target: String,
){

}