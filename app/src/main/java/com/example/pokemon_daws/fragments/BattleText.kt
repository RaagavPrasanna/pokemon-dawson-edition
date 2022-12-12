package com.example.pokemon_daws.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.pokemon_daws.R
import com.example.pokemon_daws.databinding.FragmentBattleMenuBinding
import com.example.pokemon_daws.databinding.FragmentBattleTextBinding
import com.example.pokemon_daws.databinding.FragmentItemsMenuBinding

/**
 * A simple [Fragment] subclass.
 * Use the [BattleText.newInstance] factory method to
 * create an instance of this fragment.
 */
class BattleText : Fragment(R.layout.fragment_battle_text) {
    private lateinit var binding: FragmentBattleTextBinding
    private val sharedViewModel: BattleViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBattleTextBinding.bind(view)
        sharedViewModel.setBattleText(this)
        sharedViewModel.startBattle()
    }

    fun setOpponentText(text: String){
        binding.opponentMessage.setText(text)
    }

    fun setTrainerText(text: String){
        binding.trainerMessage.setText(text)
    }
}