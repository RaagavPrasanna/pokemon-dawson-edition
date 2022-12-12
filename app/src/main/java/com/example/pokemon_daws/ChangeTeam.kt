package com.example.pokemon_daws

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemon_daws.adapters.PokemonTeamRecyclerViewAdapter
import com.example.pokemon_daws.databinding.ActivityChangeTeamBinding
class ChangeTeam : AppCompatActivity() {

    private lateinit var binding: ActivityChangeTeamBinding
    private lateinit var adapter: PokemonTeamRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = PokemonTeamRecyclerViewAdapter(MainActivity.trainer.pokemons)
        binding.changeTeamRecyclerView.adapter = adapter
        binding.changeTeamRecyclerView.layoutManager = GridLayoutManager(this, 2)

    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }
}