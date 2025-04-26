package com.example.evex

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.evex.AUTH.AuthViewmodel
import com.example.evex.databinding.ActivityClubDescriptionBinding

class Club_description : AppCompatActivity() {

    private lateinit var binding: ActivityClubDescriptionBinding
    private lateinit var viewmodel: AuthViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityClubDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewmodel = ViewModelProvider(this)[AuthViewmodel::class.java]

        val clubCategories = listOf("Technical", "Cultural", "Art", "Music", "Dance")
        val adapter = ArrayAdapter(this, R.layout.simple_dropdown_item_1line, clubCategories)
        binding.clubCategoryDropdown.setAdapter(adapter)

        // Restore selection from ViewModel if available
        viewmodel.selectedCategory.observe(this) { category ->
            if (!category.isNullOrEmpty()) {
                binding.clubCategoryDropdown.setText(category, false)
            }
        }

        binding.clubCategoryDropdown.setOnItemClickListener { _, _, position, _ ->
            val selected = clubCategories[position]
            viewmodel.setSelectedCategory(selected)
        }




        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}