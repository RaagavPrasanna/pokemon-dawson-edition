package com.example.pokemon_daws.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pokemon_daws.R
import com.example.pokemon_daws.databinding.ActivityMainBinding
import com.example.pokemon_daws.databinding.FragmentItemsMenuBinding

class ItemsMenu : Fragment(R.layout.fragment_items_menu) {
    private lateinit var binding: FragmentItemsMenuBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentItemsMenuBinding.bind(view)
    }

}