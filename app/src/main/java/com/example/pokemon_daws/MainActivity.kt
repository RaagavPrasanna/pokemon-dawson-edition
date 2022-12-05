package com.example.pokemon_daws

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.pokemon_daws.Controllers.Pokemon_Math
import com.example.pokemon_daws.utils.Json
import com.example.pokemon_daws.databinding.ActivityMainBinding
import com.example.pokemon_daws.pokemon.PokemonFactory
import com.example.pokemon_daws.Controllers.*
import com.example.pokemon_daws.pokemon.Pokemon
import com.example.pokemon_daws.pokemon.storable.Collection
import com.example.pokemon_daws.pokemon.storable.Trainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val db by lazy {PkDb.getDb(this)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val jsonReader = Json(this);
        val pkFc = PokemonFactory(this)
        Log.i("Test",jsonReader.readJsonMove("bubble.json").toString())
        Log.i("Test",jsonReader.readJsonMove("ember.json").toString())
        Log.i("Test",jsonReader.readJsonMoveList("bulbasaur.json").toString())
        Log.i("Test",jsonReader.readJsonTypeRelations("fire.json").toString())
        Log.i("Test",jsonReader.readJsonPokemon("pidgey.json").toString())
        val pk = pkFc.createPokemon(5, "bulbasaur", "bulb")
        val pk1 = pkFc.createPokemon(5, "charmander")

        val collection = Collection()

        collection.addPK(pk)
        collection.addPK(pk1)

        val trainer = Trainer(mutableListOf<Pokemon>(), "Snowman", collection)

        trainer.addPK(pk)
        trainer.addPK(pk1)


//        val db = Room.databaseBuilder(
//            applicationContext,
//            PkDb::class.java, "pokemon-db"
//        ).build()
//
//
//
//        db.pkDao().insertTrainer(trainer)
//
//        println(db.pkDao().loadTrainer("Snowman"))

//        Log.i("Test", pk.toString())
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newGameButton.setOnClickListener {
            val nsIntent = Intent(this, NameSelection::class.java)

            startActivity(nsIntent)
        }
        lifecycleScope.launch(Dispatchers.IO) {
            db.pkDao().insertTrainer(trainer)

            println(db.pkDao().loadTrainer("Snowman"))
        }
    }
}