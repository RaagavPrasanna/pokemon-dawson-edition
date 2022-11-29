package com.example.pokemon_daws.pokemon

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity
class Move(
        @ColumnInfo(name="name") val name: String,
        @ColumnInfo(name="accuracy") val accuracy: Int,
        @Ignore var pp: Int,
        @ColumnInfo(name="maxPP") val maxPP: Int,
        @ColumnInfo(name="power") val power: Int,
        @ColumnInfo(name="heal") val heal: Int,
        @ColumnInfo(name="damage_class") val damageClass: DamageClass,
        @ColumnInfo(name="type") val type: Type,
        @ColumnInfo(name="target") val target: String,
){
        @PrimaryKey(autoGenerate = true) var id: Int = 0
}