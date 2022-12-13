package com.example.pokemon_daws

import com.example.pokemon_daws.Controllers.PokemonMath
import com.example.pokemon_daws.pokemon.Pokemon
import org.junit.Test
import org.junit.Assert.*

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
class ExampleUnitTest {

}
class PokemonMathTests {
    @Test fun calculateDamageTest() {
        var pk2: Pokemon
        assertEquals(4, 2 + 2)
    }

    @Test
    fun AttemptCapture_fail() {
        assertFalse(PokemonMath.attemptCapture(10.0, 10.0))
    }

    @Test
    fun AttemptCapture_succeed() {
        assertTrue(PokemonMath.attemptCapture(0.0, 10.0))
    }

    @Test
    fun CurrentLevel_1() {
        assertEquals(PokemonMath.currentLevel(125.0), 5)
    }

    @Test
    fun CurrentLevel_2() {
        assertEquals(PokemonMath.currentLevel(64.0), 4)
    }

    @Test
    fun CalculateStat() {
        assertEquals(PokemonMath.calculateStat(10.0, 2), 5)
    }

    @Test
    fun CalculateHP() {
        assertEquals(PokemonMath.calculateHP(30.0, 2), 13)
    }
}