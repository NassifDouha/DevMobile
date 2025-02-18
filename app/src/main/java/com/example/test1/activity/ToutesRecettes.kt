package com.example.test1.activity


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test1.adapter.RecipeAdapter
import com.example.test1.databinding.ActivityToutesRecettesBinding
import com.example.test1.repository.RecipeRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class ToutesRecettes : AppCompatActivity() {
    private lateinit var binding: ActivityToutesRecettesBinding
    private val apiKey = "78e80a67aed940ec8decc16468b5b38d" // Remplace par ta clé API Spoonacular
    private val repository = RecipeRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToutesRecettesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        setupToolbar()

        fetchRecipes()

        searchButton()
    }

    private fun setupToolbar() {
        // Set up the toolbar with a back button
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Recipes"

        // set a search icon and when its clicked get redirected to another activity

        binding.toolbar.overflowIcon
        // Handle the back button click
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun searchButton(){
        // if search button is clicked go to SearchByIngredientsActivity activity
        binding.searchButton.setOnClickListener{
            val intent = Intent(this, SearchByIngredientsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchRecipes() {
        lifecycleScope.launch {
            try {
                // Show the spinner before fetching data
                binding.progressBar.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE

                val recipes = repository.fetchRecipes(apiKey, 8)

                // Hide the spinner after fetching data
                binding.progressBar.visibility = View.GONE

                binding.recyclerView.visibility = View.VISIBLE
                binding.recyclerView.adapter = RecipeAdapter(recipes) { recipeId ->
                    navigateToRecipeDetails(recipeId)
                }
            } catch (e: Exception) {
                binding.progressBar.visibility = View.GONE

                // Show Google Material Snackbar on error
                Snackbar.make(binding.root, "Error fetching recipes. Try again!", Snackbar.LENGTH_LONG)
                    .setAction("Retry") {
                        fetchRecipes()
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