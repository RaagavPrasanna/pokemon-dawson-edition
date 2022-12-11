package com.example.pokemon_daws

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.pokemon_daws.Controllers.Battle
import com.example.pokemon_daws.databinding.ActivityWildBattleBinding
import com.example.pokemon_daws.fragments.BattleMenu
import com.example.pokemon_daws.fragments.BattleScreen
import com.example.pokemon_daws.fragments.BattleText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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



        screenFrag = BattleScreen.newInstance(this)


        lifecycleScope.launch(Dispatchers.IO) {

        }

        supportFragmentManager.beginTransaction().apply {
            this.add(R.id.battle_screen, screenFrag)
            this.addToBackStack("Screen")
            commit()
        }

        battle = Battle(lifecycleScope, screenFrag)
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