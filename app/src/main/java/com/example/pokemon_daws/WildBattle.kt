package com.example.pokemon_daws

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.pokemon_daws.Controllers.Battle
import com.example.pokemon_daws.databinding.ActivityWildBattleBinding
import com.example.pokemon_daws.fragments.BattleMenu
import com.example.pokemon_daws.fragments.BattleScreen
import com.example.pokemon_daws.fragments.BattleText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WildBattle : AppCompatActivity() {
    private lateinit var screenFrag : BattleScreen
    private lateinit var menuFrag: BattleMenu
    val textFrag = BattleText()
    public lateinit var battle: Battle
    private lateinit var binding: ActivityWildBattleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWildBattleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.battleScreen.visibility = View.GONE
        binding.battleMenu.visibility = View.GONE
        binding.battleMessage.visibility = View.GONE
        binding.loadBar.visibility = View.VISIBLE

        screenFrag = BattleScreen.newInstance(this)
        battle = Battle(lifecycleScope/*, screenFrag*/)

        lifecycleScope.launch(Dispatchers.IO) {
            battle.initBattle()
            while(!battle.gotPk) {
                println("hasnt got")
            }
            withContext(Dispatchers.Main) {
                binding.loadBar.visibility = View.GONE
                binding.battleScreen.visibility = View.VISIBLE
                binding.battleMenu.visibility = View.VISIBLE
                binding.battleMessage.visibility = View.VISIBLE
                screenFrag.updateScreen(battle.opponentPk)
            }
        }

        supportFragmentManager.beginTransaction().apply {
            this.add(R.id.battle_screen, screenFrag)
            this.addToBackStack("Screen")
            commit()
        }

        menuFrag = BattleMenu.newInstance(this, battle)
        supportFragmentManager.beginTransaction().apply {
            this.add(R.id.battle_menu, menuFrag)
            this.addToBackStack("Menu")
            this.add(R.id.battle_message, textFrag)
            this.addToBackStack("Message")
            commit()
        }

    }
}