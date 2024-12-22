package com.example.test1

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

import com.example.test1.data.Recipe

interface ApiService {
    // Exemple: Récupérer une recette par ID
    @GET("recipes/{id}/information")
    suspend fun getRecipeById(@Path("id") id: Int): Recipe

    // Exemple: Récupérer une liste de recettes avec des filtres
    @GET("recipes")
    suspend fun getRecipesWithFilters(@QueryMap filters: Map<String, String>): List<Recipe>

    // Exemple: Endpoint générique avec Query
    @GET("{endpoint}")
    suspend fun getDynamicEndpoint(
        @Path("endpoint") endpoint: String,
        @QueryMap queryParameters: Map<String, String>
    ): Any // Remplacez `Any` par le type attendu si connu
}