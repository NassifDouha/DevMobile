package com.example.test1.data

data class Recipe(
    val title: String?, // Can be null if not available
    val image: String?, // Can be null if not available
    val summary: String?, // Nullable
    val readyInMinutes: Int?, // Nullable
    val servings: Int?, // Nullable
    val sourceUrl: String?, // Nullable
    val prepTime: Int?, // Nullable
    val extendedIngredients: List<Ingredient>?, // Nullable
    val analyzedInstructions: List<Instruction>? // Nullable
)
