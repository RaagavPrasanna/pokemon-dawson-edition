package com.example.pokemon_daws.Controllers

import com.example.pokemon_daws.pokemon.DamageClass
import com.example.pokemon_daws.pokemon.Move
import com.example.pokemon_daws.pokemon.Pokemon
import com.example.pokemon_daws.pokemon.Type
import kotlin.math.floor
import kotlin.math.roundToInt
import kotlin.random.Random
import kotlin.math.pow

// Math formulas that affect Pokemon behaviour.
object Pokemon_Math{
    fun CalculateDamage(attackerPk: Pokemon, defenderPk: Pokemon, move: Move): Int {
        val levelContribution = ((2 * attackerPk.level) / 5 + 2) / 50;
        val attDefRatio = if(move.damageClass == DamageClass.PHYSICAL){
            attackerPk.attack / defenderPk.defense;
        }else{
            attackerPk.specialAttack / defenderPk.specialDefense;
        }
        val damage = (levelContribution * move.power * attDefRatio + 2);
        var finalDamage = (if(move.type in attackerPk.types) damage * 1.5 else damage * 1.0).toInt()
        var effectiveness =
        return finalDamage;
    }

    fun AttemptCapture(enemyHP: Double, enemyHPMax: Double): Boolean {
        val captureChance: Double = 1 - (enemyHP / enemyHPMax);
        val captureRoll = Random.nextDouble();

        if (captureRoll < captureChance) {
            return true;
        }
        return false;
    }

    fun CurrentLevel(experience: Double): Int {
        return floor(experience.pow(1.0 / 3.0)).toInt();
    }

    fun CalculateStat(baseValue: Double, level: Int): Int {
        val calc1 = (baseValue + 10) * level;
        val calc2 = (calc1 / 50) + 5;
        return floor(calc2).toInt();
    }

    fun CalculateHP(baseValue: Double, level: Int): Int {
        val calc1 = (baseValue + 10) * level;
        val calc2 = (calc1 / 50) + level + 10;
        return floor(calc2).toInt();
    }
}