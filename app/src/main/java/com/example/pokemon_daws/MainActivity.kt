package com.example.pokemon_daws

import android.Manifest.permission
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.pokemon_daws.Controllers.ApiController
import com.example.pokemon_daws.databinding.ActivityMainBinding
import com.example.pokemon_daws.pokemon.PokemonFactory
import com.example.pokemon_daws.Controllers.*
import com.example.pokemon_daws.pokemon.TypeSingleton
import com.example.pokemon_daws.pokemon.storable.Trainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import android.provider.ContactsContract
import java.security.Permission
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var ts: TypeSingleton
        lateinit var trainer: Trainer
        lateinit var pkFactory: PokemonFactory
        lateinit var allPk: List<PokedexEntry>
        var fetch = ApiController()
        lateinit var db: PkDb
        var isInit: Int? = null
        var contacts: ArrayList<String> = arrayListOf()
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
//            Log.i("attack", pk1.specialAttack.toString())
//            Log.i("defence", pk.specialDefense.toString())
//            Log.i("effect",Pokemon_Math.CalculateDamage(pk1, pk, pk1.moves[2]).toString())

            getContact()
        }

        db = PkDb.getDb(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.newGameButton.setOnClickListener {
            val nsIntent = Intent(this, NameSelection::class.java)

            startActivity(nsIntent)
        }

        binding.loadGameButton.setOnClickListener {
            var passed = false
            val toastContext = this
//            try {
            runBlocking {
                lifecycleScope.launch(Dispatchers.IO){
                    try {
                        trainer = db.pkDao().getRecentTrainer()
                        trainer.pokemons[1].hp = 0
                        trainer.pokemons.forEachIndexed { index, elem ->
                            elem.frontImage = getImage(elem.frontUrl)!!
                            elem.backImage = getImage(elem.backUrl)!!
                        }
                        trainer.collection.pokemons.forEachIndexed { index, elem ->
                            elem.frontImage = getImage(elem.frontUrl)!!
                            elem.backImage = getImage(elem.backUrl)!!
                        }
                        println("images done loading")
                        isInit = 0
                        passed = true
                    } catch(e: Exception) {

                    }
                }.join()
            }
            if(passed) {
                val mmIntent = Intent(this, MainMenu::class.java)

                startActivity(mmIntent)
            }
//            } catch (e: Exception) {
//                e.printStackTrace()
//                val duration = Toast.LENGTH_SHORT
//                val toast = Toast.makeText(applicationContext, "Cannot load game, no save file found", duration)
//                toast.show()
//            }
        }

    }

    override fun onResume() {
        super.onResume()
        isInit = null
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

    suspend fun getContact() {
        val contactsList = ArrayList<String>()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            val arr = Array<String>(1){permission.READ_CONTACTS}
            requestPermissions(arr, 100)
        } else {
            val contactsCursor = application.contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")
            if(contactsCursor != null && contactsCursor.count > 0) {
                val nameIndex = contactsCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                while(contactsCursor.moveToNext()) {
                    val name = contactsCursor.getString(nameIndex)
                    if(name != null) {
                        contactsList.add(name)
                    }
                }
                contactsCursor.close()
            }
            contacts = contactsList
            println(contacts)
        }
    }
}