package com.example.pokemon_daws.pokemon

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.pokemon_daws.Controllers.Pokemon_Math


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
    val description: String,
){

    fun executeMove(currentPk: Pokemon, oppositePk: Pokemon){
        if(this.power == 0){
            return
        }else if(this.heal > 0){
            currentPk.hp= if(currentPk.hp + this.heal > currentPk.maxHp) currentPk.maxHp else currentPk.hp + this.heal
            pp -= 1
        }else{
            oppositePk.hp -= Pokemon_Math.CalculateDamage(currentPk, oppositePk, this)
            pp -= 1
        }
    }
}