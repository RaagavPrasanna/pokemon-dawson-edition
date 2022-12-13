package com.example.pokemon_daws.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemon_daws.MainActivity
import com.example.pokemon_daws.R
import com.example.pokemon_daws.adapters.PokemonBattleSwapRecyclerViewAdapter
import com.example.pokemon_daws.databinding.FragmentPokemonMenuBinding

class PokemonMenu(private val battleMenu: BattleMenu) : Fragment(R.layout.fragment_pokemon_menu) {
    private lateinit var binding: FragmentPokemonMenuBinding
    private lateinit var adapter: PokemonBattleSwapRecyclerViewAdapter
    private val sharedViewModel: BattleViewModel by activityViewModels()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentPokemonMenuBinding.bind(view)
        adapter = PokemonBattleSwapRecyclerViewAdapter(MainActivity.trainer.pokemons, requireActivity().supportFragmentManager, this, battleMenu, sharedViewModel)
        binding.pokemonListRecycler.adapter = adapter
        binding.pokemonListRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    companion object {
        fun newInstance(battleMenu: BattleMenu): PokemonMenu {
            return PokemonMenu(battleMenu)
        }
    }
}