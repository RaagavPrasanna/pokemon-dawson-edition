package com.example.pokemon_daws

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.pokemon_daws.utils.Json

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val jsonReader = Json(this);
//        Toast.makeText(this,jsonReader.readJsonMove("bubble.json").toString(),Toast.LENGTH_LONG).show()
        jsonReader.readJsonMove("bubble.json")
        Toast.makeText(this, jsonReader.readJsonMoveList("bulbasaur.json").toString(), Toast.LENGTH_LONG).show()
        Log.i("Test",jsonReader.readJsonPokemon("pidgey.json").toString())
        Log.i("Test",jsonReader.readJsonTypeRelations("fire.json").toString())
    }
}