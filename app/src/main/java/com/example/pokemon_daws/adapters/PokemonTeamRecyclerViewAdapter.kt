package com.example.pokemon_daws.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.pokemon_daws.SwapFromCollection
import com.example.pokemon_daws.databinding.PokemonTeamItemBinding
import com.example.pokemon_daws.pokemon.Pokemon

class PokemonTeamRecyclerViewAdapter(private val inpPokemon: MutableList<Pokemon>):
        RecyclerView.Adapter<PokemonTeamRecyclerViewAdapter.PokemonTeamViewHolder>() {

            class PokemonTeamViewHolder(val binding: PokemonTeamItemBinding) : ViewHolder(binding.root)
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonTeamViewHolder =
                PokemonTeamViewHolder(
                    PokemonTeamItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )

            override fun onBindViewHolder(holder: PokemonTeamViewHolder, position: Int) {
                val binding = holder.binding
                val pokemon: Pokemon = inpPokemon[position]
                val context = binding.root.context

                binding.pokemonName.text = pokemon.name
                binding.pokemonLevel.text = "Lvl " +pokemon.level.toString()
                binding.pokemonHp.text = pokemon.hp.toString() +"/" +pokemon.maxHp.toString()
                binding.pokemonSprite.setImageBitmap(pokemon.frontImage)

                binding.swapButton.setOnClickListener{
                    val ctIntent = Intent(context, SwapFromCollection::class.java).also {
                        it.putExtra("pokemon", position)
                    }

                    context.startActivity(ctIntent)
                }
            }

            override fun getItemCount(): Int = inpPokemon.size
        }
