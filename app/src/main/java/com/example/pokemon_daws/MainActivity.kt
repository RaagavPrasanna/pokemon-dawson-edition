package com.example.pokemon_daws

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.pokemon_daws.Controllers.ApiController
import com.example.pokemon_daws.databinding.ActivityMainBinding
import com.example.pokemon_daws.pokemon.PokemonFactory
import com.example.pokemon_daws.Controllers.*
import com.example.pokemon_daws.pokemon.TypeSingleton
import com.example.pokemon_daws.pokemon.storable.Trainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var ts: TypeSingleton
        lateinit var trainer: Trainer
        lateinit var pkFactory: PokemonFactory
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var fetch: ApiController

    private val db by lazy {PkDb.getDb(this)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ts = TypeSingleton.getTypeSingleton(lifecycleScope)!!
        pkFactory = PokemonFactory(lifecycleScope)
        lifecycleScope.launch(Dispatchers.IO){
            val pk = pkFactory.createPokemon(5, "bulbasaur", "bulb")
            val pk1 = pkFactory.createPokemon(10, "charmander")
            Log.i("power", pk1.moves[2].power.toString())
            Log.i("attack", pk1.specialAttack.toString())
            Log.i("defence", pk.specialDefense.toString())
//            Log.i("effect",Pokemon_Math.CalculateDamage(pk1, pk, pk1.moves[2]).toString())
        }



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.newGameButton.setOnClickListener {
            val nsIntent = Intent(this, NameSelection::class.java)

            startActivity(nsIntent)
        }


    }
}