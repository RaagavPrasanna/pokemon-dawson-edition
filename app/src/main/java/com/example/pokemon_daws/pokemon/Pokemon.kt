package com.example.pokemon_daws.pokemon

class Pokemon(
    val species: String,
    val name: String,
    var level: Int,
    var experience: Int,
    var baseExperienceReward: Int,
    var types: List<Type>,
    var hp: Int,
    var maxHp: Int,
    var attack: Int,
    var defense: Int,
    var specialAttack: Int,
    var specialDefense: Int,
    var speed: Int,
    var moves: MutableList<Move>,
    var status: IStatus
) {

}