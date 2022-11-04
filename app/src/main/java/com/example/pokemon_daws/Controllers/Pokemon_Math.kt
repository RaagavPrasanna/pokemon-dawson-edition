package com.example.pokemon_daws.Controllers

import kotlin.math.floor
import kotlin.math.roundToInt
import kotlin.random.Random
import kotlin.math.pow

// Math formulas that affect Pokemon behaviour.
public final class Pokemon_Math {
    fun CalculatePhysicalDamage(attackerLevel: Double, attackerAttack: Double, defenderDefense: Double, movePower: Double): Int {
        val levelContribution = ((2 * attackerLevel) / 5 + 2) / 50;
        val attDefRatio = attackerAttack / defenderDefense;
        val damage: Int = (levelContribution * movePower * attDefRatio + 2).roundToInt();
        return damage;
    }

    fun CalculateSpecialDamage(attackerLevel: Double, attackerAttackSp: Double, defenderDefenseSp: Double, movePower: Double): Int {
        val levelContribution = ((2 * attackerLevel) / 5 + 2) / 50;
        val attDefRatio = attackerAttackSp / defenderDefenseSp;
        val damage: Int = (levelContribution * movePower * attDefRatio + 2).roundToInt();
        return damage;
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
        return floor(experience.pow(1.0 / 3.0)).roundToInt();
    }
}