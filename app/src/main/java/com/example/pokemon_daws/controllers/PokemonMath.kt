package com.example.pokemon_daws.controllers

import com.example.pokemon_daws.pokemon.Type
import kotlin.math.floor
import kotlin.math.roundToInt
import kotlin.random.Random
import kotlin.math.pow

// Math formulas that affect Pokemon behaviour.
public final class PokemonMath () {
    fun CalculateDamage(attackerLevel: Double, attackerAttack: Double, defenderDefense: Double, movePower: Double, moveType: Type, attackerTypes: List<Type>
, defenderTypes: List<Type>): Int {
        val levelContribution = ((2 * attackerLevel) / 5 + 2) / 50;
        val attDefRatio = attackerAttack / defenderDefense;
        var damage = (levelContribution * movePower * attDefRatio + 2);
        var effectiveness: String? = null;

        if (moveType in attackerTypes) {
            damage = damage * 1.5;
        }
        //TODO(Implement type relations once the singleton has been created)
        //Log.i("After move type",damage.toString())
        //defenderTypes.forEach{
        //    val defenderType = it
        //    when(defenderType) {
        //        Type.BUG -> effectiveness = moveRelations.bug;
        //        Type.DRAGON -> effectiveness = moveRelations.dragon;
        //        Type.ELECTRIC -> effectiveness = moveRelations.electric;
        //        Type.FIGHTING -> effectiveness = moveRelations.fighting;
        //        Type.FIRE -> effectiveness = moveRelations.fire;
        //        Type.NORMAL -> effectiveness = moveRelations.normal;
        //        Type.FLYING -> effectiveness = moveRelations.flying;
        //        Type.GHOST -> effectiveness = moveRelations.ghost;
        //        Type.GRASS -> effectiveness = moveRelations.grass;
        //        Type.GROUND -> effectiveness = moveRelations.ground;
        //        Type.ICE -> effectiveness = moveRelations.ice;
        //        Type.POISON -> effectiveness = moveRelations.poison;
        //        Type.PSYCHIC -> effectiveness = moveRelations.psychic;
        //        Type.ROCK -> effectiveness = moveRelations.rock;
        //        Type.WATER -> effectiveness = moveRelations.water;
        //    }
        //    if (effectiveness == "not_very_effective") {
        //        damage = damage / 2;
//
        //    }
        //    else if (effectiveness == "super_effective") {
        //        damage = damage * 2;
        //    }
        //    else if (effectiveness == "no_effect"){
        //        damage = 0.0;
        //    }
        //    Log.i("Effectiveness",effectiveness + " on " + defenderType.toString())
        //}

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