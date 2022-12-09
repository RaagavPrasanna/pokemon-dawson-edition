package com.example.pokemon_daws

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.pokemon_daws.databinding.ActivityStarterSelectionBinding;
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
            val toast = Toast.makeText(applicationContext, getString(R.string.charmander), duration)
            toast.show()
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

            lifecycleScope.launch(Dispatchers.IO) {
                val pk = MainActivity.pkFactory.createPokemon(5, selectedPokemon)
                val pk2 = MainActivity.pkFactory.createPokemon(25, "mewtwo")
                val pk3 = MainActivity.pkFactory.createPokemon(25, "mew")
                val pk4 = MainActivity.pkFactory.createPokemon(36, "zapdos")
                val pk5 = MainActivity.pkFactory.createPokemon(36, "weepinbell")
                val pk6 = MainActivity.pkFactory.createPokemon(36, "hitmonchan")

                MainActivity.trainer.addPK(pk)
                MainActivity.trainer.addPK(pk2)
                MainActivity.trainer.addPK(pk3)
                MainActivity.trainer.addPK(pk4)
                MainActivity.trainer.addPK(pk5)
                MainActivity.trainer.addPK(pk6)
            }

            val mmIntent = Intent(this, MainMenu::class.java)

            startActivity(mmIntent)
        }
    }
}