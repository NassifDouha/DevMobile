package com.example.test1

import Recipe
import RecipeApiService
import RecipeResponse
import android.os.Bundle
import android.widget.Toast
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ToutesRecettes : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toutes_recettes)

        val apiKey = "78e80a67aed940ec8decc16468b5b38d" // Remplace par ta clé API Spoonacular

        // Configurer Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(RecipeApiService::class.java)
        val call = service.getRecipes(apiKey)

        // Appel API pour récupérer les recettes
        call.enqueue(object : Callback<RecipeResponse> {
            override fun onResponse(call: Call<RecipeResponse>, response: Response<RecipeResponse>) {
                if (response.isSuccessful) {
                    val recipes = response.body()?.results
                    displayRecipes(recipes)
                } else {
                    showError()
                }
            }

            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                showError()
            }
        })
    }

    // Afficher les recettes dans un TextView simple pour tester
    private fun displayRecipes(recipes: List<Recipe>?) {
        val textView = findViewById<TextView>(R.id.recipeTextView)
        val recipeTitles = recipes?.joinToString("\n") { it.title }
        textView.text = recipeTitles
    }

    // Afficher un message d'erreur en cas de problème
    private fun showError() {
        Toast.makeText(this, "Erreur lors de la récupération des recettes", Toast.LENGTH_SHORT).show()
    }
}
