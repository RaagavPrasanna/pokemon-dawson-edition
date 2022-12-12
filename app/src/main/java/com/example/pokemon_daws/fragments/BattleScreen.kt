package com.example.pokemon_daws.fragments

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import com.example.pokemon_daws.R
import com.example.pokemon_daws.WildBattle
import com.example.pokemon_daws.databinding.FragmentBattleScreenBinding
import com.example.pokemon_daws.pokemon.Move
import com.example.pokemon_daws.pokemon.Pokemon

class BattleScreen : Fragment(R.layout.fragment_battle_screen) {
    private lateinit var wildBattle: WildBattle
    private lateinit var binding: FragmentBattleScreenBinding
    private val sharedViewModel: BattleViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBattleScreenBinding.bind(view)
        wildBattle = (activity as WildBattle)

        updateScreen()
    }

    public fun updateScreen(){
        binding.opponentSprite.setImageDrawable(BitmapDrawable(sharedViewModel.getOpponentPk().frontImage))
        binding.opponentInfo.setText("${sharedViewModel.getOpponentPk().species.uppercase()}\nLVL ${sharedViewModel.getOpponentPk().level}")
        binding.opponentHp.setText("HP: ${sharedViewModel.getOpponentPk().hp}/${sharedViewModel.getOpponentPk().maxHp}")

        binding.trainerSprite.setImageDrawable(BitmapDrawable(sharedViewModel.getTrainerPk().backImage))
        binding.trainerInfo.setText("${sharedViewModel.getTrainerPk().species.uppercase()}\nLVL ${sharedViewModel.getTrainerPk().level}")
        binding.trainerHp.setText("HP: ${sharedViewModel.getTrainerPk().hp}/${sharedViewModel.getTrainerPk().maxHp}")
    }

    fun passDialogMsg(s: String, inpPokemon: Pokemon, isLastBox: Boolean, newMove: Move) {
        wildBattle.openDialogBox(s, inpPokemon, isLastBox, newMove)
    }
}