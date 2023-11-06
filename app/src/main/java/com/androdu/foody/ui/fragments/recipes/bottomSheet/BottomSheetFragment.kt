package com.androdu.foody.ui.fragments.recipes.bottomSheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.androdu.foody.databinding.RecipesBottomSheetBinding
import com.androdu.foody.ui.fragments.recipes.RecipesFragmentDirections
import com.androdu.foody.utils.Constants.DEFAULT_DIET_TYPE
import com.androdu.foody.utils.Constants.DEFAULT_MEAL_TYPE
import com.androdu.foody.viewModels.RecipesQueriesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.*

class BottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: RecipesBottomSheetBinding? = null
    private val mBinding get() = _binding!!

    private lateinit var mRecipesQueriesViewModel: RecipesQueriesViewModel
    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRecipesQueriesViewModel =
            ViewModelProvider(requireActivity())[RecipesQueriesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = RecipesBottomSheetBinding.inflate(inflater, container, false)

        mRecipesQueriesViewModel.getMealAndDietType.asLiveData()
            .observe(viewLifecycleOwner) { value ->
                mealTypeChip = value.selectedMealType
                dietTypeChip = value.selectedDietType
                updateChip(value.selectedMealTypeId, mBinding.chgMealTypeGroup)
                updateChip(value.selectedDietTypeId, mBinding.chgDietTypeGroup)
            }

        mBinding.chgMealTypeGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val chip = group.findViewById<Chip>(checkedIds[0])
            val selectedMealType = chip.text.toString().lowercase(Locale.ROOT)
            mealTypeChip = selectedMealType
            mealTypeChipId = checkedIds[0]

            Log.d("bottomSheet", "meal = $mealTypeChip : $mealTypeChipId")
        }

        mBinding.chgDietTypeGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val chip = group.findViewById<Chip>(checkedIds[0])
            val selectedDietType = chip.text.toString().lowercase(Locale.ROOT)
            dietTypeChip = selectedDietType
            dietTypeChipId = checkedIds[0]

            Log.d("bottomSheet", "diet = $dietTypeChip : $dietTypeChipId")
        }

        mBinding.btnApply.setOnClickListener {
            mRecipesQueriesViewModel.saveMealAndDietType(
                mealTypeChip,
                mealTypeChipId,
                dietTypeChip,
                dietTypeChipId
            )
            val action = BottomSheetFragmentDirections.actionBottomSheetFragmentToRecipesFragment(true)
            findNavController().navigate(action)
        }


        return mBinding.root
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            try {
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            } catch (e: Exception) {
                Log.d("bottomSheet", "updateChip: " + e.message.toString())
            }
        }
    }
}