package com.example.pokemon_daws.fragments

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemon_daws.Controllers.Pokemon_Math
import com.example.pokemon_daws.MainActivity
import com.example.pokemon_daws.pokemon.Move
import com.example.pokemon_daws.pokemon.Pokemon
import kotlin.random.Random

class BattleViewModel : ViewModel() {
    private val _opponentPk = MutableLiveData<Pokemon>()
    val opponentPk: LiveData<Pokemon> = _opponentPk

    private val _trainerPk = MutableLiveData<Pokemon>()
    val trainerPk: LiveData<Pokemon> = _trainerPk

    private val _battleScreen = MutableLiveData<BattleScreen>()
    val battleScreen = _battleScreen

    fun getOpponentPk(): Pokemon {
        return _opponentPk.value!!
    }

    fun setOpponentPk(opponentPk: Pokemon) {
        _opponentPk.value = opponentPk
    }

    fun getTrainerPk(): Pokemon {
        return _trainerPk.value!!
    }

    fun setTrainerPk(trainerPk: Pokemon) {
        _trainerPk.value = trainerPk
    }

    fun getBattleScreen(): BattleScreen {
        return _battleScreen.value!!
    }

    fun setBattleScreen(battleScreen: BattleScreen) {
        _battleScreen.value = battleScreen
    }

    init {
//        TODO add nullcheck
        this.setTrainerPk(getStartingTrainerPk()!!)

    }
    fun startBattle(){
        if (getTrainerPk().speed < getOpponentPk().speed) {
            opponentExecuteMove()
        }
    }
    fun executeMove(move: Move) {
        val hit = java.util.Random().nextInt(101)
        if (hit <= move.accuracy) {
            move.executeMove(getTrainerPk(), getOpponentPk())
            if (getOpponentPk().hp <= 0) {
                Toast.makeText(getBattleScreen().context, "You won", Toast.LENGTH_SHORT)
                getBattleScreen().requireActivity().finish()
            }
            getBattleScreen().updateScreen()
        } else{
            opponentExecuteMove()
        }
    }

    fun opponentExecuteMove() {
        Log.i("first", "opponent went first")
        val moveIndex = java.util.Random().nextInt(getOpponentPk().moves.size)
        val move = getOpponentPk().moves[moveIndex]
        val hit = java.util.Random().nextInt(101)
        if (hit <= move.accuracy) {
            move.executeMove(getOpponentPk(), getTrainerPk())
            if (getTrainerPk().hp <= 0) {
//                Toast.makeText(getBattleScreen().context, "You Lose", Toast.LENGTH_SHORT)
                getBattleScreen().requireActivity().finish()
            }
            getBattleScreen().updateScreen()
        }
    }

    fun usePotion() {
        getTrainerPk().hp += 20
        if (getTrainerPk().hp > getTrainerPk().maxHp) {
            getTrainerPk().hp = getTrainerPk().maxHp
        }
        getBattleScreen().updateScreen()
    }

    fun usePokeball() {
        val success = Pokemon_Math.AttemptCapture(
            getOpponentPk().hp.toDouble(),
            getOpponentPk().maxHp.toDouble()
        )

        if (success) {
//            Todo dialog box to name
            if (MainActivity.trainer.pokemons.size < 6) {
                MainActivity.trainer.addPK(getOpponentPk())
            } else {
                MainActivity.trainer.collectPK(getOpponentPk())
            }
        }
    }

    fun getStartingTrainerPk(): Pokemon? {
        for (pk in MainActivity.trainer.pokemons) {
            if (pk.hp != 0) {
                return pk
            }
        }
        return null
    }

    private fun getMinLvl(): Int {
        var minLvl = 100//Pk max level
        for (pk in MainActivity.trainer.pokemons) {
            minLvl = if (pk.level < minLvl) pk.level else minLvl
        }
        return minLvl
    }

    private fun getMaxLvl(): Int {
        var maxLvl = 0
        for (pk in MainActivity.trainer.pokemons) {
            maxLvl = if (pk.level > maxLvl) pk.level else maxLvl
        }
        return maxLvl
    }

    suspend fun getRandomPk(): Pokemon {
        val pk = MainActivity.allPk[Random.nextInt(MainActivity.allPk.size)]
        return MainActivity.pkFactory.createPokemon((getMinLvl()..getMaxLvl()).random(), pk.name)
    }
}