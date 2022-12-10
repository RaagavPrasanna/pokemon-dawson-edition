package com.example.pokemon_daws

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pokemon_daws.databinding.ActivityNameSelectionBinding
import com.example.pokemon_daws.pokemon.Pokemon
import com.example.pokemon_daws.pokemon.storable.Collection
import com.example.pokemon_daws.pokemon.storable.Trainer

class NameSelection : AppCompatActivity() {

    private lateinit var binding: ActivityNameSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNameSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextButtonTrainerName.setOnClickListener {
            val nameStr = binding.nameEditText.text.toString()

            if(nameStr.isEmpty()) {
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, getString(R.string.enter_name_toast), duration)
                toast.show()
            } else {
                MainActivity.trainer = Trainer(mutableListOf<Pokemon>(), nameStr, Collection())

                val ssIntent = Intent(this, StarterSelection::class.java)

                startActivity(ssIntent)
            }

        }
    }
    override fun onResume() {
        super.onResume()
        MainActivity.isInit = null
    }
}