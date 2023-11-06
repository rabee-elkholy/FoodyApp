package com.androdu.foody.ui.fragments.ingredients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.androdu.foody.adapters.IngredientAdapter
import com.androdu.foody.databinding.FragmentIngredientsBinding
import com.androdu.foody.models.Recipe
import com.androdu.foody.utils.Constants.RECIPE_BUNDLE_KEY

class IngredientsFragment : Fragment() {

    private var _binding: FragmentIngredientsBinding? = null
    private val mBinding get() = _binding!!

    private val mAdapter: IngredientAdapter by lazy { IngredientAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentIngredientsBinding.inflate(inflater, container, false)

        val args = arguments

        val recipe: Recipe? = args?.getParcelable(RECIPE_BUNDLE_KEY)!!
//
//        mBinding.recipe = recipe

        recipe?.extendedIngredients?.let {
            mAdapter.setData(it)
        }
        setUpRecyclerView()
        return mBinding.root
    }

    private fun setUpRecyclerView() {
        mBinding.rvIngredient.adapter = mAdapter
        mBinding.rvIngredient.layoutManager = LinearLayoutManager(requireContext())
    }
}