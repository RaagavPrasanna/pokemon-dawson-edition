package com.example.pokemon_daws

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pokemon_daws.databinding.ActivityMainMenuBinding
import com.example.pokemon_daws.fragments.WildBattle

class MainMenu : AppCompatActivity() {

    private lateinit var binding: ActivityMainMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.wildEncounterButton.setOnClickListener{
            val wildBattleIntent = Intent(this, WildBattle::class.java)
            startActivity(wildBattleIntent)
        }
    }
}