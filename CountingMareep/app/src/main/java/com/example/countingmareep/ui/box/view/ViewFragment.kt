package com.example.countingmareep.ui.box.view

import MarginItemDecoration
import ModifyAdapter
import PokemonDataModel
import android.graphics.drawable.Drawable
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
import com.example.countingmareep.databinding.FragmentViewBinding
import com.example.countingmareep.ui.box.Ingredient
import com.example.countingmareep.ui.box.Ingredients
import com.example.countingmareep.ui.box.Nature
import com.example.countingmareep.ui.box.PokemonData
import com.example.countingmareep.ui.box.modify.Rarity
import java.io.IOException
import java.io.InputStream

class ViewFragment : Fragment() {
    companion object {
        const val TAG = "BoxFragment"
    }

    private var _binding: FragmentViewBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: ViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val mainActivity = activity as MainActivity
        val pokemonList = viewModel.getDataList()
        val selectedPokemon = pokemonList[viewModel.getSelectedBox()]
        try {
            val ims: InputStream = mainActivity.assets.open("pokemon/${selectedPokemon.pokedexEntry}.png")
            val d = Drawable.createFromStream(ims, null)
            binding.pokemonImage.setImageDrawable(d)
            ims.close()
        } catch (ex: IOException) {
        }
        binding.nickname.setText(selectedPokemon.name)
        binding.rp.setText(selectedPokemon.RP.toString())
        binding.level.setText(selectedPokemon.level.toString())
        binding.skillLevelInput.setText(selectedPokemon.mainSkillLevel.toString())
        // Nature
        val nature = selectedPokemon.nature
        binding.nature.text = nature.nature
        binding.natureUpText.text = nature.fieldUp
        binding.natureUpPercent.text = "+${nature.fieldUpAmount}%"
        binding.natureDownText.text = nature.fieldDown
        binding.natureDownPercent.text = "+${nature.fieldDownAmount}%"

        val subSkillList = listOf(binding.subSkill1, binding.subSkill2, binding.subSkill3, binding.subSkill4, binding.subSkill5)
        var index = 0
        for(subSkill in subSkillList) {
            val skill = selectedPokemon.subSkills[index]
            subSkill.text = skill.getName()
            if (skill.getRarity() == Rarity.VERY_RARE) {
                subSkill.setBackgroundResource(R.drawable.subskills)
            } else if (skill.getRarity() == Rarity.RARE) {
                subSkill.setBackgroundResource(R.drawable.subskillsrare)
            } else if (skill.getRarity() == Rarity.COMMON) {
                subSkill.setBackgroundResource(R.drawable.subskillscommon)
            }
            index++
        }

        val ingredientList = listOf(binding.ingredient1, binding.ingredient2, binding.ingredient3)
        index = 0
        for(ingredientBinding in ingredientList) {
            val ingredient = selectedPokemon.ingredients[index]
            try {
                mainActivity.assets.open("ingredients/${ingredient.id.name.lowercase().replace("_", "")}.png").use { inputStream ->
                    val drawable = Drawable.createFromStream(inputStream, null)
                    ingredientBinding.setImageDrawable(drawable)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            index++
        }

        index = 0
        val ingredientCountList = listOf(binding.ingredient1Multiplier, binding.ingredient2Multiplier, binding.ingredient3Multiplier)
        for(ingredientCountBinding in ingredientCountList) {
            val ingredient = selectedPokemon.ingredients[index]
            ingredientCountBinding.text = "x${ingredient.quantity.toString()}"
            index++
        }

        binding.saveButton.setOnClickListener {
            val levelStr = binding.level.text.toString()
            val rpStr = binding.rp.text.toString()
            val skillLevelStr = binding.skillLevelInput.text.toString()
            val nameStr = binding.nickname.text.toString()
            if(levelStr.isEmpty() || rpStr.isEmpty() || skillLevelStr.isEmpty() || nameStr.isEmpty()) {
                Toast.makeText(mainActivity, "Unfilled Value", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Check Numbers
            val level = levelStr.toInt()
            val RP = rpStr.toInt()
            val skillLevel = skillLevelStr.toInt()
            if (level <= 0 || level > 100 || RP <= 0 || skillLevel <= 0) {
                Toast.makeText(mainActivity, "Some Value is Out of Bounds", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // All Good
            selectedPokemon.level = level
            selectedPokemon.name = nameStr
            selectedPokemon.mainSkillLevel = skillLevel
            selectedPokemon.RP = RP
            viewModel.updatePokemon(selectedPokemon)
            // Get back to Box View
            mainActivity.navController.popBackStack()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}