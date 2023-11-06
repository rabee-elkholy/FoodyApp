package com.androdu.foody.models


import com.google.gson.annotations.SerializedName

data class FoodRecipes(
    @SerializedName("results")
    val recipes: List<Recipe>
)