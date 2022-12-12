package com.example.pokemon_daws.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon_daws.R
import com.example.pokemon_daws.databinding.PokemonTeamItemBinding
import com.example.pokemon_daws.fragments.BattleMenu
import com.example.pokemon_daws.fragments.BattleScreen
import com.example.pokemon_daws.fragments.BattleViewModel
import com.example.pokemon_daws.fragments.PokemonMenu
import com.example.pokemon_daws.pokemon.Pokemon

class PokemonBattleSwapRecyclerViewAdapter(
    private val inpPokemons: MutableList<Pokemon>,
    private val supportFragmentManager: FragmentManager,
    private val pokemonMenu: PokemonMenu,
    private val battleMenu: BattleMenu,
    private val sharedViewModel: BattleViewModel,
) :
    RecyclerView.Adapter<PokemonBattleSwapRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(val binding: PokemonTeamItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            PokemonTeamItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding

        if (position == 0) {
            binding.swapButton.isEnabled = false
            binding.swapButton.text = "ACTIVE"
        } else if (inpPokemons[position].hp == 0) {
            binding.swapButton.isEnabled = false
            binding.swapButton.text = "FAINTED"
        }

        binding.pokemonName.text = inpPokemons[position].name
        binding.pokemonLevel.text = "Lvl " + inpPokemons[position].level.toString()
        binding.pokemonHp.text = inpPokemons[position].hp.toString() + "/" + inpPokemons[position].maxHp.toString()
        binding.pokemonSprite.setImageBitmap(inpPokemons[position].frontImage)

        binding.swapButton.setOnClickListener {
            val tempPokemon = inpPokemons[0]
            inpPokemons[0] = inpPokemons[position]
            inpPokemons[position] = tempPokemon
            sharedViewModel.setTrainerPk(inpPokemons[0])

            supportFragmentManager.beginTransaction().apply {
                this.remove(pokemonMenu)
                this.replace(R.id.battle_menu, battleMenu)
                this.commit()
            }

            sharedViewModel.getBattleScreen().updateScreen()
            sharedViewModel.opponentExecuteMove()
        }
    }

    override fun getItemCount(): Int = inpPokemons.size
}