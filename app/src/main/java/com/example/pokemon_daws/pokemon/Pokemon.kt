package com.example.pokemon_daws.pokemon

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.pokemon_daws.Controllers.Pokemon_Math

@Entity
class Pokemon(
    @ColumnInfo(name="species") val species: String,
    @Ignore val name: String,
    @Ignore var experience: Int,
    @ColumnInfo(name="base_experience_reward") var baseExperienceReward: Int,
    @ColumnInfo(name="types") var types: List<Type>,
    @Ignore var hp: Int,
    @ColumnInfo(name="base_max_hp") var baseMaxHp: Int,
    @ColumnInfo(name="base_attack") var baseAttack: Int,
    @ColumnInfo(name="base_defense") var baseDefense: Int,
    @ColumnInfo(name="base_special_attack") var baseSpecialAttack: Int,
    @ColumnInfo(name="base_special_defense") var baseSpecialDefense: Int,
    @ColumnInfo(name="base_speed") var baseSpeed: Int,
    @ColumnInfo(name="moves") var moves: MutableList<Move>,
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
    val maxHp: Int get() = Pokemon_Math.CalculateHP(baseMaxHp.toDouble(), level)
    val attack: Int get() = Pokemon_Math.CalculateStat(baseAttack.toDouble(), level)
    val defense: Int get() = Pokemon_Math.CalculateStat(baseDefense.toDouble(), level)
    val specialAttack: Int get() = Pokemon_Math.CalculateStat(baseSpecialAttack.toDouble(), level)
    val specialDefense: Int get() = Pokemon_Math.CalculateStat(baseSpecialDefense.toDouble(), level)
    val speed: Int get() = Pokemon_Math.CalculateStat(baseSpeed.toDouble(), level)
    val level: Int get() = Pokemon_Math.CurrentLevel(experience.toDouble())
}