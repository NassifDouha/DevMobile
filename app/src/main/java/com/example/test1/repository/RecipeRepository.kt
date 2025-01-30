package com.example.test1.repository

import com.example.test1.data.Recipe
import com.example.test1.data.RecipeDetails
import com.example.test1.iservice.RecipeApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipeRepository {
    private val api: RecipeApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(RecipeApiService::class.java)
    }

    suspend fun fetchRecipes(apiKey: String, number: Int): List<Recipe> {
        return try {
            api.getRandomRecipes(apiKey, number).recipes
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun fetchRecipeDetails(apiKey: String, id: Int): RecipeDetails? {
        return try {
            api.getRecipeDetails(id, includeNutrition = false, apiKey = apiKey)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}