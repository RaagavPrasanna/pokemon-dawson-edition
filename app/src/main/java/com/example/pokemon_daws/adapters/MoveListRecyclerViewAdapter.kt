package com.example.pokemon_daws.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon_daws.databinding.MoveListAdapterItemBinding
import com.example.pokemon_daws.pokemon.Move

class MoveListRecyclerViewAdapter(private var moves:MutableList<Move>, private val context: Context):
    RecyclerView.Adapter<MoveListRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(val binding: MoveListAdapterItemBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MoveListAdapterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        val move = moves[position]
        binding.movePp.setText("PP:${move.pp}/${move.maxPP}")
        binding.moveName.setText(move.name.uppercase())
        binding.moveType.setText(move.type.uppercase())
        holder.itemView.setOnClickListener{
        }
    }

    override fun getItemCount(): Int = moves.size

}