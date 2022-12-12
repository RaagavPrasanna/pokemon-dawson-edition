package com.example.pokemon_daws

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.pokemon_daws.databinding.ActivityWildBattleBinding
import com.example.pokemon_daws.fragments.BattleMenu
import com.example.pokemon_daws.fragments.BattleScreen
import com.example.pokemon_daws.fragments.BattleText
import com.example.pokemon_daws.fragments.BattleViewModel
import com.example.pokemon_daws.pokemon.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class WildBattle : AppCompatActivity() {
    private lateinit var screenFrag : BattleScreen
    private lateinit var menuFrag: BattleMenu
    private lateinit var textFrag : BattleText
    private lateinit var binding: ActivityWildBattleBinding
    private val sharedViewModel: BattleViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWildBattleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.battleScreen.visibility = View.GONE
        binding.battleMenu.visibility = View.GONE
        binding.battleMessage.visibility = View.GONE
        binding.loadBar.visibility = View.VISIBLE

        textFrag = BattleText()
        screenFrag = BattleScreen()
        menuFrag = BattleMenu()

        runBlocking {
            sharedViewModel.setOpponentPk(getOpponentPk())
        }

        binding.loadBar.visibility = View.GONE
        binding.battleScreen.visibility = View.VISIBLE
        binding.battleMenu.visibility = View.VISIBLE
        binding.battleMessage.visibility = View.VISIBLE

        supportFragmentManager.beginTransaction().apply {
            this.add(R.id.battle_screen, screenFrag)
            this.add(R.id.battle_menu, menuFrag)
            this.add(R.id.battle_message, textFrag)
            commit()
        }

        sharedViewModel.setBattleScreen(screenFrag)
        sharedViewModel.setBattleText(textFrag)
    }

    private suspend fun getOpponentPk(): Pokemon {
        var pk: Pokemon? = null
        lifecycleScope.launch(Dispatchers.IO){
            pk = sharedViewModel.getRandomPk()
        }.join()
        return pk!!
    }
}