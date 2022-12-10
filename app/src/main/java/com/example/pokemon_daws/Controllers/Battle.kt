package com.example.pokemon_daws.Controllers

import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.pokemon_daws.MainActivity
import com.example.pokemon_daws.fragments.BattleScreen
import com.example.pokemon_daws.pokemon.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class Battle(val lifecycleScope: LifecycleCoroutineScope,val screen: BattleScreen) {
    lateinit var opponentPk: Pokemon
    lateinit var trainerPk: Pokemon

    public fun getStartingTrainerPk(): Pokemon? {
        for(pk in MainActivity.trainer.pokemons){
            if(pk.hp != 0){
                return pk
            }
        }
        return null
    }

    fun initBattle(){
        val pk = getStartingTrainerPk()
        if(pk == null){
            Log.i("Empty trainer", "Failed")
//            Toast.makeText(screen.context,"Can't fight when whole team fainted", Toast.LENGTH_SHORT)
            screen.requireActivity().finish()
        }else{
            trainerPk = pk
        }

        lifecycleScope.launch(Dispatchers.Main){
            withContext(Dispatchers.IO){
                opponentPk = getRandomPk()
            }
            screen.updateScreen(opponentPk)
        }
    }

    private fun getMaxLvl(): Int{
        var maxLvl = 0
        for (pk in MainActivity.trainer.pokemons){
            maxLvl = if(pk.level > maxLvl) pk.level else maxLvl
        }
        return maxLvl
    }

    private suspend fun getRandomPk(): Pokemon{
        val pk = MainActivity.allPk[Random.nextInt(MainActivity.allPk.size)]
        return MainActivity.pkFactory.createPokemon(getMaxLvl(), pk.name)
    }

    // Increase the trainer's pokemon's hp by up to 20
    fun usePotion() {
        trainerPk.hp += 20
        if (trainerPk.hp > trainerPk.maxHp) {
            trainerPk.hp = trainerPk.maxHp
        }
        screen.updateScreen(opponentPk)
    }

    fun usePokeBall(): Boolean  {
        val success = Pokemon_Math.AttemptCapture(opponentPk.hp.toDouble(), opponentPk.maxHp.toDouble())

        if (success) {
            MainActivity.trainer.collectPK(opponentPk)
        }
        return success
    }
}