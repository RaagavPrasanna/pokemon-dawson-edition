package com.example.pokemon_daws.Controllers
//
//import android.util.Log
//import android.widget.Toast
//import androidx.lifecycle.LifecycleCoroutineScope
//import com.example.pokemon_daws.MainActivity
//import com.example.pokemon_daws.WildBattle
//import com.example.pokemon_daws.fragments.BattleMenu
//import com.example.pokemon_daws.fragments.BattleScreen
//import com.example.pokemon_daws.pokemon.Move
//import com.example.pokemon_daws.pokemon.Pokemon
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import kotlin.random.Random
//
//class Battle() {
//    lateinit var lifecycleScope: LifecycleCoroutineScope
//    lateinit var opponentPk: Pokemon
//    var gotPk = false
//
//
//
//    public fun executeMove(move: Move){
//        move.executeMove(trainerPk!!, opponentPk)
//        update()
//    }
//
//    suspend fun initBattle(){
//        gotPk = false
//        val pk = getStartingTrainerPk()
//        if(pk == null){
//            Toast.makeText(screen.context,"Can't fight when whole team fainted", Toast.LENGTH_SHORT).show()
//            screen.requireActivity().finish()
//        }else{
//            trainerPk = pk
//        }
//        withContext(Dispatchers.IO){
//            opponentPk = getRandomPk()
//            Log.i("Opponent pk loaded", opponentPk.toString())
//            gotPk = true
//        }
//    }
//
//    fun update(){
//        screen.updateScreen(opponentPk)
//    }
//
//    // Increase the trainer's pokemon's hp by up to 20
//    fun usePotion() {
//        trainerPk!!.hp += 20
//        if (trainerPk!!.hp > trainerPk!!.maxHp) {
//            trainerPk!!.hp = trainerPk!!.maxHp
//        }
//        screen.updateScreen(opponentPk)
//    }
//
//    fun usePokeBall(): Boolean  {
//        val success = Pokemon_Math.AttemptCapture(opponentPk.hp.toDouble(), opponentPk.maxHp.toDouble())
//
//        if (success) {
//            if (MainActivity.trainer.pokemons.size < 6) {
//                MainActivity.trainer.addPK(opponentPk)
//            }
//            else {
//                MainActivity.trainer.collectPK(opponentPk)
//            }
//        }
//        return success
//    }