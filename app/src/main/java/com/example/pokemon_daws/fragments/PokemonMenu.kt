package com.example.pokemon_daws.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemon_daws.Controllers.Battle
import com.example.pokemon_daws.R
import com.example.pokemon_daws.adapters.PokemonBattleSwapRecyclerViewAdapter
import com.example.pokemon_daws.databinding.FragmentPokemonMenuBinding

class PokemonMenu : Fragment(R.layout.fragment_pokemon_menu) {
    private lateinit var binding: FragmentPokemonMenuBinding
    private lateinit var adapter: PokemonBattleSwapRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPokemonMenuBinding.bind(view)
        adapter = PokemonBattleSwapRecyclerViewAdapter()
        binding.pokemonListRecycler.adapter = adapter
        binding.pokemonListRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    companion object {
        fun newInstance(): PokemonMenu {
            return PokemonMenu()
        }
    }
}