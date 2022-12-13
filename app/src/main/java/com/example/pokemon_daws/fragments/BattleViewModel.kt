package com.example.pokemon_daws.fragments

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemon_daws.Controllers.PokemonMath
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

    private val _battleText = MutableLiveData<BattleText>()
    val battleText = _battleText

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

    private fun getBattleText(): BattleText {
        return _battleText.value!!
    }

    fun setBattleText(battleText: BattleText) {
        _battleText.value = battleText
    }

    init {
        val pk = getStartingTrainerPk()
        if(pk == null){
//        TODO call dialog here
        }else{
            this.setTrainerPk(getStartingTrainerPk()!!)
        }

    }
    fun startBattle(){
        if (getTrainerPk().speed < getOpponentPk().speed) {
            getBattleText().setOpponentText("Opponent went first")
            opponentExecuteMove()
        }
    }
//    fun executeMove(move: Move) {
//        val hit = java.util.Random().nextInt(101)
//        if (hit <= move.accuracy) {
//            getBattleText().setTrainerText("${getTrainerPk().name} used ${move.name}")
//            move.executeMove(getTrainerPk(), getOpponentPk())
//            if (getOpponentPk().hp <= 0) {
//                Toast.makeText(getBattleScreen().context, "You won", Toast.LENGTH_SHORT)
//                getBattleScreen().requireActivity().finish()
//            }
//            getBattleScreen().updateScreen()
//        } else{
//            getBattleText().setTrainerText("${getTrainerPk().name} missed")
//        }
//        if(getOpponentPk().hp !=0){
//            opponentExecuteMove()
//        }
//    }

    fun opponentExecuteMove() {
        val moveIndex = java.util.Random().nextInt(getOpponentPk().moves.size)
        val move = getOpponentPk().moves[moveIndex]
        val hit = java.util.Random().nextInt(101)
        if (hit <= move.accuracy) {
            getBattleText().setOpponentText("Opponent used ${move.name}")
            move.executeMove(getOpponentPk(), getTrainerPk())
            if (getTrainerPk().hp <= 0) {
                if(MainActivity.trainer.isTrainerDead()){
                    getBattleScreen().passPkDialogMsg(getTrainerPk().name +" has fainted. You lose")
                }else{
                    setTrainerPk(getStartingTrainerPk()!!)
                }
            }
            getBattleScreen().updateScreen()
        }else{
            getBattleText().setOpponentText("Opponent missed")
        }
    }


    fun usePotion() {
        getBattleText().setTrainerText("Used Potion: Healed 20 hp")
        getTrainerPk().hp += 20
        if (getTrainerPk().hp > getTrainerPk().maxHp) {
            getTrainerPk().hp = getTrainerPk().maxHp
        }
        getBattleScreen().updateScreen()
        opponentExecuteMove()
    }

    fun executeMove(move: Move){
        Log.i("attack", "here")

        val hit = java.util.Random().nextInt(101)

        if (hit <= move.accuracy) {
            getBattleText().setTrainerText("${getTrainerPk().name} used ${move.name}")
            move.executeMove(getTrainerPk(), getOpponentPk())
            if (getOpponentPk().hp <= 0) {
                Toast.makeText(getBattleScreen().context, "You won", Toast.LENGTH_SHORT)

            }
            getBattleScreen().updateScreen()
        } else{
            getBattleText().setTrainerText("${getTrainerPk().name} missed")
        }


        val prevLevel = getTrainerPk().level
        if(getOpponentPk().hp <= 0){
            getTrainerPk().experience += (0.3 * getOpponentPk().baseExperienceReward * getOpponentPk().level).toInt()
            if(getTrainerPk().level > prevLevel) {
                var firstVal = true
                for(i in prevLevel + 1 .. getTrainerPk().level) {
                    for(m in getTrainerPk().allMoves) {
                        if(m.level == i) {
                            if(getTrainerPk().moves.size == 4) {
                                if(firstVal) {
                                    getBattleScreen().passDialogMsg(getTrainerPk().name +" levelled up to level: " +i, getTrainerPk(), firstVal, m)
                                    firstVal = false
                                }  else {
                                    getBattleScreen().passDialogMsg(getTrainerPk().name +" levelled up to level: " +i, getTrainerPk(), firstVal, m)
                                }
                            } else {
                                getTrainerPk().moves.add(m)
                                getBattleScreen().displayToast(getTrainerPk().name +" learned " +m.name)
                            }
                        }
                    }
                }
//                for (i in prevLevel + 1 .. getTrainerPk().level) {
//                    if(i == prevLevel + 1) {
//                        getBattleScreen().passDialogMsg(getTrainerPk().name +" levelled up to level: " +i, getTrainerPk(), true)
//                    } else {
//                        getBattleScreen().passDialogMsg(getTrainerPk().name +" levelled up to level: " +i, getTrainerPk(), false)
//                    }
//                }
                println("levelled up")
//                getBattleScreen().requireActivity().finish()
                if(firstVal) {
                    getBattleScreen().passPkDialogMsg(getOpponentPk().name +" has fainted. You win")
                }

            } else {
//            Toast.makeText(getBattleScreen().context, "You won", Toast.LENGTH_SHORT)
                getBattleScreen().passPkDialogMsg(getOpponentPk().name +" has fainted. You win")
            }
        }
        if(getOpponentPk().hp != 0) {
            opponentExecuteMove()
        }
        getBattleScreen().updateScreen()
    }

    fun usePokeball() {
        val success = PokemonMath.attemptCapture(
            getOpponentPk().hp.toDouble(),
            getOpponentPk().maxHp.toDouble()
        )

        if (success) {
//            Todo dialog box to name
            getBattleText().setTrainerText("Threw Pokeball: Caugth Wild Pk")

            if (MainActivity.trainer.pokemons.size < 6) {
                MainActivity.trainer.addPK(getOpponentPk())
            } else {
                MainActivity.trainer.collectPK(getOpponentPk())
            }
//            getBattleScreen().passPkDialogMsg("Congrats! You caught " +getOpponentPk().name)
            getBattleScreen().catchPkDialogMsg("Congrats! You caught " +getOpponentPk().name, getOpponentPk())
        }else{
            getBattleText().setTrainerText("Threw Pokeball: Missed")
            opponentExecuteMove()
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