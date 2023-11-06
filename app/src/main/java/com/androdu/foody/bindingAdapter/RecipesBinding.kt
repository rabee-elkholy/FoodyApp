package com.androdu.foody.bindingAdapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.androdu.foody.R
import com.androdu.foody.models.Recipe
import com.androdu.foody.ui.fragments.recipes.RecipesFragmentDirections
import com.androdu.foody.utils.Constants.BASE_IMAGES_URL
import com.androdu.foody.utils.Constants.BASE_IMAGE_URL
import org.jsoup.Jsoup

object RecipesBinding {

    @BindingAdapter("onRecipeClickListener")
    @JvmStatic
    fun onRecipeClickListener(recipeRowLayout: ConstraintLayout, recipe: Recipe) {
        recipeRowLayout.setOnClickListener {
            try {
                val action =
                    RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(recipe)
                recipeRowLayout.findNavController().navigate(action)
            } catch (e: Exception) {
                Log.d("onRecipeClickListener", e.toString())
            }
        }
    }

    @BindingAdapter("loadImageFromUrl")
    @JvmStatic
    fun loadImageFromUrl(imageView: ImageView, url: String) {
        if (url.contains(BASE_IMAGES_URL))
            imageView.load(url) {
                crossfade(600)
                error(R.drawable.ic_no_connection)
            }
        else
            imageView.load(BASE_IMAGE_URL + url) {
                crossfade(600)
                error(R.drawable.ic_no_connection)
            }
    }

    @BindingAdapter("setNumOfLikes")
    @JvmStatic
    fun setNumOfLikes(textView: TextView, likes: Int) {
        textView.text = likes.toString()
    }

    @BindingAdapter("setAmount")
    @JvmStatic
    fun setAmount(textView: TextView, likes: Double) {
        textView.text = likes.toString()
    }

    @BindingAdapter("setNumOfMinutes")
    @JvmStatic
    fun setNumOfMinutes(textView: TextView, minutes: Int) {
        textView.text = minutes.toString().plus("m")
    }

    @BindingAdapter("applyColor")
    @JvmStatic
    fun applyColor(view: View, isChecked: Boolean) {
        if (isChecked) {
            when (view) {
                is TextView -> {
                    view.setTextColor(
                        ContextCompat.getColor(
                            view.context,
                            R.color.green
                        )
                    )
                }
                is ImageView -> {
                    view.setColorFilter(
                        ContextCompat.getColor(
                            view.context,
                            R.color.green
                        )
                    )
                }
            }
        }
    }

    @BindingAdapter("parseHtml")
    @JvmStatic
    fun parseHtml(textView: TextView, description: String?) {
        if (description != null) {
            val desc = Jsoup.parse(description).text()
            textView.text = desc
        }
    }
}