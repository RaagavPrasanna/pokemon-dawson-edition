package com.example.pokemon_daws

import android.Manifest.permission
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.pokemon_daws.Controllers.ApiController
import com.example.pokemon_daws.Controllers.PkDb
import com.example.pokemon_daws.Controllers.PokedexEntry
import com.example.pokemon_daws.databinding.ActivityMainBinding
import com.example.pokemon_daws.pokemon.PokemonFactory
import com.example.pokemon_daws.pokemon.TypeSingleton
import com.example.pokemon_daws.pokemon.storable.Trainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var ts: TypeSingleton
        lateinit var trainer: Trainer
        lateinit var pkFactory: PokemonFactory
        lateinit var allPk: List<PokedexEntry>
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
            getContact()
            allPk = fetch.getAllPokemon()
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
            runBlocking {
                lifecycleScope.launch(Dispatchers.IO){
                    try {
                        trainer = db.pkDao().getRecentTrainer()
                        trainer.pokemons.forEachIndexed { index, elem ->
                            elem.frontImage = getImage(elem.frontUrl)!!
                            elem.backImage = getImage(elem.backUrl)!!
                        }
                        trainer.collection.pokemons.forEachIndexed { index, elem ->
                            elem.frontImage = getImage(elem.frontUrl)!!
                            elem.backImage = getImage(elem.backUrl)!!
                        }
                        isInit = 0
                        passed = true

                        trainer.pokemons[0].moves.add(trainer.pokemons[0].allMoves[5])
                    } catch(e: Exception) {

                    }
                }.join()
            }
            if(passed) {
                val mmIntent = Intent(this, MainMenu::class.java)

                startActivity(mmIntent)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        isInit = null
    }

    private fun getImage(urlStr: String): Bitmap? {
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
            withContext(Dispatchers.Main) {
                Toast.makeText(applicationContext, "Please relaunch the app to fetch your contacts", Toast.LENGTH_LONG).show()
            }
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
            if(contacts.size == 0) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        applicationContext,
                        "No contacts found on phone :(",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}