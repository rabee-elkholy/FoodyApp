package com.androdu.foody.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.androdu.foody.data.DataStoreRepository
import com.androdu.foody.utils.Constants.API_KEY
import com.androdu.foody.utils.Constants.DEFAULT_DIET_TYPE
import com.androdu.foody.utils.Constants.DEFAULT_MEAL_TYPE
import com.androdu.foody.utils.Constants.DEFAULT_RECIPES_NUMBER
import com.androdu.foody.utils.Constants.QUERY_ADD_RECIPE_INFORMATION
import com.androdu.foody.utils.Constants.QUERY_API_KEY
import com.androdu.foody.utils.Constants.QUERY_DIET
import com.androdu.foody.utils.Constants.QUERY_FILL_INGREDIENTS
import com.androdu.foody.utils.Constants.QUERY_NUMBER
import com.androdu.foody.utils.Constants.QUERY_SEARCH
import com.androdu.foody.utils.Constants.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesQueriesViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    val getMealAndDietType = dataStoreRepository.getMealAndDietTypes

    fun saveMealAndDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypesId: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypesId)
        }
    }

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
            getMealAndDietType.collect { value ->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }

        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries

    }

    fun applySearchQueries(searchQuery: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[QUERY_SEARCH] = searchQuery
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries

    }
}