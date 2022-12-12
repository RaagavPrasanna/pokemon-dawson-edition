package com.example.pokemon_daws.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.pokemon_daws.R
import com.example.pokemon_daws.databinding.ActivityMainBinding
import com.example.pokemon_daws.databinding.FragmentItemsMenuBinding

class ItemsMenu() : Fragment(R.layout.fragment_items_menu) {
    private lateinit var binding: FragmentItemsMenuBinding
    private val sharedViewModel: BattleViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentItemsMenuBinding.bind(view)
        binding.potnBtn.setOnClickListener {
            sharedViewModel.usePotion()
        }
        binding.pkballBtn.setOnClickListener {
            sharedViewModel.usePokeball()
        }
    }
}