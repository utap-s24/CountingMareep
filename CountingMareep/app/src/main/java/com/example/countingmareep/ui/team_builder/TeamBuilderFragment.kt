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
import androidx.fragment.app.activityViewModels
import com.example.countingmareep.MainActivity
import com.example.countingmareep.ViewModel
import com.example.countingmareep.databinding.FragmentTeamBuilderBinding
import com.example.countingmareep.ui.box.PokemonData
import com.example.countingmareep.ui.box.Nature
import com.example.countingmareep.ui.box.NatureData
import com.example.countingmareep.ui.box.modify.SubSkill
import com.example.countingmareep.ui.box.Ingredient
import com.example.countingmareep.ui.box.Ingredients
import java.io.IOException
import java.util.Random
import java.util.UUID

class TeamBuilderFragment : Fragment() {

    private var _binding: FragmentTeamBuilderBinding? = null
    private val binding get() = _binding!!
    private val random = Random()
    private val viewModel: ViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamBuilderBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val pokemonData = viewModel.getDataList()
        var currentTeam = loadTeam(pokemonData)
        val mainActivity = activity as MainActivity

        binding.buttonGenerateAltTeam.setOnClickListener {
            val sortedPokemon = pokemonData.sortedByDescending { it.RP }
            currentTeam = loadTeam(sortedPokemon)
        }

        binding.buttonRandomTeam.setOnClickListener {
            val shuffledPokemon = pokemonData.shuffled()
            currentTeam = loadTeam(shuffledPokemon)
        }

        binding.buttonSave.setOnClickListener {
            if (currentTeam.isEmpty()) {
                return@setOnClickListener
            }
            val pok1: PokemonDataModel = currentTeam[0]
            var pok2: PokemonDataModel? = null
            var pok3: PokemonDataModel? = null
            var pok4: PokemonDataModel? = null
            var pok5: PokemonDataModel? = null
            if (currentTeam.size > 1) {
                pok2 = currentTeam[1]
            }
            if (currentTeam.size > 2) {
                pok3 = currentTeam[2]
            }
            if (currentTeam.size > 3) {
                pok4 = currentTeam[3]
            }
            if (currentTeam.size > 4) {
                pok5 = currentTeam[4]
            }
            viewModel.addTeam(
                PokemonTeam(
                    binding.editTeamName.text.toString(),
                    pok1,
                    pok2,
                    pok3,
                    pok4,
                    pok5
                ), mainActivity
            )
        }
        return root
    }

    private fun loadTeam(pokemonData: List<PokemonDataModel>): List<PokemonDataModel> {
        val teamList: MutableList<PokemonDataModel> = mutableListOf()
        for (i in 0..4) {
            if (i < pokemonData.size) {
                teamList.add(pokemonData[i])
            }
        }
        val totalRP = teamList.sumOf { it.RP }

        if (teamList.size > 0) {
            populatePokemonPreview(
                binding.imagePokemonMain,
                binding.textPokemonMainDetails,
                teamList[0]
            )
            if (teamList.size > 1) {
                populatePokemonPreview(
                    binding.imagePokemonPreview1,
                    binding.textPokemonPreview1Details,
                    teamList[1]
                )
                if (teamList.size > 2) {
                    populatePokemonPreview(
                        binding.imagePokemonPreview2,
                        binding.textPokemonPreview2Details,
                        teamList[2]
                    )
                    if (teamList.size > 3) {
                        populatePokemonPreview(
                            binding.imagePokemonPreview3,
                            binding.textPokemonPreview3Details,
                            teamList[3]
                        )
                        if (teamList.size > 4) {
                            populatePokemonPreview(
                                binding.imagePokemonPreview4,
                                binding.textPokemonPreview4Details,
                                teamList[4]
                            )
                        }
                    }
                }
            }
        }
        binding.textTotalTeamRp.text = "Total Team RP: $totalRP"
        return teamList
    }


    private fun populatePokemonPreview(
        imageView: ImageView,
        textView: TextView,
        pokemonDataModel: PokemonDataModel
    ) {
        try {
            requireActivity().assets.open("pokemon/${pokemonDataModel.pokedexEntry}.png")
                .use { inputStream ->
                    val drawable = Drawable.createFromStream(inputStream, null)
                    imageView.setImageDrawable(drawable)
                }
        } catch (ex: IOException) {
            ex.printStackTrace()
            // Handle the case where the image is not found. Maybe set a default image.
        }

        textView.text = "${pokemonDataModel.name} lv. ${pokemonDataModel.level}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
