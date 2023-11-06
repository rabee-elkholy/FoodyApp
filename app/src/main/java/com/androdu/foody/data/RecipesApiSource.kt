package com.androdu.foody.data

import com.androdu.foody.data.api.RecipesApi
import javax.inject.Inject

class RecipesApiSource @Inject constructor(
    private val recipesApi: RecipesApi
) {

    suspend fun getRecipes(queries: Map<String, String>) = recipesApi.getRecipes(queries)

    suspend fun searchRecipes(searchQuery: Map<String, String>) =
        recipesApi.searchRecipes(searchQuery)
}