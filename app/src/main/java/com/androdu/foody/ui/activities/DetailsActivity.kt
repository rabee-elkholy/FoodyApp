package com.androdu.foody.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import com.androdu.foody.R
import com.androdu.foody.adapters.PagerAdapter
import com.androdu.foody.data.room.entities.FavoritesEntity
import com.androdu.foody.databinding.ActivityDetailsBinding
import com.androdu.foody.ui.fragments.ingredients.IngredientsFragment
import com.androdu.foody.ui.fragments.instructions.InstructionsFragment
import com.androdu.foody.ui.fragments.overview.OverviewFragment
import com.androdu.foody.utils.Constants.RECIPE_BUNDLE_KEY
import com.androdu.foody.utils.HelperMethods.showSnackBarMessage
import com.androdu.foody.viewModels.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityDetailsBinding

    private val args: DetailsActivityArgs by navArgs()

    private lateinit var mViewModel: MainViewModel

    private var isFavRecipe = false
    private var favRecipeId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        setSupportActionBar(mBinding.toolbar)
        mBinding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add(getString(R.string.overview))
        titles.add(getString(R.string.ingredients))
        titles.add(getString(R.string.instructions))

        val recipeBundle = Bundle()
        recipeBundle.putParcelable(RECIPE_BUNDLE_KEY, args.recipeDetails)

        val adapter = PagerAdapter(
            recipeBundle,
            fragments,
            titles,
            this
        )

        mBinding.viewPager.adapter = adapter
        mBinding.viewPager.currentItem = 0

        TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager) { tab, position ->
            tab.text = adapter.getTabTitle(position)
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        val menuItem = menu?.findItem(R.id.save_to_fav_menu)
        checkSavedRecipe(menuItem!!)
        return true
    }

    private fun checkSavedRecipe(menuItem: MenuItem) {
        mViewModel.getFavRecipesFromLocal.observe(this) { favoritesEntity ->
            try {
                for (savedRecipe in favoritesEntity) {
                    if (savedRecipe.recipe.id == args.recipeDetails.id) {
                        changeMenuItemColor(menuItem, R.color.yellow)
                        confirmFavRecipe(savedRecipe.id)
                        break
                    }
                }
            } catch (e: Exception) {
                Log.d("checkSavedRecipe", e.message.toString())
            }
        }
    }

    private fun confirmFavRecipe(id: Int) {
        favRecipeId = id
        isFavRecipe = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.save_to_fav_menu -> {
                if (!isFavRecipe) {
                    saveToFav(item)
                } else {
                    deleteFromFav(item)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveToFav(item: MenuItem) {
        val favoritesEntity = FavoritesEntity(0, args.recipeDetails)
        mViewModel.insertFavRecipe(favoritesEntity)
        changeMenuItemColor(item, R.color.yellow)
        showSnackBarMessage(this, getString(R.string.recipe_saved))
    }

    private fun deleteFromFav(item: MenuItem) {
        val favoritesEntity = FavoritesEntity(favRecipeId, args.recipeDetails)
        mViewModel.deleteFavRecipe(favoritesEntity)
        changeMenuItemColor(item, R.color.white)
        showSnackBarMessage(this, getString(R.string.recipe_removed))
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon?.setTint(ContextCompat.getColor(this, color))
    }
}