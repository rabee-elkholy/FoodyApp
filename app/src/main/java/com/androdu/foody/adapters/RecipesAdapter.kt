package com.androdu.foody.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.androdu.foody.databinding.RecipesRowLayoutBinding
import com.androdu.foody.models.FoodRecipes
import com.androdu.foody.models.Recipe
import com.androdu.foody.utils.RecipesDiffUtil

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    private var recipesList = emptyList<Recipe>()

    class MyViewHolder(private val binding: RecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) {
            binding.recipe = recipe
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return recipesList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = recipesList[position]
        holder.bind(currentRecipe)
    }

    fun setData(newData: FoodRecipes){
        val recipesDiffUtil = RecipesDiffUtil(recipesList, newData.recipes)
        val recipesDiffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipesList = newData.recipes
        recipesDiffUtilResult.dispatchUpdatesTo(this)
    }

    fun clearData(){
        recipesList = emptyList()
    }
}