package com.example.pokemon_daws

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.pokemon_daws.fragments.BattleScreen
import com.example.pokemon_daws.pokemon.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class Battle(lifecycleScope: LifecycleCoroutineScope, screen: BattleScreen) {
    lateinit var opponentPk: Pokemon
    lateinit var trainerPk: Pokemon

    init {
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

    public fun getStartingTrainerPk(): Pokemon? {
        for(pk in MainActivity.trainer.pokemons){
            if(pk.hp != 0){
                return pk
            }
        }
        return null
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
}