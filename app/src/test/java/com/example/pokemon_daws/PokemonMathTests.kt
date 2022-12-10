package com.example.pokemon_daws

import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.pokemon_daws.Controllers.Pokemon_Math
import com.example.pokemon_daws.pokemon.Pokemon
import com.example.pokemon_daws.pokemon.PokemonFactory
import kotlinx.coroutines.*
import org.junit.Test
import org.junit.Assert.*

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
class ExampleUnitTest {

}
class PokemonMathTests {
    @Test fun calculateDamageTest() {
        //var pk1: Pokemon = Pokemon("species1", "pokemon1", 125, 5, listOf(), )
        var pk2: Pokemon
        assertEquals(4, 2 + 2)
    }

    @Test
    fun AttemptCapture_fail() {
        assertFalse(Pokemon_Math.AttemptCapture(10.0, 10.0))
    }

    @Test
    fun AttemptCapture_succeed() {
        assertTrue(Pokemon_Math.AttemptCapture(0.0, 10.0))
    }

    @Test
    fun CurrentLevel_1() {
        assertEquals(Pokemon_Math.CurrentLevel(125.0), 5)
    }

    @Test
    fun CurrentLevel_2() {
        assertEquals(Pokemon_Math.CurrentLevel(64.0), 4)
    }

    @Test
    fun CalculateStat() {
        assertEquals(Pokemon_Math.CalculateStat(10.0, 2), 5)
    }

    @Test
    fun CalculateHP() {
        assertEquals(Pokemon_Math.CalculateHP(30.0, 2), 13)
    }
}