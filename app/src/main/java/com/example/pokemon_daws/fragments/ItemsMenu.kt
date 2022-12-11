package com.example.pokemon_daws.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pokemon_daws.Controllers.Battle
import com.example.pokemon_daws.R
import com.example.pokemon_daws.databinding.ActivityMainBinding
import com.example.pokemon_daws.databinding.FragmentItemsMenuBinding

class ItemsMenu() : Fragment(R.layout.fragment_items_menu) {
    private lateinit var binding: FragmentItemsMenuBinding
    private lateinit var battle: Battle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentItemsMenuBinding.bind(view)
        binding.potnBtn.setOnClickListener {
            battle.usePotion()
        }
        binding.pkballBtn.setOnClickListener {
            if (battle.usePokeBall()) {
                activity?.finish()
            }
        }
    }

    fun setBattle(battle_in: Battle) {
        battle = battle_in
    }
}