package com.example.test1

import RecipeApiService
import RecipeResponse
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test1.data.Recipe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.test1.databinding.ActivityToutesRecettesBinding
import com.example.test1.databinding.ItemRecipeCardBinding

class ToutesRecettes : AppCompatActivity() {
    private lateinit var binding: ActivityToutesRecettesBinding
    private val apiKey = "78e80a67aed940ec8decc16468b5b38d" // Remplace par ta clé API Spoonacular

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToutesRecettesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getRecipes()
    }

    private fun getRecipes() {
        // Configure Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(RecipeApiService::class.java)
        val call = service.getRecipes(apiKey)

        // API call to fetch recipes
        call.enqueue(object : Callback<RecipeResponse> {
            override fun onResponse(call: Call<RecipeResponse>, response: Response<RecipeResponse>) {
                if (response.isSuccessful) {
                    val recipes = response.body()?.results?.map {
                        Recipe(
                            title = it.title,
                            image = it.image,
                            readyInMinutes = it.readyInMinutes,
                            summary = null,
                            servings = null,
                            sourceUrl = null,
                            prepTime = null,
                            extendedIngredients = null,
                            analyzedInstructions = null,
                        )
                    }
                    displayRecipes(recipes ?: emptyList()) // Provide empty list if null
                } else {
                    showError()
                }
            }

            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                showError()
            }
        })
    }

    // Afficher les recettes dans un RecyclerView
    private fun displayRecipes(recipes: List<Recipe>) {
        binding.recipesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.recipesRecyclerView.adapter = RecipeAdapter(recipes)
    }

    // Afficher un message d'erreur en cas de problème
    private fun showError() {
        Toast.makeText(this, "Erreur lors de la récupération des recettes", Toast.LENGTH_SHORT).show()
    }
}

///////////////////////////

class RecipeAdapter(private val recipes: List<Recipe>) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemRecipeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun getItemCount() = recipes.size

    class RecipeViewHolder(private val binding: ItemRecipeCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            binding.recipeName.text = recipe.title ?: "Unknown" // Use default if title is null
            binding.prepTime.text = recipe.prepTime?.toString() ?: "N/A" // Default to "N/A" if null
            Glide.with(binding.root.context)
                .load(recipe.image)
                .into(binding.recipeImage)
        }
    }
}

