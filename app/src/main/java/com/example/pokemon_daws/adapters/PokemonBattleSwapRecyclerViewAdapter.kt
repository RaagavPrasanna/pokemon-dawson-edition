package com.example.pokemon_daws.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon_daws.R
import com.example.pokemon_daws.databinding.PokemonTeamItemBinding
import com.example.pokemon_daws.fragments.BattleMenu
import com.example.pokemon_daws.fragments.BattleViewModel
import com.example.pokemon_daws.fragments.PokemonMenu
import com.example.pokemon_daws.pokemon.Pokemon

class PokemonBattleSwapRecyclerViewAdapter(
        private val inpPokemons: MutableList<Pokemon>,
        private val supportFragmentManager: FragmentManager,
        private val pokemonMenu: PokemonMenu,
        private val battleMenu: BattleMenu,
        private val sharedViewModel: BattleViewModel,
        ):
        RecyclerView.Adapter<PokemonBattleSwapRecyclerViewAdapter.ViewHolder>() {
        class ViewHolder(val binding: PokemonTeamItemBinding): RecyclerView.ViewHolder(binding.root)


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
                        ViewHolder(
                                PokemonTeamItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                        )

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val binding = holder.binding

                if(position == 0) {
                        binding.swapButton.isEnabled = false
                        binding.swapButton.text = "ACTIVE"
                } else if(inpPokemons[position].hp == 0) {
                        println("hp changed")
                        binding.swapButton.isEnabled = false
                        binding.swapButton.text = "FAINTED"
                }


                binding.pokemonName.text = inpPokemons[position].name
                binding.pokemonLevel.text = "Lvl " +inpPokemons[position].level.toString()
                binding.pokemonHp.text = inpPokemons[position].hp.toString() +"/" +inpPokemons[position].maxHp.toString()
                binding.pokemonSprite.setImageBitmap(inpPokemons[position].frontImage)

                binding.swapButton.setOnClickListener {
                        val tempPokemon = inpPokemons[0]
//                                println("bring to first: " +MainActivity.trainer.pokemons[position].name +" position: " +position)
//                                println("Bring to " +position +" " +MainActivity.trainer.pokemons[0].name)

                        inpPokemons[0] = inpPokemons[position]
                        inpPokemons[position] = tempPokemon

//                                for (pk in MainActivity.trainer.pokemons) {
//                                        println(pk.name +" : " +pk.hp)
//                                }

//                                if(MainActivity.trainer.pokemons[position].hp == 0) {
//                                        binding.swapButton.isEnabled = false
//                                        binding.swapButton.text = "FAINTED"
//                                } else {
//                                        binding.swapButton.isEnabled = true
//                                        binding.swapButton.text = "SWAP"
//                                }

                        supportFragmentManager.beginTransaction().apply {
                                this.remove(pokemonMenu)
                                this.replace(R.id.battle_menu, battleMenu)
                                this.commit()
                        }
//                        Battle.instance.update()
                }

//                        binding.pokemonSprite.layoutParams.height =
//                                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                                        30F, Resources.getSystem().displayMetrics)
//                                        .toInt()
        }

                override fun getItemCount(): Int = inpPokemons.size
        }