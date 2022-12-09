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
    //@Test fun calculateDamageTest() {
    //    var pk1: Pokemon
    //    var pk2: Pokemon
    //    MainActivity.pkFactory = PokemonFactory()
    //    runBlocking {
    //        launch {
    //            runBlocking {
    //                launch(newSingleThreadContext("testThread")) {
    //                    pk1 = MainActivity.pkFactory.createPokemon(5, "bulbasoar")
    //                    pk2 = MainActivity.pkFactory.createPokemon(5, "charmander")
    //                }
    //            }.join()
    //        }
    //    }
    //    //val damage = Pokemon_Math.CalculateDamage(pk1, pk2, pk1.allMoves[0])
    //    //Log.i("damage", damage.toString())
    //    assertEquals(4, 2 + 2)
    //}

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