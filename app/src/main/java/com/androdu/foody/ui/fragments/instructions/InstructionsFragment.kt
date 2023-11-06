package com.androdu.foody.ui.fragments.instructions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.androdu.foody.databinding.FragmentInstructionsBinding
import com.androdu.foody.models.Recipe
import com.androdu.foody.utils.Constants

class InstructionsFragment : Fragment() {
    private var _binding: FragmentInstructionsBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInstructionsBinding.inflate(inflater, container, false)

        val args = arguments
        val recipe: Recipe? = args?.getParcelable(Constants.RECIPE_BUNDLE_KEY)!!

        mBinding.wvInstructions.webViewClient = object : WebViewClient() {}
        val websiteUrl = recipe!!.sourceUrl
        mBinding.wvInstructions.loadUrl(websiteUrl)

        return mBinding.root
    }
}