package com.example.pokemon_daws.controllers

import androidx.lifecycle.LifecycleCoroutineScope
import com.example.pokemon_daws.utils.PokedexEntry
import androidx.lifecycle.lifecycleScope
import com.example.pokemon_daws.utils.fetchAllPokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ApiController(private val lifecycleScope: LifecycleCoroutineScope) {
     fun getAllPokemon(): List<PokedexEntry> {
        var entries = listOf<PokedexEntry>()
        lifecycleScope.launch(Dispatchers.IO) {
            launch { entries = fetchAllPokemon() }.join()
        }
        return entries
    }
}