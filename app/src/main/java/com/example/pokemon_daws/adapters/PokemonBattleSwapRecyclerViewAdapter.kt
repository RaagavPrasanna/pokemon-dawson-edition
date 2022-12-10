package com.example.pokemon_daws.adapters

import android.content.res.Resources
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon_daws.MainActivity
import com.example.pokemon_daws.databinding.PokemonTeamItemBinding
import com.example.pokemon_daws.pokemon.Pokemon

class PokemonBattleSwapRecyclerViewAdapter():
        RecyclerView.Adapter<PokemonBattleSwapRecyclerViewAdapter.ViewHolder>() {
                class ViewHolder(val binding: PokemonTeamItemBinding): RecyclerView.ViewHolder(binding.root)

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
                        ViewHolder(
                                PokemonTeamItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                        )

                override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                        val binding = holder.binding
                        val pokemon: Pokemon = MainActivity.trainer.pokemons[position]

                        if(position == 0) {
                                binding.swapButton.isEnabled = false
                                binding.swapButton.text = "ACTIVE"
                        } else if(MainActivity.trainer.pokemons[position].hp == 0) {
                                println("hp changed")
                                binding.swapButton.isEnabled = false
                                binding.swapButton.text = "FAINTED"
                        }


                        binding.pokemonName.text = pokemon.name
                        binding.pokemonLevel.text = "Lvl " +pokemon.level.toString()
                        binding.pokemonHp.text = pokemon.hp.toString() +"/" +pokemon.maxHp.toString()
                        binding.pokemonSprite.setImageBitmap(pokemon.frontImage)

                        binding.swapButton.setOnClickListener {
                                val tempPokemon = MainActivity.trainer.pokemons[0]
                                println("bring to first: " +MainActivity.trainer.pokemons[position].name +" position: " +position)
                                println("Bring to " +position +" " +MainActivity.trainer.pokemons[0].name)

                                MainActivity.trainer.pokemons[0] = MainActivity.trainer.pokemons[position]
                                MainActivity.trainer.pokemons[position] = tempPokemon

                                for (pk in MainActivity.trainer.pokemons) {
                                        println(pk.name +" : " +pk.hp)
                                }

//                                if(MainActivity.trainer.pokemons[position].hp == 0) {
//                                        binding.swapButton.isEnabled = false
//                                        binding.swapButton.text = "FAINTED"
//                                } else {
//                                        binding.swapButton.isEnabled = true
//                                        binding.swapButton.text = "SWAP"
//                                }

                                notifyDataSetChanged()

                        }

//                        binding.pokemonSprite.layoutParams.height =
//                                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                                        30F, Resources.getSystem().displayMetrics)
//                                        .toInt()
                }

                override fun getItemCount(): Int = MainActivity.trainer.pokemons.size
        }