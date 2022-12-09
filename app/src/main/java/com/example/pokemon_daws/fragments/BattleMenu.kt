package com.example.pokemon_daws.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.pokemon_daws.R
import com.example.pokemon_daws.databinding.FragmentBattleMenuBinding
import com.example.pokemon_daws.databinding.FragmentItemsMenuBinding

class BattleMenu : Fragment(R.layout.fragment_battle_menu) {
    private lateinit var binding: FragmentBattleMenuBinding
    val moveMenu = MoveMenu()
    val itemMenu = ItemsMenu()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBattleMenuBinding.bind(view)

        binding.fightBtn.setOnClickListener{
            switchFragment(moveMenu)
        }

        binding.itemBtn.setOnClickListener{
            switchFragment(itemMenu)
        }

        binding.runBtn.setOnClickListener {
            activity?.finish()
        }

        //TODO add Pokemon team picking
    }

    private fun switchFragment(fragment:Fragment){
        val fragManager = requireActivity().supportFragmentManager
        val transac = fragManager.beginTransaction()
        transac.replace(R.id.battle_menu, fragment)
            .addToBackStack("Battle Menu")
            .commit()
    }
}