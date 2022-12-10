package com.example.pokemon_daws.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemon_daws.Controllers.Battle
import com.example.pokemon_daws.MainActivity
import com.example.pokemon_daws.R
import com.example.pokemon_daws.WildBattle
import com.example.pokemon_daws.adapters.MoveListRecyclerViewAdapter
import com.example.pokemon_daws.databinding.FragmentBattleMenuBinding
import com.example.pokemon_daws.databinding.FragmentMoveMenuBinding
import com.example.pokemon_daws.pokemon.Move


class MoveMenu : Fragment(R.layout.fragment_move_menu) {
    private lateinit var binding: FragmentMoveMenuBinding
    public lateinit var battle: Battle

    private lateinit var moves: MutableList<Move>
    private lateinit var adapter: MoveListRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMoveMenuBinding.bind(view)

        moves = battle.trainerPk.moves
        adapter = MoveListRecyclerViewAdapter(moves, requireContext())
        binding.moveListRecycler.adapter = adapter
        binding.moveListRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    companion object{
        fun newInstance(battle: Battle):MoveMenu{
            val fragment = MoveMenu()
            fragment.battle = battle
            return fragment
        }
    }
}