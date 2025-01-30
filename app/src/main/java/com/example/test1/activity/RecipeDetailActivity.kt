package com.example.test1.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
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
        // Set up the toolbar with a back button
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Recipe Details"

        // Handle the back button click
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
        binding.recipeTitle.text = recipeDetails.title
        binding.recipeServings.text = "Servings: ${recipeDetails.servings}"
        binding.recipeReadyIn.text = "Ready in ${recipeDetails.readyInMinutes} mins"
//        binding.recipeSourceUrl.text = recipeDetails.sourceUrl

        // Display ingredients
        val ingredientsText = recipeDetails.extendedIngredients.joinToString("\n") {
            "${it.amount} ${it.unit} ${it.name}"
        }
        binding.recipeIngredients.text = ingredientsText

        // Load image using Picasso
        Picasso.get().load(recipeDetails.image).into(binding.recipeImage)

        // Set sourceUrl as a clickable link
        binding.recipeSourceUrl.text = recipeDetails.sourceUrl
        binding.recipeSourceUrl.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(recipeDetails.sourceUrl))
            startActivity(intent)
        }
    }
}