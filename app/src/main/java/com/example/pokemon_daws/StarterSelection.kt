package com.example.pokemon_daws

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.pokemon_daws.databinding.ActivityStarterSelectionBinding;
import com.example.pokemon_daws.pokemon.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StarterSelection : AppCompatActivity() {

    private lateinit var binding: ActivityStarterSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStarterSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var selectedPokemon = "bulbasaur"


        binding.selectBulbasaur.setOnClickListener {
            binding.starterSprite.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.bulbasaur_starter_sprite
                )
            )

            selectedPokemon = "bulbasaur"

            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, getString(R.string.bulbasaur), duration)
            toast.show()
        }

        binding.selectCharmander.setOnClickListener {
            binding.starterSprite.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.charmander_starter_sprite                )
            )

            selectedPokemon = "charmander"

            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, getString(R.string.charmander), duration).show()
        }

        binding.selectSquirtle.setOnClickListener {
            binding.starterSprite.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.squirtle_starter_sprite
                )
            )

            selectedPokemon = "squirtle"

            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, getString(R.string.squirtle), duration)
            toast.show()
        }

        binding.nextButtonStarterSelection.setOnClickListener {

            val nickname = binding.starterNameEditText.text.toString()

            lifecycleScope.launch(Dispatchers.IO) {
                var pk: Pokemon
                if(nickname.isEmpty()) {
                    pk = MainActivity.pkFactory.createPokemon(5, selectedPokemon)
                } else {
                    pk = MainActivity.pkFactory.createPokemon(5, selectedPokemon, nickname)
                }
                MainActivity.trainer.addPK(pk)
                MainActivity.isInit = 0
            }

            val mmIntent = Intent(this, MainMenu::class.java)

            startActivity(mmIntent)
        }
    }
    override fun onResume() {
        super.onResume()
        MainActivity.isInit = null
    }
}