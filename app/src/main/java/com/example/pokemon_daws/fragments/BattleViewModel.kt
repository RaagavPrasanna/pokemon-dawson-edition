package com.example.pokemon_daws.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemon_daws.MainActivity
import com.example.pokemon_daws.pokemon.Move
import com.example.pokemon_daws.pokemon.Pokemon
import kotlin.random.Random

class BattleViewModel: ViewModel() {
    private val _opponentPk = MutableLiveData<Pokemon>()
    val opponentPk: LiveData<Pokemon> = _opponentPk

    private val _trainerPk = MutableLiveData<Pokemon>()
    val trainerPk: LiveData<Pokemon> = _trainerPk

    private val _battleScreen = MutableLiveData<BattleScreen>()
    val battleScreen = _battleScreen

    fun getOpponentPk(): Pokemon {
        return _opponentPk.value!!
    }

    fun setOpponentPk(opponentPk: Pokemon){
        _opponentPk.value = opponentPk
    }

    fun getTrainerPk(): Pokemon {
        return _trainerPk.value!!
    }

    fun setTrainerPk(trainerPk: Pokemon){
        _trainerPk.value = trainerPk
    }

    fun getBattleScreen(): BattleScreen {
        return _battleScreen.value!!
    }

    fun setBattleScreen(battleScreen: BattleScreen){
        _battleScreen.value = battleScreen
    }

    init {
//        TODO add nullcheck
        this.setTrainerPk(getStartingTrainerPk()!!)
    }

    fun executeMove(move: Move){
        Log.i("attack", "here")
        move.executeMove(getTrainerPk(), getOpponentPk())
        getBattleScreen().updateScreen()
    }

    fun getStartingTrainerPk(): Pokemon? {
        for(pk in MainActivity.trainer.pokemons){
            if(pk.hp != 0){
                return pk
            }
        }
        return null
    }

    private fun getMinLvl():Int{
        var minLvl = 100//Pk max level
        for (pk in MainActivity.trainer.pokemons){
            minLvl = if(pk.level < minLvl) pk.level else minLvl
        }
        return minLvl
    }

    private fun getMaxLvl(): Int{
        var maxLvl = 0
        for (pk in MainActivity.trainer.pokemons){
            maxLvl = if(pk.level > maxLvl) pk.level else maxLvl
        }
        return maxLvl
    }

    suspend fun getRandomPk(): Pokemon{
        val pk = MainActivity.allPk[Random.nextInt(MainActivity.allPk.size)]
        return MainActivity.pkFactory.createPokemon((getMinLvl()..getMaxLvl()).random(), pk.name)
    }
}