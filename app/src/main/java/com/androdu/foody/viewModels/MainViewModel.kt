package com.androdu.foody.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.androdu.foody.R
import com.androdu.foody.data.Repository
import com.androdu.foody.data.room.entities.FavoritesEntity
import com.androdu.foody.data.room.entities.RecipesEntity
import com.androdu.foody.models.ApiResponseError
import com.androdu.foody.models.FoodRecipes
import com.androdu.foody.utils.ApiResult
import com.androdu.foody.utils.HelperMethods.hasInternetConnection
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    private val application: Application
) : AndroidViewModel(application) {

    /** RETROFIT */
    val recipesApiResponse: MutableLiveData<ApiResult<FoodRecipes>> = MutableLiveData()
    val searchRecipesApiResponse: MutableLiveData<ApiResult<FoodRecipes>> = MutableLiveData()

    fun getRecipesFromApi(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    fun searchRecipesFromApi(searchQuery: Map<String, String>) = viewModelScope.launch {
        searchRecipesSafeCall(searchQuery)
    }


    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        if (hasInternetConnection(application)) {
            try {
                recipesApiResponse.value = ApiResult.Loading()
                val response = repository.apiSource.getRecipes(queries)
                recipesApiResponse.value = handleRecipesResponse(response)

                val recipes = recipesApiResponse.value!!.data
                if (recipes != null) {
                    offlineCacheRecipes(recipes)
                }
            } catch (e: Exception) {
                Log.d("recipesFragment", "getRecipesSafeCall: " + e.message)
                recipesApiResponse.value = ApiResult.Error(message = e.message)
            }

        } else {
            recipesApiResponse.value = ApiResult.Error(
                message = application.getString(R.string.no_connection)
            )
        }
    }

    private suspend fun searchRecipesSafeCall(searchQuery: Map<String, String>) {
        if (hasInternetConnection(application)) {
            try {
                searchRecipesApiResponse.value = ApiResult.Loading()
                val response = repository.apiSource.searchRecipes(searchQuery)
                searchRecipesApiResponse.value = handleRecipesResponse(response)
            } catch (e: Exception) {
                Log.d("recipesFragment", "getRecipesSafeCall: " + e.message)
                searchRecipesApiResponse.value = ApiResult.Error(message = e.message)
            }

        } else {
            searchRecipesApiResponse.value = ApiResult.Error(
                message = application.getString(R.string.no_connection)
            )
        }
    }

    private fun handleRecipesResponse(response: Response<FoodRecipes>): ApiResult<FoodRecipes> {
        when {
            response.message().toString().contains("Timeout") -> {
                return ApiResult.Error(message = application.getString(R.string.connection_timeout))
            }
            response.code() == 402 -> {
                return ApiResult.Error(message = application.getString(R.string.api_limited))
            }
            !response.isSuccessful -> {
                val error = handleResponseError(response)
                Log.d(
                    "recipesFragment",
                    "handleRecipesResponse: errorBody -> " + error.message
                )
                return ApiResult.Error(message = error.message)
            }
            response.body()!!.recipes.isEmpty() -> {
                return ApiResult.Error(message = application.getString(R.string.not_found))
            }
            response.isSuccessful -> {
                val foodRecipes = response.body()
                return ApiResult.Success(data = foodRecipes!!)
            }
            else -> {
                Log.d(
                    "recipesFragment",
                    "handleRecipesResponse: " + response.errorBody().toString()
                )
                return ApiResult.Error(message = application.getString(R.string.unknown_error))
            }
        }
    }

    private fun handleResponseError(response: Response<FoodRecipes>): ApiResponseError {
        val gson = Gson()
        return gson.fromJson(response.errorBody()!!.string(), ApiResponseError::class.java)
    }


    /** ROOM DATABASE */
    val getRecipesFromLocal: LiveData<List<RecipesEntity>> =
        repository.localSource.getRecipes().asLiveData()
    val getFavRecipesFromLocal: LiveData<List<FavoritesEntity>> =
        repository.localSource.getFavRecipes().asLiveData()

    private suspend fun insertRecipes(recipesEntity: RecipesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.localSource.insertRecipes(recipesEntity)
        }

    private suspend fun offlineCacheRecipes(recipes: FoodRecipes) {
        val recipesEntity = RecipesEntity(recipes)
        insertRecipes(recipesEntity)
    }

    fun insertFavRecipe(favoritesEntity: FavoritesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.localSource.insertFavRecipes(favoritesEntity)
        }
    }

    fun deleteFavRecipe(favoritesEntity: FavoritesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.localSource.deleteFavRecipe(favoritesEntity)
        }
    }

    private suspend fun deleteAllFavRecipe() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.localSource.deleteAllFavRecipes()
        }
    }
}