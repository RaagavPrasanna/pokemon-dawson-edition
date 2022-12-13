package com.example.pokemon_daws.pokemon

import android.graphics.Bitmap
import com.example.pokemon_daws.Controllers.PokemonMath.calculateHP
import com.example.pokemon_daws.Controllers.PokemonMath.calculateStat
import com.example.pokemon_daws.Controllers.PokemonMath.currentLevel

class Pokemon(
    val species: String,
    var name: String,
    var experience: Int,
    var baseExperienceReward: Int,
    var types: List<String>,
    var hp: Int,
    private var baseMaxHp: Int,
    private var baseAttack: Int,
    private var baseDefense: Int,
    private var baseSpecialAttack: Int,
    private var baseSpecialDefense: Int,
    private var baseSpeed: Int,
    var moves: MutableList<Move>,
    val allMoves:List<Move>,
    var frontImage: Bitmap,
    var backImage: Bitmap,
    var frontUrl: String,
    var backUrl: String
) {
    val maxHp: Int get() = calculateHP(baseMaxHp.toDouble(), level)
    val attack: Int get() = calculateStat(baseAttack.toDouble(), level)
    val defense: Int get() = calculateStat(baseDefense.toDouble(), level)
    val specialAttack: Int get() = calculateStat(baseSpecialAttack.toDouble(), level)
    val specialDefense: Int get() = calculateStat(baseSpecialDefense.toDouble(), level)
    val speed: Int get() = calculateStat(baseSpeed.toDouble(), level)
    val level: Int get() = currentLevel(experience.toDouble())
}