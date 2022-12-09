package com.example.pokemon_daws

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.pokemon_daws.databinding.PokemonTeamItemBinding
import com.example.pokemon_daws.pokemon.Pokemon

class PokemonTeamRecyclerViewAdapter(private val inpPokemon: List<Pokemon>):
        RecyclerView.Adapter<PokemonTeamRecyclerViewAdapter.PokemonTeamViewHolder>() {

            class PokemonTeamViewHolder(val binding: PokemonTeamItemBinding) : ViewHolder(binding.root)
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonTeamViewHolder =
                PokemonTeamViewHolder(
                    PokemonTeamItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )

            override fun onBindViewHolder(holder: PokemonTeamViewHolder, position: Int) {
                val binding = holder.binding
                val pokemon: Pokemon = inpPokemon[position]

                binding.pokemonName.text = pokemon.name
                binding.pokemonLevel.text = "Lvl " +pokemon.level.toString()
                binding.pokemonHp.text = pokemon.hp.toString() +"/" +pokemon.baseMaxHp.toString()
                binding.pokemonSprite.setImageBitmap(pokemon.frontImage)
            }

            override fun getItemCount(): Int = inpPokemon.size
        }
