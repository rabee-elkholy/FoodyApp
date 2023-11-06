package com.androdu.foody.data.room

import androidx.room.TypeConverter
import com.androdu.foody.models.FoodRecipes
import com.androdu.foody.models.Recipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipesTypeConverter {
    var gson = Gson()

    @TypeConverter
    fun foodRecipesToString(foodRecipes: FoodRecipes): String {
        return gson.toJson(foodRecipes)
    }

    @TypeConverter
    fun stringToFoodRecipes(strData: String): FoodRecipes {
        val dataType = object : TypeToken<FoodRecipes>() {}.type
        return gson.fromJson(strData, dataType)
    }

    @TypeConverter
    fun recipeToString(recipe: Recipe): String {
        return gson.toJson(recipe)
    }

    @TypeConverter
    fun stringToRecipe(strData: String): Recipe {
        val dataType = object : TypeToken<Recipe>() {}.type
        return gson.fromJson(strData, dataType)
    }
}