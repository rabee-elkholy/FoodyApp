package com.androdu.foody.utils

object Constants {
    const val NAV_ANIMATION_MS: Long = 200L
    const val API_KEY = "226ccc75d2fd4590b034965b66bf7bd2"
    const val BASE_URL = "https://api.spoonacular.com"
    const val BASE_IMAGE_URL = "https://spoonacular.com/cdn/ingredients_100x100/"

    const val BASE_IMAGES_URL = "https://spoonacular.com/"

    const val RECIPE_BUNDLE_KEY = "recipeBundle"


//    94160622849d4fc4b9e3e3c64c302696
//    96b2337d6dda41838c44248e55a2d9fe
//    553db35114454eac997327f3408fa7a3
//    226ccc75d2fd4590b034965b66bf7bd2
//    a524e575f194404ebf1bb818a3bf3613


    // API Query Keys
    const val QUERY_SEARCH = "query"
    const val QUERY_NUMBER = "number"
    const val QUERY_API_KEY = "apiKey"
    const val QUERY_TYPE = "type"
    const val QUERY_DIET = "diet"
    const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
    const val QUERY_FILL_INGREDIENTS = "fillIngredients"

    // ROOM Database
    const val DATABASE_NAME = "recipes_database"
    const val RECIPES_TABLE = "recipes_table"
    const val FAVORITES_TABLE = "favorites_table"

    // Bottom sheet and Preferences

    const val DATA_STORE_NAME = "foody_dataStore"
    const val DEFAULT_RECIPES_NUMBER = "50"
    const val DEFAULT_MEAL_TYPE = "main course"
    const val DEFAULT_DIET_TYPE = "gluten free"
    const val PREFERENCES_MEAL_TYPE = "mealType"
    const val PREFERENCES_MEAL_TYPE_ID = "mealTypeId"
    const val PREFERENCES_DIET_TYPE = "dietType"
    const val PREFERENCES_DIET_TYPE_ID = "dietTypeId"


}