package com.androdu.foody.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.androdu.foody.data.room.entities.FavoritesEntity
import com.androdu.foody.databinding.FavRecipesRowLayoutBinding
import com.androdu.foody.utils.RecipesDiffUtil

class FavRecipesAdapter : RecyclerView.Adapter<FavRecipesAdapter.MyViewHolder>() {

    private var recipesList = emptyList<FavoritesEntity>()

    class MyViewHolder(private val binding: FavRecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favEntity: FavoritesEntity) {
            binding.recipe = favEntity.recipe
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavRecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
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

    fun setData(newData: List<FavoritesEntity>) {
        val recipesDiffUtil = RecipesDiffUtil(recipesList, newData)
        val recipesDiffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipesList = newData
        recipesDiffUtilResult.dispatchUpdatesTo(this)
    }

    fun clearData() {
        recipesList = emptyList()
    }
}