package com.example.pokemon_daws

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.pokemon_daws.databinding.PokemonTeamItemBinding
import com.example.pokemon_daws.pokemon.Pokemon

class CollectionRecyclerViewAdapter(private val inpPokemon: MutableList<Pokemon>, private val swapInd: Int):
    RecyclerView.Adapter<CollectionRecyclerViewAdapter.CollectionTeamViewHolder>() {

        class CollectionTeamViewHolder(val binding: PokemonTeamItemBinding) : ViewHolder(binding.root)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionTeamViewHolder =
            CollectionTeamViewHolder(
                PokemonTeamItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

        override fun onBindViewHolder(holder: CollectionTeamViewHolder, position: Int) {
            val binding = holder.binding
            val pokemon: Pokemon = inpPokemon[position]

            println(swapInd)

            binding.swapButton.text = "GET"
            binding.pokemonName.text = pokemon.name
            binding.pokemonLevel.text = "Lvl " +pokemon.level.toString()
            binding.pokemonHp.text = pokemon.hp.toString() +"/" +pokemon.baseMaxHp.toString()
            binding.pokemonSprite.setImageBitmap(pokemon.frontImage)

            binding.swapButton.setOnClickListener {
                val tempPokemon1 = MainActivity.trainer.pokemons[swapInd]
                val tempPokemon2 = inpPokemon[position]

                inpPokemon[position] = tempPokemon1
                MainActivity.trainer.collection.pokemons[position] = tempPokemon1

                MainActivity.trainer.pokemons[swapInd] = tempPokemon2

                notifyDataSetChanged()
            }
        }

    override fun getItemCount(): Int = inpPokemon.size
}