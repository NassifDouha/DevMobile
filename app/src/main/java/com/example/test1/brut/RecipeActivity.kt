package com.example.test1.brut

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.test1.R

import com.example.test1.data.Recipe

class RecipeActivity : AppCompatActivity() {
    private lateinit var viewModel: RecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recipe)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        viewModel = RecipeViewModel()

        // Récupérer une recette par ID
        viewModel.fetchRecipeById(20081,
            onSuccess = { recipe ->
                Log.d("API Response", "Recette reçue: ${recipe}")
                afficherRecette(recipe) // Passez la recette récupérée à votre méthode d'affichage
            },
            onError = { errorMessage ->
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        )

        // Récupérer des recettes avec des filtres
//        val filters = mapOf("vegetarian" to "true", "maxCalories" to "500")
//        viewModel.fetchRecipesWithFilters(filters,
//            onSuccess = {
//                recipes -> afficherListeRecettes(recipes)
//            },
//            onError = {
//                errorMessage -> Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
//            }
//        )
    }

    private fun afficherRecette(recipe: Recipe) {
//        findViewById<TextView>(R.id.recipe_summary).text = recipe.summary
//        findViewById<TextView>(R.id.recipe_ready_in_minutes).text =
//            "Temps de préparation : ${recipe.readyInMinutes} minutes"
//        findViewById<TextView>(R.id.recipe_servings).text =
//            "Portions : ${recipe.servings}"

        // Trouver le TextView principal
        val recipeTextView: TextView = findViewById(R.id.recipe_text_view)

        // Construire une chaîne de texte avec les informations de la recette
        val recipeDetails = """
            Titre : ${recipe.title}
            Temps de préparation : ${recipe.readyInMinutes} minutes
            Portions : ${recipe.servings}
            URL image : ${recipe.image}
            Résumé : ${recipe.summary}
        """.trimIndent()

        // Afficher les détails dans le TextView
        recipeTextView.text = recipeDetails
    }

//    private fun afficherListeRecettes(recipes: List<Recipe>) {
//        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_recipes)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = RecipeAdapter(recipes)
//    }
}