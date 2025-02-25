package com.example.test1.data

data class RecipeDetails(
    val id: Int,
    val title: String,
    val image: String,
    val readyInMinutes: Int,
    val healthScore: Double,
    val servings: Int,
    val extendedIngredients: List<Ingredient>,
    val sourceUrl: String
)
