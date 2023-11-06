package com.androdu.foody.ui.fragments.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.androdu.foody.adapters.FavRecipesAdapter
import com.androdu.foody.databinding.FragmentFavRecipesBinding
import com.androdu.foody.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavRecipesFragment : Fragment() {

    private lateinit var mViewModel: MainViewModel
    private var _binding: FragmentFavRecipesBinding? = null
    private val mBinding get() = _binding!!
    private val mAdapter by lazy { FavRecipesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavRecipesBinding.inflate(inflater, container, false)
        mBinding.lifecycleOwner = this

        setUpRecyclerView()
        readFromDatabase()

        return mBinding.root
    }

    private fun readFromDatabase() {
        mViewModel.getFavRecipesFromLocal.observe(viewLifecycleOwner) { favRecipes ->
            mAdapter.setData(favRecipes)

        }
    }

    private fun setUpRecyclerView() {
        mBinding.rvFavRecipes.adapter = mAdapter
        mBinding.rvFavRecipes.layoutManager = LinearLayoutManager(requireContext())
    }
}