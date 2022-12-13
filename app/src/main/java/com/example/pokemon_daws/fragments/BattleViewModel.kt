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
import com.example.pokemon_daws.pokemon.storable.Trainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Random

class BattleViewModel : ViewModel() {
    private val _opponentPk = MutableLiveData<Pokemon>()
    var battleType: String = "pokemon"
    val opponentPk: LiveData<Pokemon> = _opponentPk
    lateinit var OpponentTrainer: Trainer

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

    fun opponentExecuteMove() {
        if(getOpponentPk().moves.size > 0) {
            val moveIndex = java.util.Random().nextInt(getOpponentPk().moves.size)
            val move = getOpponentPk().moves[moveIndex]
            val hit = java.util.Random().nextInt(101)
            if (hit <= move.accuracy) {
                getBattleText().setOpponentText("Opponent used ${move.name}")
                move.executeMove(getOpponentPk(), getTrainerPk())
                if (getTrainerPk().hp <= 0) {
                    if(MainActivity.trainer.isTrainerDead()) {
                        getBattleScreen().passPkDialogMsg(getTrainerPk().name + " has fainted.")
                    }else{
                        setTrainerPk(getStartingTrainerPk()!!)
                    }
                }else{
                    setTrainerPk(getStartingTrainerPk()!!)
                }

                getBattleScreen().updateScreen()
            } else {
                getBattleText().setOpponentText("Opponent missed")
            }
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
                println("levelled up")
                if(firstVal) {
                    getBattleScreen().passPkDialogMsg(getOpponentPk().name +" has fainted")
                }

            } else {
                getBattleScreen().passPkDialogMsg(getOpponentPk().name +" has fainted")
            }
            if(battleType == "trainer") {
                if(OpponentTrainer.isTrainerDead()) {
                    getBattleScreen().passBattleDoneMsg("Congrats, you defeated " +OpponentTrainer.name)
                } else {
                    var foundNextPoke = false
                    for(i in OpponentTrainer.pokemons) {
                        if(i == getOpponentPk()) {
                            foundNextPoke = true
                        } else if(foundNextPoke) {
                            setOpponentPk(i)
                            break
                        }
                    }
                }
            }
        }
        if(getOpponentPk().hp != 0) {
            opponentExecuteMove()
        }
        getBattleScreen().updateScreen()
    }

    fun usePokeball() {
        if(battleType == "pokemon") {
            val success = Pokemon_Math.AttemptCapture(
                getOpponentPk().hp.toDouble(),
                getOpponentPk().maxHp.toDouble()
            )

            if (success) {
                getBattleText().setTrainerText("Threw Pokeball: Caugth Wild Pk")

                if (MainActivity.trainer.pokemons.size < 6) {
                    MainActivity.trainer.addPK(getOpponentPk())
                } else {
                    MainActivity.trainer.collectPK(getOpponentPk())
                }
                getBattleScreen().catchPkDialogMsg("Congrats! You caught " +getOpponentPk().name, getOpponentPk())
            }else{
                getBattleText().setTrainerText("Threw Pokeball: Missed")
                opponentExecuteMove()
            }
        } else {
            getBattleScreen().displayToast("Cannot catch a pokemon in a trainer battle!")
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
        return if(minLvl - 5 <= 0) 1 else minLvl - 5
    }

    private fun getMaxLvl(): Int {
        var maxLvl = 0
        for (pk in MainActivity.trainer.pokemons) {
            maxLvl = if (pk.level > maxLvl) pk.level else maxLvl
        }
        return if(maxLvl+ 5 >= 100) 100 else maxLvl + 5
    }

    suspend fun getRandomPk(): Pokemon {
        val pk = MainActivity.allPk[java.util.Random().nextInt(MainActivity.allPk.size)]
        return MainActivity.pkFactory.createPokemon((getMinLvl()..getMaxLvl()).random(), pk.name)
    }

    suspend fun getRandomPk(rand: Random): Pokemon {
        val pk = MainActivity.allPk[rand.nextInt(MainActivity.allPk.size)]
        return MainActivity.pkFactory.createPokemon((getMinLvl()..getMaxLvl()).random(), pk.name)
    }

    suspend fun setTrainersPokemon() {
        if(OpponentTrainer.name == "Blue") {
            withContext(Dispatchers.Main) {
                for (i in 0..java.util.Random().nextInt(6)) {
                    val pk = MainActivity.allPk[java.util.Random().nextInt(MainActivity.allPk.size)]
                    val genPk = MainActivity.pkFactory.createPokemon(
                        (getMinLvl()..getMaxLvl()).random(),
                        pk.name
                    )
                    OpponentTrainer.addPK(genPk)
                    for (i in OpponentTrainer.pokemons) {
                        println(i.name)
                    }
                }
            }
            setOpponentPk(OpponentTrainer.pokemons[0])
        } else {
            var seed = 0
            for (i in 0..OpponentTrainer.name.length-1) {
                seed += OpponentTrainer.name[i].code
            }
            val rand = java.util.Random(seed.toLong())
            for(i in 0 .. rand.nextInt(6)) {
                val pk = MainActivity.allPk[rand.nextInt(MainActivity.allPk.size)]
                OpponentTrainer.addPK(MainActivity.pkFactory.createPokemon((getMinLvl()..getMaxLvl()).random(), pk.name))
            }
        }
    }
}