package com.example.pokemon_daws.Controllers

import android.util.Log
import com.example.pokemon_daws.MainActivity
import org.junit.Assert.*
import org.junit.Test

internal class Pokemon_MathTest {
    @Test suspend fun calculateDamageTest() {
        val pk1 = MainActivity.pkFactory.createPokemon(5, "bulbasoar")
        val pk2 = MainActivity.pkFactory.createPokemon(5, "charmander")
        val damage = Pokemon_Math.CalculateDamage(pk1, pk2, pk1.allMoves[0])
        Log.i("damage", damage.toString())
        assertEquals(4, 2 + 2)
    }
}