package com.androdu.foody.data

import com.androdu.foody.data.room.RecipesDao
import com.androdu.foody.data.room.entities.FavoritesEntity
import com.androdu.foody.data.room.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipesLocalSource @Inject constructor(
    private val recipesDao: RecipesDao
) {
    suspend fun insertRecipes(recipesEntity: RecipesEntity) =
        recipesDao.insertRecipes(recipesEntity)

    fun getRecipes(): Flow<List<RecipesEntity>> = recipesDao.getRecipes()

    suspend fun insertFavRecipes(favoritesEntity: FavoritesEntity) =
        recipesDao.insertFavRecipe(favoritesEntity)

    fun getFavRecipes(): Flow<List<FavoritesEntity>> = recipesDao.getFavRecipes()

    suspend fun deleteFavRecipe(favoritesEntity: FavoritesEntity) =
        recipesDao.deleteFavRecipe(favoritesEntity)

    suspend fun deleteAllFavRecipes() = recipesDao.deleteAllFavRecipe()
}