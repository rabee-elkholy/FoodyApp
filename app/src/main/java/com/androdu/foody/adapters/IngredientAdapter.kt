package com.androdu.foody.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.androdu.foody.databinding.IngredientsRowLayoutBinding
import com.androdu.foody.models.ExtendedIngredient
import com.androdu.foody.utils.RecipesDiffUtil

class IngredientAdapter() : RecyclerView.Adapter<IngredientAdapter.MyViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()

    class MyViewHolder(private val binding: IngredientsRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredient: ExtendedIngredient) {
            binding.ingredient = ingredient
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = IngredientsRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = ingredientsList[position]
        holder.bind(currentRecipe)
    }

    fun setData(newData: List<ExtendedIngredient>) {
        val recipesDiffUtil = RecipesDiffUtil(ingredientsList, newData)
        val recipesDiffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        ingredientsList = newData
        recipesDiffUtilResult.dispatchUpdatesTo(this)
    }

    fun clearData() {
        ingredientsList = emptyList()
    }

}