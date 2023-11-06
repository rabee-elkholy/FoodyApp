package com.androdu.foody.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.androdu.foody.models.FoodRecipes
import com.androdu.foody.utils.Constants.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipes: FoodRecipes
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}