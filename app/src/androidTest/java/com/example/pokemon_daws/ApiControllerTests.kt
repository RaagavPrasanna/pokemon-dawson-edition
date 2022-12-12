package com.example.pokemon_daws

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.pokemon_daws.Controllers.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ApiControllerTests {
    val api = ApiController()
    @Test
    fun getAllPokemonTest() {
        var allPokemon: List<PokedexEntry> = listOf()
        runBlocking {
            launch {
                runBlocking {
                    launch(newSingleThreadContext("testThread")) {
                        allPokemon = api.getAllPokemon()
                    }
                }.join()
            }
        }
        assertTrue(allPokemon.size > 0)
    }

    @Test
    fun getTypesTest() {
        var types: List<String> = listOf()
        runBlocking {
            launch {
                runBlocking {
                    launch(newSingleThreadContext("testThread")) {
                        types = api.getTypes()
                    }
                }.join()
            }
        }
        assertTrue(types.size > 0)
    }

    @Test
    fun getPokemonTest() {
        var pokemon: PokemonEntry? = null
        runBlocking {
            launch {
                runBlocking {
                    launch(newSingleThreadContext("testThread")) {
                        pokemon = api.getPokemon("charmander")
                    }
                }.join()
            }
        }
        assertEquals(pokemon!!.name, "charmander")
    }

    @Test
    fun getPkMoves() {
        var moves: List<PkMove> = listOf()
        runBlocking {
            launch {
                runBlocking {
                    launch(newSingleThreadContext("testThread")) {
                        moves = api.getPkMoves("charmander")
                    }
                }.join()
            }
        }
        assertTrue(moves.size > 0)
    }

    @Test
    fun getMoveTest() {
        var move: MoveEntry? = null
        runBlocking {
            launch {
                runBlocking {
                    launch(newSingleThreadContext("testThread")) {
                        move = api.getMove("bite")
                    }
                }.join()
            }
        }
        assertEquals(move!!.name, "bite")
    }

    @Test
    fun getTypeRelationsTest() {
        var typeRelation: TypeRelation? = null
        runBlocking {
            launch {
                runBlocking {
                    launch(newSingleThreadContext("testThread")) {
                        typeRelation = api.getTypeRelations("dragon")
                    }
                }.join()
            }
        }
        //What does the string look like?
        assertEquals(typeRelation!!.normal, "normal")
    }
}