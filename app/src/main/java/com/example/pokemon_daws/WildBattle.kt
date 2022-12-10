package com.example.pokemon_daws

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.pokemon_daws.Controllers.Battle
import com.example.pokemon_daws.fragments.BattleMenu
import com.example.pokemon_daws.fragments.BattleScreen
import com.example.pokemon_daws.fragments.BattleText

class WildBattle : AppCompatActivity() {
    private lateinit var screenFrag : BattleScreen
    private lateinit var menuFrag: BattleMenu
    val textFrag = BattleText()
    public lateinit var battle: Battle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wild_battle)
        screenFrag = BattleScreen.newInstance(this)

        supportFragmentManager.beginTransaction().apply {
            this.add(R.id.battle_screen, screenFrag)
            commit()
        }

        battle = Battle(lifecycleScope, screenFrag)
        menuFrag = BattleMenu.newInstance(this, battle)
        supportFragmentManager.beginTransaction().apply {
            this.add(R.id.battle_menu, menuFrag)
            this.add(R.id.battle_message, textFrag)
            commit()
        }

    }
}