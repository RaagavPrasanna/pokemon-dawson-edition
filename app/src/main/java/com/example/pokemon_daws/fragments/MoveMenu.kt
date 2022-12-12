package com.example.pokemon_daws.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemon_daws.R
import com.example.pokemon_daws.adapters.MoveListRecyclerViewAdapter
import com.example.pokemon_daws.databinding.FragmentMoveMenuBinding
import com.example.pokemon_daws.pokemon.Move


class MoveMenu : Fragment(R.layout.fragment_move_menu) {
    private lateinit var binding: FragmentMoveMenuBinding
    private val sharedViewModel: BattleViewModel by activityViewModels()

    private lateinit var moves: MutableList<Move>
    private lateinit var adapter: MoveListRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMoveMenuBinding.bind(view)

        moves = sharedViewModel.getTrainerPk().moves
        adapter = MoveListRecyclerViewAdapter(moves, requireContext(), sharedViewModel, requireActivity().supportFragmentManager)
        binding.moveListRecycler.adapter = adapter
        binding.moveListRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
    }
}