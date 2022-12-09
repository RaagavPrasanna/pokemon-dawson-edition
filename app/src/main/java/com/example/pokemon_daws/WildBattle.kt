package com.example.pokemon_daws

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.pokemon_daws.R
import com.example.pokemon_daws.pokemon.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class WildBattle : AppCompatActivity() {
    var opponentPk: Pokemon? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wild_battle)
        lifecycleScope.launch(Dispatchers.IO){
            opponentPk = getRandomPk()
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
}