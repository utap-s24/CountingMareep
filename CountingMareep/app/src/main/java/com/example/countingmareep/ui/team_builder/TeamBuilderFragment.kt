package com.example.countingmareep.ui.team_builder

import PokemonDataModel
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.countingmareep.databinding.FragmentTeamBuilderBinding
import com.example.countingmareep.ui.box.PokemonData
import com.example.countingmareep.ui.box.Nature
import com.example.countingmareep.ui.box.NatureData
import com.example.countingmareep.ui.box.modify.SubSkill
import com.example.countingmareep.ui.box.Ingredient
import com.example.countingmareep.ui.box.Ingredients
import java.io.IOException
import java.util.Random

class TeamBuilderFragment : Fragment() {

    private var _binding: FragmentTeamBuilderBinding? = null
    private val binding get() = _binding!!
    private val random = Random()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTeamBuilderBinding.inflate(inflater, container, false)
        val root: View = binding.root
    
        val pokemonDataModels = List(5) { generateRandomPokemonDataModel() } // Generate 5 random Pokemon
        val totalRP = pokemonDataModels.sumOf { it.RP }
    
        populatePokemonPreview(binding.imagePokemonMain, binding.textPokemonMainDetails, pokemonDataModels[0])
        populatePokemonPreview(binding.imagePokemonPreview1, binding.textPokemonPreview1Details, pokemonDataModels[1])
        populatePokemonPreview(binding.imagePokemonPreview2, binding.textPokemonPreview2Details, pokemonDataModels[2])
        populatePokemonPreview(binding.imagePokemonPreview3, binding.textPokemonPreview3Details, pokemonDataModels[3])
        populatePokemonPreview(binding.imagePokemonPreview4, binding.textPokemonPreview4Details, pokemonDataModels[4])
    
        binding.textTotalTeamRp.text = "Total Team RP: $totalRP"
    
        return root
    }
    

    private fun populatePokemonPreview(imageView: ImageView, textView: TextView, pokemonDataModel: PokemonDataModel) {
        try {
            requireActivity().assets.open("pokemon/${pokemonDataModel.pokedexEntry}.png").use { inputStream ->
                val drawable = Drawable.createFromStream(inputStream, null)
                imageView.setImageDrawable(drawable)
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            // Handle the case where the image is not found. Maybe set a default image.
        }
    
        textView.text = "${pokemonDataModel.name} lv. ${pokemonDataModel.level}"
    }
    

    private fun generateRandomPokemonDataModel(): PokemonDataModel {
        val random = Random()
        val pokemonBaseData = PokemonData.getRandom()
        val level = random.nextInt(30) + 1  // Level from 1 to 30
        val rp = random.nextInt(100) + 1  // RP for example, replace with your logic
        val nature = Nature.getRandom()
        val subSkills = listOf(SubSkill.fromIndex(random.nextInt(SubSkill.subSkillNames.size)))
        val ingredients = listOf(Ingredient(Ingredients.values()[random.nextInt(Ingredients.values().size)], random.nextInt(10) + 1))
        val mainSkillLevel = random.nextInt(5) + 1  // MainSkillLevel from 1 to 5
    
        return PokemonDataModel(
            name = pokemonBaseData.name,
            level = level,
            pokedexEntry = pokemonBaseData.dexNumber,
            subSkills = subSkills,
            ingredients = ingredients,
            nature = nature,
            RP = rp,
            mainSkillLevel = mainSkillLevel
        )
    }
    

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
