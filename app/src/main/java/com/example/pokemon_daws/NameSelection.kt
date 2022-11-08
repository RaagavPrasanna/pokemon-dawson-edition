package com.example.pokemon_daws

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pokemon_daws.databinding.ActivityNameSelectionBinding

class NameSelection : AppCompatActivity() {

    private lateinit var binding: ActivityNameSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNameSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextButtonTrainerName.setOnClickListener {
            val ssIntent = Intent(this, StarterSelection::class.java)

            startActivity(ssIntent)
        }
    }
}