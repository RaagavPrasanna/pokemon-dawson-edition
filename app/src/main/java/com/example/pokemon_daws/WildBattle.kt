package com.example.pokemon_daws

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon_daws.adapters.NewMoveRecyclerAdapter
import com.example.pokemon_daws.databinding.ActivityWildBattleBinding
import com.example.pokemon_daws.fragments.BattleMenu
import com.example.pokemon_daws.fragments.BattleScreen
import com.example.pokemon_daws.fragments.BattleText
import com.example.pokemon_daws.fragments.BattleViewModel
import com.example.pokemon_daws.pokemon.Move
import com.example.pokemon_daws.pokemon.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class WildBattle : AppCompatActivity() {
    private lateinit var screenFrag : BattleScreen
    private lateinit var menuFrag: BattleMenu
    val textFrag = BattleText()
    private lateinit var binding: ActivityWildBattleBinding
    private val sharedViewModel: BattleViewModel by viewModels()
    private lateinit var builder: AlertDialog.Builder
    private var allAdapters: MutableList<NewMoveRecyclerAdapter> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWildBattleBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.battleScreen.visibility = View.GONE
        binding.battleMenu.visibility = View.GONE
        binding.battleMessage.visibility = View.GONE
        binding.loadBar.visibility = View.VISIBLE

        screenFrag = BattleScreen()

        runBlocking {
            sharedViewModel.setOpponentPk(getOpponentPk())
        }
//             battle.update()
//                screenFrag.updateScreen(battle.opponentPk)
        binding.loadBar.visibility = View.GONE
        binding.battleScreen.visibility = View.VISIBLE
        binding.battleMenu.visibility = View.VISIBLE
        binding.battleMessage.visibility = View.VISIBLE

        supportFragmentManager.beginTransaction().apply {
            this.add(R.id.battle_screen, screenFrag)
//            this.addToBackStack("Screen")
            commit()
        }

        menuFrag = BattleMenu()
        supportFragmentManager.beginTransaction().apply {
            this.add(R.id.battle_menu, menuFrag)
//            this.addToBackStack("Menu")
            this.add(R.id.battle_message, textFrag)
//            this.addToBackStack("Message")
            commit()
        }

        sharedViewModel.setBattleScreen(screenFrag)
    }

    private suspend fun getOpponentPk(): Pokemon {
        var pk: Pokemon? = null
        lifecycleScope.launch(Dispatchers.IO){
            pk = sharedViewModel.getRandomPk()
        }.join()
        return pk!!
    }
    fun getNum(): Int{
        return 5
    }

    fun sayHi() {
        println("hi")
    }
    fun openDialogBox(msg: String, inpPokemon: Pokemon, isLastBox: Boolean, newMove: Move) {
        println("opening box")



        val dialogView = layoutInflater.inflate(R.layout.new_move_dialog, null)

        val rv = dialogView.findViewById<RecyclerView>(R.id.new_move_recycler_view)



        builder = AlertDialog.Builder(this)
            .setTitle(inpPokemon.name +" wants to learn: " +newMove.name +", choose a move to replace")
            .setView(dialogView)
            .setMessage(msg)
            .setCancelable(false)
            .setPositiveButton("DON'T LEARN"){di, it ->
                if(isLastBox) {
                    finish()
                }
            }

        val alertDialog = builder.create()

        val adapter = NewMoveRecyclerAdapter(inpPokemon.moves, this, alertDialog, isLastBox, newMove)

        allAdapters.add(adapter)

        rv.adapter = adapter

        rv.layoutManager = GridLayoutManager(this, 2)

        alertDialog.show()

    }

    fun notifyALlAdapters() {
        println("call notify all adapters")
        for (i in allAdapters) {
            i.notifyDataSetChanged()
        }
    }
}