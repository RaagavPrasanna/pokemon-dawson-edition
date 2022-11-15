package com.example.pokemon_daws

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.pokemon_daws.databinding.ActivityStarterSelectionBinding;

class StarterSelection : AppCompatActivity() {

    private lateinit var binding: ActivityStarterSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStarterSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.selectBulbasaur.setOnClickListener {
            binding.starterSprite.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.bulbasaur_starter_sprite
                )
            )
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
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, getString(R.string.squirtle), duration)
            toast.show()
        }
    }
}