package com.example.test1.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test1.adapter.IngredientsAdapter
import com.example.test1.data.RecipeDetails
import com.example.test1.databinding.ActivityRecipeDetailBinding
import com.example.test1.repository.RecipeRepository
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class RecipeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeDetailBinding
    private val repository = RecipeRepository()
    private val apiKey = "78e80a67aed940ec8decc16468b5b38d"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        val recipeId = intent.getIntExtra("RECIPE_ID", -1)
        if (recipeId != -1) {
            fetchRecipeDetails(recipeId)
        }
        Log.d("RecipeDetailActivity", "Recipe ID: $recipeId")
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Recipe Details"

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun fetchRecipeDetails(recipeId: Int) {
        lifecycleScope.launch {
            val recipe = repository.fetchRecipeDetails(apiKey, recipeId)
            if (recipe != null) {
                displayRecipeDetails(recipe)
            }
        }
    }

    private fun displayRecipeDetails(recipeDetails: RecipeDetails) {
        // Set text values
        binding.collapsingToolbar.title = recipeDetails.title
        binding.healthScore.text = "${recipeDetails.healthScore}"
        binding.totalTime.text = "${recipeDetails.readyInMinutes} min"
        binding.servingsCount.text = recipeDetails.servings.toString()

        // Load image using Picasso
        Picasso.get().load(recipeDetails.image).into(binding.recipeImage)

        // Set source URL as a clickable link
        binding.sourceButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(recipeDetails.sourceUrl))
            startActivity(intent)
        }

        // Populate ingredients RecyclerView
        val adapter = IngredientsAdapter(recipeDetails.extendedIngredients)
        binding.ingredientsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.ingredientsRecyclerView.adapter = adapter

        // Handle favorite button
        binding.fabFavorite.setOnClickListener {
            // Toggle favorite state (implement logic)
        }
    }
}
