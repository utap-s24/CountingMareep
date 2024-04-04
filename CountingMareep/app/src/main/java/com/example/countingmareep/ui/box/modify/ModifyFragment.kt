package com.example.countingmareep.ui.box.modify

import MarginItemDecoration
import ModifyAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.countingmareep.MainActivity
import com.example.countingmareep.ViewModel
import com.example.countingmareep.databinding.FragmentModifyPokemonBinding
import com.example.countingmareep.ui.box.PokemonData

class ModifyFragment : Fragment() {
    companion object {
        const val TAG = "BoxFragment"
    }

    private var _binding: FragmentModifyPokemonBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: ViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentModifyPokemonBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val mainActivity = activity as MainActivity

        // Pokemon Selection
        val recyclerView = binding.listRV
        recyclerView.layoutManager = GridLayoutManager(mainActivity, 5)
        recyclerView.addItemDecoration(MarginItemDecoration(10, 5, GridLayoutManager.VERTICAL))
        binding.listRV.adapter = ModifyAdapter(PokemonData.pokemon, mainActivity)

        // Sub Skills
        val subSkillAdapter = ArrayAdapter(
            mainActivity,
            androidx.appcompat.R.layout.select_dialog_item_material,
            SubSkill.subSkillNames
        )
        val subSkillViews = listOf(
            binding.subSkill1,
            binding.subSkill2,
            binding.subSkill3,
            binding.subSkill4,
            binding.subSkill5
        )
        for(view in subSkillViews) {
            view.threshold = 0
            view.setAdapter(subSkillAdapter)
            view.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    view.showDropDown()
                }
            }
        }
        // Ingredients
        val ingredientImageCodes = listOf(
            SpinnerItem("beansausage"),
            SpinnerItem("fancyapple"),
            SpinnerItem("fancyegg"),
            SpinnerItem("fieryherb"),
            SpinnerItem("greengrasscorn"),
            SpinnerItem("greengrasssoybeans"),
            SpinnerItem("honey"),
            SpinnerItem("largeleek"),
            SpinnerItem("moomoomilk"),
            SpinnerItem("pureoil"),
            SpinnerItem("slowpoketail"),
            SpinnerItem("snoozytomato"),
            SpinnerItem("soothingcacao"),
            SpinnerItem("tastymushroom"),
            SpinnerItem("warmingginger")
        )
        val ingredientViews = listOf(binding.ingredient1, binding.ingredient2, binding.ingredient3)
        val ingredientAdapter = IngredientSpinnerAdapter(mainActivity, ingredientImageCodes)
        for(view in ingredientViews) {
            view.adapter = ingredientAdapter
        }
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}