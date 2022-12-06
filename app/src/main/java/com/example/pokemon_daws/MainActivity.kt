package com.example.pokemon_daws

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.pokemon_daws.Controllers.ApiController
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
    private lateinit var fetch: ApiController

    private val db by lazy {PkDb.getDb(this)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val jsonReader = Json(this);
        val pkFc = PokemonFactory(this, lifecycleScope)
        Log.i("Test",jsonReader.readJsonMove("bubble.json").toString())
        Log.i("Test",jsonReader.readJsonMove("ember.json").toString())
        Log.i("Test",jsonReader.readJsonMoveList("bulbasaur.json").toString())
        Log.i("Test",jsonReader.readJsonTypeRelations("fire.json").toString())
        Log.i("Test",jsonReader.readJsonPokemon("pidgey.json").toString())
        lifecycleScope.launch(Dispatchers.IO){
            val pk = pkFc.createPokemon(5, "bulbasaur", "bulb")

             val pk1 = pkFc.createPokemon(5, "charmander")
            Log.i("TEST", pk.toString())
        }

//        val collection = Collection()
//
//        collection.addPK(pk)
//        collection.addPK(pk1)
//
//        val trainer = Trainer(mutableListOf<Pokemon>(), "Snowman", collection)
//
//        trainer.addPK(pk)
//        trainer.addPK(pk1)

//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        fetch = ApiController(lifecycleScope)
//        lifecycleScope.launch(Dispatchers.IO){
//            fetch.getAllPokemon()
//            fetch.getTypes()
//            fetch.getPokemon("bulbasaur")
//            fetch.getPkMoves("bulbasaur")
//            fetch.getMove("tackle")
//            fetch.getTypeRelations("ground")
//        }
//        binding.newGameButton.setOnClickListener {
//            val nsIntent = Intent(this, NameSelection::class.java)
//
//            startActivity(nsIntent)
//        }
//        lifecycleScope.launch(Dispatchers.IO) {
//            db.pkDao().insertTrainer(trainer)
//
//            println(db.pkDao().loadTrainer("Snowman"))
//        }
    }
}