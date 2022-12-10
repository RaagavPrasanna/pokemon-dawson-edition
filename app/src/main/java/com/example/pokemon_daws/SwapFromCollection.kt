package com.example.pokemon_daws

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemon_daws.adapters.CollectionRecyclerViewAdapter
import com.example.pokemon_daws.databinding.ActivitySwapFromCollectionBinding

class SwapFromCollection : AppCompatActivity() {

    private lateinit var binding: ActivitySwapFromCollectionBinding
    private lateinit var adapter: CollectionRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySwapFromCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var passInd = 0

        intent.extras?.let {
            passInd = it.getInt("pokemon")
        }

        println("opened activity")

        adapter = CollectionRecyclerViewAdapter(MainActivity.trainer.collection.pokemons, passInd)
        binding.swapCollectionRecyclerView.adapter = adapter
        binding.swapCollectionRecyclerView.layoutManager = GridLayoutManager(this, 2)
    }
}