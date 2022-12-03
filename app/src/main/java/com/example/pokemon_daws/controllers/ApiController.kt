package com.example.pokemon_daws.controllers

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.pokemon_daws.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.reflect.Type


class ApiController(private val lifecycleScope: LifecycleCoroutineScope) {
    fun getAllPokemon(): List<PokedexEntry> {
        var entries = listOf<PokedexEntry>()
        lifecycleScope.launch(Dispatchers.IO) {
            launch { entries = fetchAllPokemon() }.join()
            launch { fetchTypes() }.join()
            launch { fetchPokemon("bulbasaur") }.join()
            launch { fetchPkMoves("bulbasaur") }.join()
            launch { fetchMove("tackle") }.join()

        }
        return entries
    }

    fun getTypeRelations(type: String): TypeRelation? {
        var relations: TypeRelation? = null
        lifecycleScope.launch(Dispatchers.IO) {
            launch { relations = fetchTypeRelations("ground") }.join()
        }
        return relations
    }
}