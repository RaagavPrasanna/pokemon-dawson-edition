package com.example.pokemon_daws.fragments

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
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

    fun updateScreen(){
        binding.opponentSprite.setImageDrawable(BitmapDrawable(sharedViewModel.getOpponentPk().frontImage))
        binding.opponentInfo.text = "${sharedViewModel.getOpponentPk().species.uppercase()}\nLVL ${sharedViewModel.getOpponentPk().level}"
        binding.opponentHp.text = "HP: ${sharedViewModel.getOpponentPk().hp}/${sharedViewModel.getOpponentPk().maxHp}"

        binding.trainerSprite.setImageDrawable(BitmapDrawable(sharedViewModel.getTrainerPk().backImage))
        binding.trainerInfo.text = "${sharedViewModel.getTrainerPk().species.uppercase()}\nLVL ${sharedViewModel.getTrainerPk().level}"
        binding.trainerHp.text = "HP: ${sharedViewModel.getTrainerPk().hp}/${sharedViewModel.getTrainerPk().maxHp}"
    }

    fun passDialogMsg(s: String, inpPokemon: Pokemon, isLastBox: Boolean, newMove: Move) {
        wildBattle.openDialogBox(s, inpPokemon, isLastBox, newMove)
    }

    fun passPkDialogMsg(s: String) {
        wildBattle.openPkDialogBox(s)
    }

    fun catchPkDialogMsg(s: String, inpPokemon: Pokemon) {
        wildBattle.openCaughtDialogBox(s, inpPokemon)
    }

    fun displayToast(s: String) {
        println("displaying toast")
        Toast.makeText(wildBattle.applicationContext, s, Toast.LENGTH_SHORT).show()
    }
}