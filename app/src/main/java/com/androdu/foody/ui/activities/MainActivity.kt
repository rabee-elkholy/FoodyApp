package com.androdu.foody.ui.activities

import android.animation.Animator
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.androdu.foody.R
import com.androdu.foody.databinding.ActivityMainBinding
import com.androdu.foody.utils.Constants.NAV_ANIMATION_MS
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navView: BottomNavigationView = mBinding.bottomNavigationView
        mNavController = findNavController(R.id.navHostFragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.recipesFragment,
                R.id.favRecipesFragment,
                R.id.foodJokeFragment
            )
        )

        setupActionBarWithNavController(mNavController, appBarConfiguration)
        navView.setupWithNavController(mNavController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return mNavController.navigateUp() || super.onSupportNavigateUp()
    }

    internal fun hideBottomNav() {
        mBinding.bottomNavigationView.clearAnimation()
        val navHeight = mBinding.bottomNavigationView.height.toFloat()
        mBinding.bottomNavigationView.animate().translationY(navHeight)
            .setDuration(NAV_ANIMATION_MS)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                }

                override fun onAnimationEnd(animation: Animator) {
                    mBinding.bottomNavigationView.visibility = GONE
                }

                override fun onAnimationCancel(animation: Animator) {
                }

                override fun onAnimationRepeat(animation: Animator) {
                }

            })
    }

    internal fun showBottomNav() {
        mBinding.bottomNavigationView.clearAnimation()
        mBinding.bottomNavigationView.animate().translationY(0f).setDuration(NAV_ANIMATION_MS)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    mBinding.bottomNavigationView.visibility = VISIBLE
                }

                override fun onAnimationEnd(animation: Animator) {

                }

                override fun onAnimationCancel(animation: Animator) {
                }

                override fun onAnimationRepeat(animation: Animator) {
                }

            })
    }
}