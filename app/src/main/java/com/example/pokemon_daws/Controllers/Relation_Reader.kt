package com.example.pokemon_daws.Controllers

import com.google.gson.Gson

class Relation_Reader {

    val fireRelationRaw = """{
        "fire": "not_very_effective",
        "water": "not_very_effective",
        "grass": "super_effective",
        "ice": "super_effective",
        "bug": "super_effective",
        "rock": "not_very_effective",
        "dragon": "not_very_effective"
    }"""
    val normalRelationRaw = """{
        "rock": "not_very_effective",
        "ghost": "no_effect"
    }"""

    val fireRelation = Gson().toJson(fireRelationRaw)
}