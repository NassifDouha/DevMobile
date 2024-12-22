import retrofit2.Call
import com.example.test1.data.Recipe
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApiService {
    @GET("recipes/complexSearch")
    fun getRecipes(
        @Query("apiKey") apiKey: String, // Ta clé API
        @Query("number") number: Int = 8 // Nombre de recettes à récupérer
    ): Call<RecipeResponse>

    suspend fun getRecipe(): Recipe

}
