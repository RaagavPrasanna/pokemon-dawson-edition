package com.example.pokemon_daws.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon_daws.TrainerBattle
import com.example.pokemon_daws.WildBattle
import com.example.pokemon_daws.databinding.MoveListAdapterItemBinding
import com.example.pokemon_daws.pokemon.Move

class NewMoveRecyclerAdapter(
    private val inpMoves: MutableList<Move>,
    private val battle: AppCompatActivity,
    private val alertDialog: AlertDialog,
    private val isLastBox: Boolean,
    private val newMove: Move,
) :
        RecyclerView.Adapter<NewMoveRecyclerAdapter.ViewHolder>() {
            class ViewHolder(val binding: MoveListAdapterItemBinding) : RecyclerView.ViewHolder(binding.root)

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
                ViewHolder(
                    MoveListAdapterItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val binding = holder.binding

                binding.moveName.text = inpMoves[position].name
                binding.movePp.text = inpMoves[position].maxPP.toString()
                binding.moveType.text = inpMoves[position].type

                holder.itemView.setOnClickListener{
                    inpMoves[position] = newMove
                    if(battle is WildBattle) {
                        battle.notifyALlAdapters()
                    } else if(battle is TrainerBattle) {
                        battle.notifyALlAdapters()
                    }
                    alertDialog.dismiss()
                    if(isLastBox) {
                        battle.finish()
                    }
                }
            }

            override fun getItemCount(): Int = inpMoves.size
        }