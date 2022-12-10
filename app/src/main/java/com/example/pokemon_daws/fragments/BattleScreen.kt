package com.example.pokemon_daws.fragments

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.pokemon_daws.Battle
import com.example.pokemon_daws.MainActivity
import com.example.pokemon_daws.R
import com.example.pokemon_daws.WildBattle
import com.example.pokemon_daws.databinding.FragmentBattleScreenBinding
import com.example.pokemon_daws.pokemon.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class BattleScreen : Fragment(R.layout.fragment_battle_screen) {
    private lateinit var wildBattle: WildBattle
    private lateinit var binding: FragmentBattleScreenBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBattleScreenBinding.bind(view)
    }

    public fun updateScreen(opponentPk: Pokemon){
        binding.opponentSprite.setImageDrawable(BitmapDrawable(opponentPk.frontImage))
        binding.opponentInfo.setText("${opponentPk.species.uppercase()}\nLVL ${opponentPk.level}")
        binding.opponentHp.setText("HP: ${opponentPk.hp}/${opponentPk.maxHp}")

        binding.trainerSprite.setImageDrawable(BitmapDrawable(wildBattle.battle.trainerPk.backImage))
        binding.trainerInfo.setText("${wildBattle.battle.trainerPk.species.uppercase()}\nLVL ${wildBattle.battle.trainerPk.level}")
        binding.trainerHp.setText("HP: ${wildBattle.battle.trainerPk.hp}/${wildBattle.battle.trainerPk.maxHp}")
    }


    companion object{
        fun newInstance(act: WildBattle):BattleScreen{
            val fragment = BattleScreen()
            fragment.wildBattle = act
            return fragment
        }
    }
}