package com.example.test1.iservice

import com.example.test1.data.Recipe
import com.example.test1.data.RecipeDetails
import com.example.test1.data.RecipeList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApiService {
    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("apiKey") apiKey: String,
        @Query("number") number: Int
    ): RecipeList

    @GET("recipes/{id}/information")
    suspend fun getRecipeDetails(
        @Path("id") id: Int,
        @Query("includeNutrition") includeNutrition: Boolean = false,
        @Query("apiKey") apiKey: String
    ): RecipeDetails

    @GET("recipes/findByIngredients")
    suspend fun searchRecipesByIngredients(
        @Query("apiKey") apiKey: String,
        @Query("ingredients") ingredients: String,
        @Query("number") number: Int,
        @Query("ranking") ranking: Int = 1, // Default: maximize used ingredients
        @Query("ignorePantry") ignorePantry: Boolean = true
    ): List<Recipe>
}
