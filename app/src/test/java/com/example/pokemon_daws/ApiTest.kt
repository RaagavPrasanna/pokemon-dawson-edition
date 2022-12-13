package com.example.pokemon_daws

import com.example.pokemon_daws.Controllers.ApiController
import com.example.pokemon_daws.pokemon.Pokemon
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*

class ApiTest {
    val api = ApiController()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testFetchAllPk()= runTest{
        var pkEntries = api.getAllPokemon()
        assertTrue(pkEntries.size == 151)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testFetchPk()= runTest{
        val pk = api.getPokemon("charmander")
        assertEquals(pk!!.types[0], "fire")
        assertEquals(pk.base_exp_reward, 62)
        assertEquals(pk.base_maxHp, 39)
        assertEquals(pk.base_attack, 52)
        assertEquals(pk.base_defense, 43)
        assertEquals(pk.baseSpecialAttack, 60)
        assertEquals(pk.baseSpecialDefense, 50)
        assertEquals(pk.base_speed, 65)
        assertEquals(pk.back_sprite, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-i/red-blue/transparent/back/4.png")
        assertEquals(pk.front_sprite, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-i/red-blue/transparent/4.png")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testTypes()= runTest{
        var types = api.getTypes()
        assertEquals(types[0], "normal")
        assertEquals(types[1], "fighting")
        assertEquals(types[2], "flying")
        assertEquals(types[3], "poison")
        assertEquals(types[4], "ground")
        assertEquals(types[5], "rock")
        assertEquals(types[6], "bug")
        assertEquals(types[7], "ghost")
        assertEquals(types[8], "fire")
        assertEquals(types[9], "water")
        assertEquals(types[10], "grass")
        assertEquals(types[11], "electric")
        assertEquals(types[12], "psychic")
        assertEquals(types[13], "ice")
        assertEquals(types[14], "dragon")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testTypeRelation()= runTest{
        val relations = api.getTypeRelations("fire")
        assertEquals(relations!!.fire, "not_very_effective")
        assertEquals(relations.normal, null)
        assertEquals(relations.water, "not_very_effective")
        assertEquals(relations.electric, null)
        assertEquals(relations.grass, "super_effective")
        assertEquals(relations.ice, "super_effective")
        assertEquals(relations.fighting, null)
        assertEquals(relations.poison, null)
        assertEquals(relations.ground, null)
        assertEquals(relations.flying, null)
        assertEquals(relations.psychic, null)
        assertEquals(relations.bug, "super_effective")
        assertEquals(relations.rock, "not_very_effective")
        assertEquals(relations.ghost, null)
        assertEquals(relations.dragon, "not_very_effective")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testMove()= runTest{
        var move = api.getMove("tackle")
        assertEquals(move!!.name, "tackle")
        assertEquals(move.description, "A full-body charge attack.")
        assertEquals(move.category, "damage")
        assertEquals(move.accuracy, 100)
        assertEquals(move.power, 40)
        assertEquals(move.damage_class, "physical")
        assertEquals(move.type, "normal")
        assertEquals(move.maxPP, 35)
        assertEquals(move.ailment, null)
        assertEquals(move.ailment_chance, 0)
        assertEquals(move.healing, 0)
        assertEquals(move.target, "opponent")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testPkMoves()= runTest{
        var pkMoves = api.getPkMoves("bulbasaur")
        assertEquals(pkMoves[0].level, 13)
        assertEquals(pkMoves[0].move, "vine-whip")
        assertEquals(pkMoves[8].level, 41)
        assertEquals(pkMoves[8].move, "sleep-powder")
    }
}