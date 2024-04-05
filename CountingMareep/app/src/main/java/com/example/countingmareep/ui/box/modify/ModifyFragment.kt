package com.example.countingmareep.ui.box.modify

import MarginItemDecoration
import ModifyAdapter
import PokemonDataModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.countingmareep.MainActivity
import com.example.countingmareep.R
import com.example.countingmareep.ViewModel
import com.example.countingmareep.databinding.FragmentModifyPokemonBinding
import com.example.countingmareep.ui.box.Ingredient
import com.example.countingmareep.ui.box.Ingredients
import com.example.countingmareep.ui.box.Nature
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
        val pokemonAdapter = pokemonBindings(mainActivity)
        natureBindings(mainActivity)
        subSkillBindings(mainActivity)
        ingredientBindings(mainActivity)

        binding.saveButton.setOnClickListener {
            val levelStr = binding.levelInput.text.toString()
            val pokedexNumber = PokemonData.pokemon[pokemonAdapter.selected].dexNumber
            val RPStr = binding.rpInput.text.toString()
            val skillLevelStr = binding.skillLevelInput.text.toString()
            val nature = Nature.natureFromIndex(binding.natureInput.selectedItemPosition)
            val subSkills = getSubSkills()
            val ingredients = getIngredients()
            var nickname = binding.nicknameInput.text.toString()
            if (nickname.isEmpty()) {
                nickname = PokemonData.getByDex(pokedexNumber).name
            }

            if(levelStr.isEmpty() || RPStr.isEmpty() || skillLevelStr.isEmpty()) {
                Toast.makeText(mainActivity, "Unfilled Value", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val level = levelStr.toInt()
            val RP = RPStr.toInt()
            val skillLevel = skillLevelStr.toInt()

            if (level <= 0 || level > 100 || RP <= 0 || skillLevel <= 0) {
                Toast.makeText(mainActivity, "Some Value is Out of Bounds", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newMon = PokemonDataModel(
                nickname,
                level,
                pokedexNumber,
                subSkills,
                ingredients,
                nature,
                RP,
                skillLevel
            )

            viewModel.addPokemon(newMon)
            mainActivity.navController.popBackStack()
        }
        return root
    }

    private fun getIngredientCodes(): List<SpinnerItem> {
        return listOf(
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
    }

    private fun pokemonBindings(mainActivity: MainActivity): ModifyAdapter {
        val recyclerView = binding.listRV
        recyclerView.layoutManager = GridLayoutManager(mainActivity, 5)
        recyclerView.addItemDecoration(MarginItemDecoration(10, 5, GridLayoutManager.VERTICAL))
        val adapter = ModifyAdapter(PokemonData.pokemon, mainActivity)
        binding.listRV.adapter = adapter
        return adapter
    }

    private fun natureBindings(mainActivity: MainActivity) {
        val natureAdapter = ArrayAdapter(
            mainActivity,
            androidx.appcompat.R.layout.select_dialog_item_material,
            Nature.natureNames
        )
        binding.natureInput.adapter = natureAdapter
    }

    private fun subSkillBindings(mainActivity: MainActivity) {
        val subSkillAdapter = ArrayAdapter(
            mainActivity,
            R.layout.subskill_spinner,
            SubSkill.subSkillNames
        )
        subSkillAdapter.setDropDownViewResource(R.layout.subskill_spinner_dialog)
        val subSkillViews = listOf(
            binding.subSkill1,
            binding.subSkill2,
            binding.subSkill3,
            binding.subSkill4,
            binding.subSkill5
        )
        for (view in subSkillViews) {
            view.adapter = subSkillAdapter
            view.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {
                    val skill = SubSkill.SKILLS[position]
                    if (skill.rarity == Rarity.VERY_RARE) {
                        view.setBackgroundResource(R.drawable.subskills)
                    } else if (skill.rarity == Rarity.RARE) {
                        view.setBackgroundResource(R.drawable.subskillsrare)
                    } else if (skill.rarity == Rarity.COMMON) {
                        view.setBackgroundResource(R.drawable.subskillscommon)
                    }
                }

                override fun onNothingSelected(parentView: AdapterView<*>) {}
            }
        }
    }

    private fun getSubSkills(): List<SubSkill> {
        val subSkill1 = SubSkill.fromIndex(binding.subSkill1.selectedItemPosition)
        val subSkill2 = SubSkill.fromIndex(binding.subSkill2.selectedItemPosition)
        val subSkill3 = SubSkill.fromIndex(binding.subSkill3.selectedItemPosition)
        val subSkill4 = SubSkill.fromIndex(binding.subSkill4.selectedItemPosition)
        val subSkill5 = SubSkill.fromIndex(binding.subSkill5.selectedItemPosition)
        return listOf(subSkill1, subSkill2, subSkill3, subSkill4, subSkill5)
    }

    private fun ingredientBindings(mainActivity: MainActivity) {
        val ingredientImageCodes = getIngredientCodes()
        val ingredientViews = listOf(binding.ingredient1, binding.ingredient2, binding.ingredient3)
        val ingredientAdapter = IngredientSpinnerAdapter(mainActivity, ingredientImageCodes)
        for (view in ingredientViews) {
            view.adapter = ingredientAdapter
        }
        // Ingredient Multipliers
        binding.ingredient1Multiplier.adapter = ArrayAdapter(
            mainActivity,
            androidx.appcompat.R.layout.select_dialog_item_material,
            listOf("x1", "x2")
        )
        binding.ingredient2Multiplier.adapter = ArrayAdapter(
            mainActivity,
            androidx.appcompat.R.layout.select_dialog_item_material,
            listOf("x1", "x2", "x3", "x4", "x5", "x6")
        )
        binding.ingredient3Multiplier.adapter = ArrayAdapter(
            mainActivity,
            androidx.appcompat.R.layout.select_dialog_item_material,
            listOf("x1", "x2", "x3", "x4", "x5", "x6", "x7", "x8", "x9", "x10", "x11", "x12")
        )
    }

    private fun getIngredients(): List<Ingredient> {
        val ingredient1 = Ingredient(
            Ingredients.entries[binding.ingredient1.selectedItemPosition],
            binding.ingredient1Multiplier.selectedItemPosition + 1
        )
        val ingredient2 = Ingredient(
            Ingredients.entries[binding.ingredient2.selectedItemPosition],
            binding.ingredient2Multiplier.selectedItemPosition + 1
        )
        val ingredient3 = Ingredient(
            Ingredients.entries[binding.ingredient3.selectedItemPosition],
            binding.ingredient3Multiplier.selectedItemPosition + 1
        )
        return listOf(ingredient1, ingredient2, ingredient3)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}