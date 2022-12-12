package com.example.pokemon_daws.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.pokemon_daws.R
import com.example.pokemon_daws.databinding.FragmentBattleMenuBinding

class BattleMenu : Fragment(R.layout.fragment_battle_menu) {
    private lateinit var binding: FragmentBattleMenuBinding
    private val sharedViewModel: BattleViewModel by activityViewModels()

    private lateinit var moveMenu: MoveMenu
    private lateinit var pokemonMenu: PokemonMenu
    private val itemMenu = ItemsMenu()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBattleMenuBinding.bind(view)

        moveMenu = MoveMenu()

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
        binding.pokemonBtn.setOnClickListener {
            switchFragment(PokemonMenu.newInstance(this))
        }
    }

    private fun switchFragment(fragment:Fragment){
        val fragManager = requireActivity().supportFragmentManager
        val transac = fragManager.beginTransaction()
        transac.replace(R.id.battle_menu, fragment)
            .addToBackStack("Battle Menu Fragment")
            .commit()
    }

}