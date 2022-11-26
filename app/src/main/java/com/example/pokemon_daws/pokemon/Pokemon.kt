package com.example.pokemon_daws.pokemon

class Pokemon(
    val species: String,
    val name: String,
    var level: Int,
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
    var status: IStatus?
) {
    val maxHp: Int get() = TODO()
    val attack: Int get() = TODO()
    val defense: Int get() = TODO()
    val specialAttack: Int get() = TODO()
    val specialDefense: Int get() = TODO()
    val speed: Int get() = TODO()
}