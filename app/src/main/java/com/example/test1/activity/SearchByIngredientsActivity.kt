package com.example.test1.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import com.example.test1.adapter.RecipeAdapter
import com.example.test1.databinding.ActivitySearchByIngredientsBinding
import com.google.android.material.snackbar.Snackbar
import com.example.test1.repository.RecipeRepository
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class SearchByIngredientsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchByIngredientsBinding
    private val apiKey = "78e80a67aed940ec8decc16468b5b38d" // Remplace par ta clé API Spoonacular
    private val repository = RecipeRepository()
    private val selectedIngredients = mutableSetOf<String>() // Store selected ingredients

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchByIngredientsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        setupToolbar()

        // Add user-typed ingredient
        binding.addButton.setOnClickListener {
            val ingredient = binding.ingredientFieldInput.text.toString().trim()
            if (ingredient.isNotEmpty() && !selectedIngredients.contains(ingredient)) {
                addChipToGroup(ingredient, binding.chipGroupSelectedIngredients)
                selectedIngredients.add(ingredient) // Add to selected ingredients
                binding.ingredientFieldInput.text?.clear()
            }
        }

        // Search button click
        binding.searchButton.setOnClickListener {
            if (selectedIngredients.isNotEmpty()) {
                searchRecipes()
            } else {
                Snackbar.make(binding.root, "Select at least one ingredient!", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Search Recipes"

        binding.toolbar.overflowIcon
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun addChipToGroup(ingredient: String, chipGroup: ChipGroup) {
        val chip = Chip(this).apply {
            text = ingredient
            isCloseIconVisible = true // Show "X" for removal
            isCheckable = true // Allow selection toggle
            isChecked = true // Automatically select when added

            setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedIngredients.add(ingredient)
                } else {
                    selectedIngredients.remove(ingredient)
                }
            }

            setOnCloseIconClickListener {
                selectedIngredients.remove(ingredient) // Remove from selected set
                chipGroup.removeView(this) // Remove chip from UI
            }
        }
        chipGroup.addView(chip)
    }

    // Call API with selected ingredients
    private fun searchRecipes() {
        val ingredientsQuery = selectedIngredients.joinToString(",") { it.lowercase() } // Format for API
        Log.d("Search", "Ingredients: ${ingredientsQuery}")
        lifecycleScope.launch {
            try {
                binding.recyclerView.visibility = View.GONE
                binding.searchButton.isEnabled = false
                binding.progressBar.visibility = View.VISIBLE

                val recipes = repository.searchRecipesByIngredients(apiKey, ingredientsQuery, 8)

                binding.progressBar.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                binding.searchButton.isEnabled = true

                binding.recyclerView.adapter = RecipeAdapter(recipes) { recipeId ->
                    navigateToRecipeDetails(recipeId)
                }
            } catch (e: Exception) {
                binding.progressBar.visibility = View.GONE

                // Show Google Material Snackbar on error
                Snackbar.make(binding.root, "Error fetching recipes. Try again!", Snackbar.LENGTH_LONG)
                    .setAction("Retry") {
                        searchRecipes()
                    }
                    .show()
            }
        }
    }

    private fun navigateToRecipeDetails(recipeId: Int) {
        val intent = Intent(this, RecipeDetailActivity::class.java)
        intent.putExtra("RECIPE_ID", recipeId)
        startActivity(intent)
    }
}