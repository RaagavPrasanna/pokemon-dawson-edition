package com.example.pokemon_daws.Controllers

import com.example.pokemon_daws.MainActivity
import com.example.pokemon_daws.pokemon.*
import kotlin.math.floor
import kotlin.math.roundToInt
import kotlin.random.Random
import kotlin.math.pow

// Math formulas that affect Pokemon behaviour.
object PokemonMath{
    fun calculateDamage(attackerPk: Pokemon, defenderPk: Pokemon, move: Move): Int {
        val levelContribution = ((2 * attackerPk.level)*1.0 / 5 + 2) / 50
        val attDefRatio = if(move.damageClass == DamageClass.PHYSICAL){
            attackerPk.attack*1.0 / defenderPk.defense
        }else{
            attackerPk.specialAttack*1.0 / defenderPk.specialDefense
        }
        val damage = (levelContribution * move.power * attDefRatio + 2)
        val finalDamage = if(move.type in attackerPk.types) damage * 1.5 else damage * 1.0
        var effectiveness = 1.0
        for (type in defenderPk.types){
            var rate = 1.0
            if(MainActivity.ts.getEffectiveType(move.type, type) != null){
                rate = MainActivity.ts.getEffectiveType(move.type, type)!!.damageRate
            }
             effectiveness *= rate
        }
        return if((finalDamage * effectiveness).toInt() < 1) 1 else (finalDamage * effectiveness).toInt()
    }

    fun attemptCapture(enemyHP: Double, enemyHPMax: Double): Boolean {
        val captureChance: Double = 1 - (enemyHP / enemyHPMax)
        val captureRoll = Random.nextDouble()

        if (captureRoll < captureChance) {
            return true
        }
        return false
    }

    fun currentLevel(experience: Double): Int {
        return experience.pow(1.0 / 3.0).roundToInt()
    }

    fun calculateStat(baseValue: Double, level: Int): Int {
        val calc1 = (baseValue + 10) * level
        val calc2 = (calc1 / 50) + 5
        return floor(calc2).toInt()
    }

    fun calculateHP(baseValue: Double, level: Int): Int {
        val calc1 = (baseValue + 10) * level
        val calc2 = (calc1 / 50) + level + 10
        return floor(calc2).toInt()
    }
}