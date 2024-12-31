package com.example.test1.brut

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test1.ApiClient
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import com.example.test1.data.Recipe

class RecipeViewModel : ViewModel() {

    // Exemple: Récupérer une recette par ID
    fun fetchRecipeById(id: Int, onSuccess: (Recipe) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val recipe = withContext(Dispatchers.IO) {
                    ApiClient.apiService.getRecipeById(id)
                }
                onSuccess(recipe)
            } catch (e: Exception) {
                onError("Erreur : ${e.message}")
            }
        }
    }

    // Exemple: Récupérer des recettes avec des filtres
    fun fetchRecipesWithFilters(
        filters: Map<String, String>,
        onSuccess: (List<Recipe>) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val recipes = withContext(Dispatchers.IO) {
                    ApiClient.apiService.getRecipesWithFilters(filters)
                }
                onSuccess(recipes)
            } catch (e: Exception) {
                onError("Erreur : ${e.message}")
            }
        }
    }
}