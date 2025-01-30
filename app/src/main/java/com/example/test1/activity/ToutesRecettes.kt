package com.example.test1.activity


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test1.adapter.RecipeAdapter
import com.example.test1.databinding.ActivityToutesRecettesBinding
import com.example.test1.repository.RecipeRepository
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

        fetchRecipes()
    }

    private fun fetchRecipes() {
        lifecycleScope.launch {
            val recipes = repository.fetchRecipes(apiKey, 8)
            binding.recyclerView.adapter = RecipeAdapter(recipes) { recipeId ->
                navigateToRecipeDetails(recipeId)
            }
        }
    }

    private fun navigateToRecipeDetails(recipeId: Int) {
        val intent = Intent(this, RecipeDetailActivity::class.java)
        intent.putExtra("RECIPE_ID", recipeId)
        startActivity(intent)
    }
}

