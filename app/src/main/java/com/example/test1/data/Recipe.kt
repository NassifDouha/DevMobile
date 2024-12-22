package com.example.test1.data

data class Recipe(
    val title: String,
    val image: String,
    val summary: String,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceUrl: String,
    val extendedIngredients: List<Ingredient>,
    val analyzedInstructions: List<Instruction>
)
