package com.example.pokemon_daws.pokemon

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.pokemon_daws.Controllers.Pokemon_Math

 class Pokemon(
    val species: String,
    val name: String,
    var experience: Int,
    var baseExperienceReward: Int,
    var types: List<Type>,
    var hp: Int,
    var baseMaxHp: Int,
    var baseAttack: Int,
    var baseDefense: Int,
    var baseSpecialAttack: Int,
    var baseSpecialDefense: Int,
    var baseSpeed: Int,
    var moves: MutableList<Move>,
) {
    val maxHp: Int get() = Pokemon_Math.CalculateHP(baseMaxHp.toDouble(), level)
    val attack: Int get() = Pokemon_Math.CalculateStat(baseAttack.toDouble(), level)
    val defense: Int get() = Pokemon_Math.CalculateStat(baseDefense.toDouble(), level)
    val specialAttack: Int get() = Pokemon_Math.CalculateStat(baseSpecialAttack.toDouble(), level)
    val specialDefense: Int get() = Pokemon_Math.CalculateStat(baseSpecialDefense.toDouble(), level)
    val speed: Int get() = Pokemon_Math.CalculateStat(baseSpeed.toDouble(), level)
    val level: Int get() = Pokemon_Math.CurrentLevel(experience.toDouble())
}