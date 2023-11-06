package com.androdu.foody.bindingAdapter

import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.androdu.foody.data.room.entities.RecipesEntity
import com.androdu.foody.models.FoodRecipes
import com.androdu.foody.utils.ApiResult

object RecipesFragmentBinding {

    @BindingAdapter("ivApiResult", "ivDatabase", requireAll = true)
    @JvmStatic
    fun ivErrorVisibility(
        imageView: ImageView,
        apiResult: ApiResult<FoodRecipes>?,
        database: List<RecipesEntity>?
    ) {
        if (apiResult is ApiResult.Error && database.isNullOrEmpty()) {
            imageView.visibility = VISIBLE
        } else if (apiResult is ApiResult.Loading) {
            imageView.visibility = GONE
        } else if (apiResult is ApiResult.Success) {
            imageView.visibility = GONE
        }
    }

    @BindingAdapter("tvApiResult", "tvDatabase", requireAll = true)
    @JvmStatic
    fun tvErrorVisibility(
        textView: TextView,
        apiResult: ApiResult<FoodRecipes>?,
        database: List<RecipesEntity>?
    ) {
        if (apiResult is ApiResult.Error && database.isNullOrEmpty()) {
            textView.visibility = VISIBLE
            textView.text = apiResult.message
        } else if (apiResult is ApiResult.Loading) {
            textView.visibility = GONE
        } else if (apiResult is ApiResult.Success) {
            textView.visibility = GONE
        }
    }

}
