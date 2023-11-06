package com.androdu.foody.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.androdu.foody.R
import com.androdu.foody.adapters.RecipesAdapter
import com.androdu.foody.databinding.FragmentRecipesBinding
import com.androdu.foody.utils.ApiResult
import com.androdu.foody.utils.HelperMethods.hasInternetConnection
import com.androdu.foody.utils.HelperMethods.showToastMessage
import com.androdu.foody.utils.observeOnce
import com.androdu.foody.viewModels.MainViewModel
import com.androdu.foody.viewModels.RecipesQueriesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment(), MenuProvider, SearchView.OnQueryTextListener {

    private val args by navArgs<RecipesFragmentArgs>()

    private lateinit var mViewModel: MainViewModel
    private lateinit var mRecipesQueriesViewModel: RecipesQueriesViewModel
    private var _binding: FragmentRecipesBinding? = null
    private val mBinding get() = _binding!!
    private val mAdapter by lazy { RecipesAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mRecipesQueriesViewModel =
            ViewModelProvider(requireActivity())[RecipesQueriesViewModel::class.java]

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        mBinding.lifecycleOwner = this
        mBinding.mainViewModel = mViewModel

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        setUpRecyclerView()
        readDatabase()

        mBinding.fabAddRecipe.setOnClickListener {
            if (hasInternetConnection(requireContext()))
                findNavController().navigate(R.id.action_recipesFragment_to_bottomSheetFragment)
            else
                showToastMessage(requireContext(), getString(R.string.no_connection))

        }
        return mBinding.root
    }

    private fun setUpRecyclerView() {
        mBinding.rvRecipes.adapter = mAdapter
        mBinding.rvRecipes.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun readDatabase() {
        mViewModel.getRecipesFromLocal.observeOnce(viewLifecycleOwner) { database ->
            Log.d("recipesFragment", "readDatabase: observeOnce")

            if (database.isNotEmpty() && !args.backFromBottomSheet) {
                Log.d("recipesFragment", "readDatabase: ")
                mAdapter.setData(database[0].foodRecipes)
                hideShimmerEffect()
            } else {
                requestApiData()
            }
        }
    }

    private fun requestApiData() {
        Log.d("recipesFragment", "requestApiData: ")
        mViewModel.getRecipesFromApi(mRecipesQueriesViewModel.applyQueries())
        mViewModel.recipesApiResponse.observe(viewLifecycleOwner) { response ->
            Log.d("recipesFragment", "requestApiData: observe")
            when (response) {
                is ApiResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let {
                        mAdapter.setData(it)
                    }
                }
                is ApiResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    showToastMessage(requireContext(), response.message.toString())
                }
                is ApiResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun searchApiData(searchQuery: String) {
        showShimmerEffect()
        mViewModel.searchRecipesFromApi(mRecipesQueriesViewModel.applySearchQueries(searchQuery))
        mViewModel.searchRecipesApiResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let {
                        mAdapter.setData(it)
                    }
                }
                is ApiResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    showToastMessage(requireContext(), response.message.toString())
                }
                is ApiResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mViewModel.getRecipesFromLocal.observeOnce(viewLifecycleOwner) { database ->
                Log.d("recipesFragment", "loadFromCache: observe")
                if (database.isNotEmpty()) {
                    Log.d("recipesFragment", "loadFromCache: ")
                    mAdapter.setData(database[0].foodRecipes)
                }
            }
        }
    }

    private fun showShimmerEffect() {
        mBinding.rvRecipes.showShimmer()
    }

    private fun hideShimmerEffect() {
        mBinding.rvRecipes.hideShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.recipes_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.setOnQueryTextListener(this)
        searchView?.setOnCloseListener {
            loadDataFromCache()
            false
        }

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            searchApiData(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}