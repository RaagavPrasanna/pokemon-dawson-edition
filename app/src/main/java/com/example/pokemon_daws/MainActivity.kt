package com.example.pokemon_daws

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.pokemon_daws.controllers.ApiController
import com.example.pokemon_daws.utils.Json
import com.example.pokemon_daws.databinding.ActivityMainBinding
import com.example.pokemon_daws.pokemon.PokemonFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fetch: ApiController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fetch = ApiController(lifecycleScope)
        lifecycleScope.launch(Dispatchers.IO){
            fetch.getAllPokemon()
            fetch.getTypes()
            fetch.getPokemon("bulbasaur")
            fetch.getPkMoves("bulbasaur")
            fetch.getMove("tackle")
            fetch.getTypeRelations("ground")
        }
        binding.newGameButton.setOnClickListener {
            val nsIntent = Intent(this, NameSelection::class.java)

            startActivity(nsIntent)
        }
    }
}