package com.example.pokemon_daws

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pokemon_daws.databinding.ActivityMainMenuBinding

class MainMenu : AppCompatActivity() {

    private lateinit var binding: ActivityMainMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.changeTeamButton.setOnClickListener{
            val ctIntent = Intent(this, ChangeTeam::class.java)

            startActivity(ctIntent)
        }
        binding.wildEncounterButton.setOnClickListener{
            val wildBattleIntent = Intent(this, WildBattle::class.java)
            startActivity(wildBattleIntent)
        }

        binding.pokecenterButton.setOnClickListener{
            MainActivity.trainer.pokemons.forEach{
                it.hp = it.maxHp;
            }
        }
    }
}