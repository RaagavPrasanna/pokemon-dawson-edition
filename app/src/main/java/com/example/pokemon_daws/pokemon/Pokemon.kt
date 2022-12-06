package com.example.pokemon_daws.pokemon

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
<<<<<<< HEAD
import com.example.pokemon_daws.controllers.Pokemon_Math.CalculateHP
import com.example.pokemon_daws.controllers.Pokemon_Math.CalculateStat
import com.example.pokemon_daws.controllers.Pokemon_Math.CurrentLevel
=======
import com.example.pokemon_daws.Controllers.Pokemon_Math.CalculateHP
import com.example.pokemon_daws.Controllers.Pokemon_Math.CalculateStat
import com.example.pokemon_daws.Controllers.Pokemon_Math.CurrentLevel
>>>>>>> 1f3d85b414930f02c91ff0bb3b5d39de59765a51

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
    val maxHp: Int get() = CalculateHP(baseMaxHp.toDouble(), level)
    val attack: Int get() = CalculateStat(baseAttack.toDouble(), level)
    val defense: Int get() = CalculateStat(baseDefense.toDouble(), level)
    val specialAttack: Int get() = CalculateStat(baseSpecialAttack.toDouble(), level)
    val specialDefense: Int get() = CalculateStat(baseSpecialDefense.toDouble(), level)
    val speed: Int get() = CalculateStat(baseSpeed.toDouble(), level)
    val level: Int get() = CurrentLevel(experience.toDouble())
}