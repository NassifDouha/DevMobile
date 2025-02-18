package com.example.test1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test1.data.Recipe
import com.example.test1.databinding.ItemRecipeCardBinding
import com.squareup.picasso.Picasso

class RecipeAdapter(
    private val recipes: List<Recipe>,
    private val onRecipeClick: (Int) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {
    class RecipeViewHolder(val binding: ItemRecipeCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemRecipeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.binding.recipeName.text = recipe.title
        holder.binding.prepTime.text = "Ready in ${recipe.readyInMinutes} mins"

        if (recipe.readyInMinutes != 0){
            holder.binding.prepTime.text = "Ready in ${recipe.readyInMinutes} mins"
        } else {
            holder.binding.prepTime.visibility = View.GONE
        }

        // Load image using Picasso
        Picasso.get().load(recipe.image).into(holder.binding.recipeImage)

        // Set click listener
        holder.binding.root.setOnClickListener {
            onRecipeClick(recipe.id)
        }
    }

    override fun getItemCount(): Int = recipes.size
}