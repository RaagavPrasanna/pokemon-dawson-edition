package com.example.pokemon_daws

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.pokemon_daws.databinding.ActivityMainMenuBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainMenu : AppCompatActivity() {

    private lateinit var binding: ActivityMainMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.selectionLayout.visibility = View.GONE
        binding.saveButton.visibility = View.GONE
        binding.loadBar.visibility = View.VISIBLE

        lifecycleScope.launch(Dispatchers.IO) {
            while(MainActivity.isInit == null) {
            }
            withContext(Dispatchers.Main){
                binding.loadBar.visibility = View.GONE
                binding.selectionLayout.visibility = View.VISIBLE
                binding.saveButton.visibility = View.VISIBLE
            }
        }

        binding.changeTeamButton.setOnClickListener{
            val ctIntent = Intent(this, ChangeTeam::class.java)

            startActivity(ctIntent)
        }
        binding.wildEncounterButton.setOnClickListener{
            if(MainActivity.trainer.isTrainerDead()){
                Toast.makeText(this, "Team is dead", Toast.LENGTH_SHORT).show()
            }else{
                val wildBattleIntent = Intent(this, WildBattle::class.java)
                startActivity(wildBattleIntent)
            }
        }

        binding.trainerBattleButton.setOnClickListener {
            if(MainActivity.trainer.isTrainerDead()){
                Toast.makeText(this, "Team is dead", Toast.LENGTH_SHORT).show()
            }else{
                val tbi = Intent(this, TrainerBattle::class.java)
                startActivity(tbi)
            }

        }

        binding.pokecenterButton.setOnClickListener{
            MainActivity.trainer.pokemons.forEach{
                it.hp = it.maxHp;
                it.moves.forEach{
                    it.pp = it.maxPP
                }
            }
            Toast.makeText(this, "Healed all pokemon in team", Toast.LENGTH_SHORT).show()
        }


        binding.saveButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                MainActivity.db.pkDao().insertTrainer(MainActivity.trainer)
            }
        }
    }
}