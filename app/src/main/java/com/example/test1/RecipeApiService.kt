package com.example.test1

interface RecipeApiService {
    @GET("recipes") // Replace with your API endpoint
    suspend fun getRecipes(): List<Recipe>
}