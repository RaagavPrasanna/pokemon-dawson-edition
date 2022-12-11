package com.example.pokemon_daws.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.pokemon_daws.Controllers.Battle
import com.example.pokemon_daws.R
import com.example.pokemon_daws.WildBattle
import com.example.pokemon_daws.databinding.FragmentBattleMenuBinding

class BattleMenu : Fragment(R.layout.fragment_battle_menu) {
    private lateinit var binding: FragmentBattleMenuBinding
    private lateinit var moveMenu: MoveMenu
    private lateinit var pokemonMenu: PokemonMenu
    private lateinit var battle: Battle
    private val itemMenu = ItemsMenu()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBattleMenuBinding.bind(view)
        battle.initBattle()
        moveMenu = MoveMenu.newInstance(battle)
//        pokemonMenu = PokemonMenu.newInstance()

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

    companion object{
        fun newInstance(act: WildBattle, battle: Battle):BattleMenu{
            val fragment = BattleMenu()
            fragment.battle = battle
            return fragment
        }
    }
}