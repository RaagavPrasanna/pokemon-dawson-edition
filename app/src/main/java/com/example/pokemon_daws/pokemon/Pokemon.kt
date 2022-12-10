package com.example.pokemon_daws.pokemon

import android.graphics.Bitmap
import com.example.pokemon_daws.Controllers.Pokemon_Math.CalculateHP
import com.example.pokemon_daws.Controllers.Pokemon_Math.CalculateStat
import com.example.pokemon_daws.Controllers.Pokemon_Math.CurrentLevel

class Pokemon(
    val species: String,
    val name: String,
    var experience: Int,
    var baseExperienceReward: Int,
    var types: List<String>,
    var hp: Int,
    var baseMaxHp: Int,
    var baseAttack: Int,
    var baseDefense: Int,
    var baseSpecialAttack: Int,
    var baseSpecialDefense: Int,
    var baseSpeed: Int,
    var moves: MutableList<Move>,
    val allMoves:List<Move>,
    var frontImage: Bitmap,
    var backImage: Bitmap,
    var frontUrl: String,
    var backUrl: String
) {
    val maxHp: Int get() = CalculateHP(baseMaxHp.toDouble(), level)
    val attack: Int get() = CalculateStat(baseAttack.toDouble(), level)
    val defense: Int get() = CalculateStat(baseDefense.toDouble(), level)
    val specialAttack: Int get() = CalculateStat(baseSpecialAttack.toDouble(), level)
    val specialDefense: Int get() = CalculateStat(baseSpecialDefense.toDouble(), level)
    val speed: Int get() = CalculateStat(baseSpeed.toDouble(), level)
    val level: Int get() = CurrentLevel(experience.toDouble())
}