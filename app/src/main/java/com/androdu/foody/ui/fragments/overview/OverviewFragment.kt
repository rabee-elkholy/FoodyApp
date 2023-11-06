package com.androdu.foody.ui.fragments.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.androdu.foody.databinding.FragmentOverviewBinding
import com.androdu.foody.models.Recipe
import com.androdu.foody.utils.Constants.RECIPE_BUNDLE_KEY

class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val mBinding get() = _binding!!

    lateinit var recipe: Recipe


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)

        val args = arguments

        recipe = args?.getParcelable(RECIPE_BUNDLE_KEY)!!

        mBinding.recipe = recipe

        return mBinding.root
    }
}