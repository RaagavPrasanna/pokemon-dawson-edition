package com.example.pokemon_daws.Controllers

import android.content.Context
import com.example.pokemon_daws.pokemon.Type
import kotlin.math.floor
import kotlin.math.roundToInt
import kotlin.random.Random
import kotlin.math.pow
import com.example.pokemon_daws.utils.Json
import com.example.pokemon_daws.utils.TypeRelations
import android.util.Log

// Math formulas that affect Pokemon behaviour.
public final class Pokemon_Math () {
    fun CalculateDamage(attackerLevel: Double, attackerAttack: Double, defenderDefense: Double, movePower: Double, moveType: Type, attackerTypes: List<Type>
, attackerRelations: TypeRelations, defenderTypes: List<Type>): Int {
        val levelContribution = ((2 * attackerLevel) / 5 + 2) / 50;
        val attDefRatio = attackerAttack / defenderDefense;
        var damage = (levelContribution * movePower * attDefRatio + 2);
        var effectiveness: String?;

        if (moveType in attackerTypes) {
            damage = damage * 1.5;
        }
        Log.i("After move type",damage.toString())
        defenderTypes.forEach{
            val defenderType = it
            when(defenderType) {
                Type.BUG -> effectiveness = attackerRelations.bug;
                Type.DRAGON -> effectiveness = attackerRelations.dragon;
                Type.ELECTRIC -> effectiveness = attackerRelations.electric;
                Type.FIGHTING -> effectiveness = attackerRelations.fighting;
                Type.FIRE -> effectiveness = attackerRelations.fire;
                Type.NORMAL -> effectiveness = attackerRelations.normal;
                Type.FLYING -> effectiveness = attackerRelations.flying;
                Type.GHOST -> effectiveness = attackerRelations.ghost;
                Type.GRASS -> effectiveness = attackerRelations.grass;
                Type.GROUND -> effectiveness = attackerRelations.ground;
                Type.ICE -> effectiveness = attackerRelations.ice;
                Type.POISON -> effectiveness = attackerRelations.poison;
                Type.PSYCHIC -> effectiveness = attackerRelations.psychic;
                Type.ROCK -> effectiveness = attackerRelations.rock;
                Type.WATER -> effectiveness = attackerRelations.water;
            }
            if (effectiveness == "not_very_effective") {
                damage = damage / 2;

            }
            else if (effectiveness == "super_effective") {
                damage = damage * 2;
            }
            else if (effectiveness == "no_effect"){
                damage = 0.0;
            }
            Log.i("Effectiveness",effectiveness + " on " + defenderType.toString())
        }

        val finalDamage: Int = damage.roundToInt();

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

    fun CalculateStat(baseValue: Double, level: Double): Int {
        val calc1 = (baseValue + 10) * level;
        val calc2 = (calc1 / 50) + 5;
        return floor(calc2).toInt();
    }

    fun CalculateHP(baseValue: Double, level: Double): Int {
        val calc1 = (baseValue + 10) * level;
        val calc2 = (calc1 / 50) + level + 10;
        return floor(calc2).toInt();
    }
}