package com.example.pokemon_daws.fragments

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.pokemon_daws.R
import com.example.pokemon_daws.TrainerBattle
import com.example.pokemon_daws.WildBattle
import com.example.pokemon_daws.databinding.FragmentBattleScreenBinding
import com.example.pokemon_daws.pokemon.Move
import com.example.pokemon_daws.pokemon.Pokemon

class BattleScreen : Fragment(R.layout.fragment_battle_screen) {
    private lateinit var wildBattle: WildBattle
    private lateinit var trainerBattle: TrainerBattle
    private lateinit var binding: FragmentBattleScreenBinding
    private val sharedViewModel: BattleViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBattleScreenBinding.bind(view)
        if(activity is WildBattle) {
            wildBattle = (activity as WildBattle)
        } else if(activity is TrainerBattle) {
            trainerBattle = (activity as TrainerBattle)
        }

        updateScreen()
    }

    fun updateScreen(){
        if(this::wildBattle.isInitialized) {
            binding.opponentSprite.setImageDrawable(BitmapDrawable(sharedViewModel.getOpponentPk().frontImage))
            binding.opponentInfo.setText("${sharedViewModel.getOpponentPk().species.uppercase()}\nLVL ${sharedViewModel.getOpponentPk().level}")
            binding.opponentHp.setText("HP: ${sharedViewModel.getOpponentPk().hp}/${sharedViewModel.getOpponentPk().maxHp}")

            binding.trainerSprite.setImageDrawable(BitmapDrawable(sharedViewModel.getTrainerPk().backImage))
            binding.trainerInfo.setText("${sharedViewModel.getTrainerPk().species.uppercase()}\nLVL ${sharedViewModel.getTrainerPk().level}")
            binding.trainerHp.setText("HP: ${sharedViewModel.getTrainerPk().hp}/${sharedViewModel.getTrainerPk().maxHp}")
        } else if (this::trainerBattle.isInitialized) {
            binding.trainerName.text = "TRAINER: " +trainerBattle.sharedViewModel.OpponentTrainer.name +" " +trainerBattle.sharedViewModel.OpponentTrainer.alivePokeCount() +"/" +trainerBattle.sharedViewModel.OpponentTrainer.pokemons.size
            binding.trainerName.visibility = View.VISIBLE
            binding.opponentSprite.setImageDrawable(BitmapDrawable(sharedViewModel.getOpponentPk().frontImage))
            binding.opponentInfo.setText("${sharedViewModel.getOpponentPk().species.uppercase()}\nLVL ${sharedViewModel.getOpponentPk().level}")
            binding.opponentHp.setText("HP: ${sharedViewModel.getOpponentPk().hp}/${sharedViewModel.getOpponentPk().maxHp}")

            binding.trainerSprite.setImageDrawable(BitmapDrawable(sharedViewModel.getTrainerPk().backImage))
            binding.trainerInfo.setText("${sharedViewModel.getTrainerPk().species.uppercase()}\nLVL ${sharedViewModel.getTrainerPk().level}")
            binding.trainerHp.setText("HP: ${sharedViewModel.getTrainerPk().hp}/${sharedViewModel.getTrainerPk().maxHp}")
        }
    }

    fun passDialogMsg(s: String, inpPokemon: Pokemon, isLastBox: Boolean, newMove: Move) {
        if(this::wildBattle.isInitialized) {
            wildBattle.openDialogBox(s, inpPokemon, isLastBox, newMove)
        } else if(this::trainerBattle.isInitialized) {
            trainerBattle.openDialogBox(s, inpPokemon, isLastBox, newMove)
        }
    }

    fun passPkDialogMsg(s: String) {
        if(this::wildBattle.isInitialized) {
            wildBattle.openPkDialogBox(s)
        } else if (this::trainerBattle.isInitialized) {
            trainerBattle.openPkDialogBox(s)
        }
    }

    fun passBattleDoneMsg(s: String) {
        trainerBattle.openBattleDoneDialogBox(s)
    }

    fun catchPkDialogMsg(s: String, inpPokemon: Pokemon) {
        if(this::wildBattle.isInitialized) {
            wildBattle.openCaughtDialogBox(s, inpPokemon)
        } else if(this::trainerBattle.isInitialized) {
            trainerBattle.openCaughtDialogBox(s, inpPokemon)
        }
    }

    fun trainerDeadDialogMsg(s: String) {
        trainerBattle.openBattleDoneDialogBox(s)
    }

    fun displayToast(s: String) {
        println("displaying toast")
        if(this::wildBattle.isInitialized) {
            Toast.makeText(wildBattle.applicationContext, s, Toast.LENGTH_SHORT).show()
        } else if (this::trainerBattle.isInitialized) {
            Toast.makeText(trainerBattle.applicationContext, s, Toast.LENGTH_SHORT).show()
        }
    }
}