package com.example.pokemon_daws

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.pokemon_daws.utils.Json
import com.example.pokemon_daws.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val jsonReader = Json(this);
        Log.i("Test",jsonReader.readJsonMove("bubble.json").toString())
        Log.i("Test",jsonReader.readJsonMove("ember.json").toString())
        Log.i("Test",jsonReader.readJsonMoveList("bulbasaur.json").toString())
        Log.i("Test",jsonReader.readJsonTypeRelations("fire.json").toString())
        Log.i("Test",jsonReader.readJsonPokemon("pidgey.json").toString())
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newGameButton.setOnClickListener {
            val nsIntent = Intent(this, NameSelection::class.java)

            startActivity(nsIntent)
        }
    }
}