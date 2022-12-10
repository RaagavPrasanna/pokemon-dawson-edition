package com.example.pokemon_daws

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var ts: TypeSingleton
        lateinit var trainer: Trainer
        lateinit var pkFactory: PokemonFactory
        lateinit var allPk: List<PokedexEntry>
        var fetch = ApiController()
        lateinit var db: PkDb
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var fetch: ApiController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ts = TypeSingleton.getTypeSingleton(lifecycleScope)!!
        pkFactory = PokemonFactory()
        fetch = ApiController()
        lifecycleScope.launch(Dispatchers.IO){
            allPk = fetch.getAllPokemon()
            val pk = pkFactory.createPokemon(5, "bulbasaur", "bulb")
            val pk1 = pkFactory.createPokemon(10, "charmander")
//            Log.i("power", pk1.moves[2].power.toString())
            Log.i("attack", pk1.specialAttack.toString())
            Log.i("defence", pk.specialDefense.toString())
//            Log.i("effect",Pokemon_Math.CalculateDamage(pk1, pk, pk1.moves[2]).toString())
        }

        db = PkDb.getDb(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.newGameButton.setOnClickListener {
            val nsIntent = Intent(this, NameSelection::class.java)

            startActivity(nsIntent)
        }

        binding.loadGameButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                trainer = db.pkDao().getRecentTrainer()
                trainer.pokemons.forEachIndexed{index, elem ->
                    elem.frontImage = getImage(elem.frontUrl)!!
                    elem.backImage = getImage(elem.backUrl)!!
                }
                trainer.collection.pokemons.forEachIndexed{index, elem ->
                    elem.frontImage = getImage(elem.frontUrl)!!
                    elem.backImage = getImage(elem.backUrl)!!
                }
                println("images done loading")
            }

            val mmIntent = Intent(this, MainMenu::class.java)

            startActivity(mmIntent)
        }

    }

    private suspend fun getImage(urlStr: String): Bitmap? {
        var retVal: Bitmap? = null
        val url = URL(urlStr)
        val conn = url.openConnection() as HttpURLConnection
        try {
            conn.requestMethod = "GET"
            conn.connect()
            if (conn.responseCode == HttpsURLConnection.HTTP_OK) {
                val inputStream = conn.inputStream
                retVal = BitmapFactory.decodeStream(inputStream)
                inputStream.close()
            } else {
                throw java.lang.IllegalArgumentException("fetch did not work for front sprite")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("NETWORK ERROR", e.toString())
        }
        return retVal
    }
}