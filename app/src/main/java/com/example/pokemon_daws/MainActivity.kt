package com.example.pokemon_daws

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.pokemon_daws.Controllers.Pokemon_Math
import com.example.pokemon_daws.utils.Json
import com.example.pokemon_daws.databinding.ActivityMainBinding
import com.example.pokemon_daws.pokemon.Type

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val jsonReader = Json(this);
//        Toast.makeText(this,jsonReader.readJsonMove("bubble.json").toString(),Toast.LENGTH_LONG).show()
        jsonReader.readJsonMove("bubble.json")
        Toast.makeText(this, jsonReader.readJsonMoveList("bulbasaur.json").toString(), Toast.LENGTH_LONG).show()
        Log.i("Test",jsonReader.readJsonPokemon("pidgey.json").toString())
        Log.i("Test",jsonReader.readJsonTypeRelations("fire.json").toString())
        val math = Pokemon_Math();
        math.CalculateDamage(1.0, 5.0, 1.0, 1.0, "throw", jsonReader.readJsonTypeRelations("fire.json"), Type.GRASS)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newGameButton.setOnClickListener {
            val nsIntent = Intent(this, NameSelection::class.java)

            startActivity(nsIntent)
        }
    }
}