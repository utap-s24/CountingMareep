package com.example.countingmareep.ui.search

import PokemonDataModel
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countingmareep.ViewModel
import com.example.countingmareep.databinding.FragmentSearchBinding
import com.example.countingmareep.ui.box.Ingredient
import com.example.countingmareep.ui.box.Ingredients
import com.example.countingmareep.ui.box.Nature
import com.example.countingmareep.ui.box.PokemonData
import com.example.countingmareep.ui.box.modify.SubSkill
import java.util.Random
import java.util.UUID

class SearchFragment : Fragment() {
    companion object {
        const val TAG = "SearchFragment"
    }

    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var teamAdapter: TeamAdapter
    private lateinit var fakeTeams: List<Team>
    private val viewModel: ViewModel by activityViewModels()

    data class Team(
        val name: String,
        val pokemonList: List<PokemonDataModel>
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        // Generate fake teams
        fakeTeams = generateFakeTeams(30)

        setupRecyclerView()
        setupSearchBar()

        return binding.root
    }

    private fun generateFakeTeams(count: Int): List<Team> {
        return List(count) { index ->
            Team(
                name = "Team ${index + 1}",
                pokemonList = List(5) { generateRandomPokemonDataModel() }
            )
        }
    }

    private fun setupRecyclerView() {
        teamAdapter = TeamAdapter(context, fakeTeams, object : TeamAdapter.OnTeamClickListener {
            override fun onTeamClick(team: SearchFragment.Team) {
                /* ToDo: Tap support */
                Log.d(TAG, "Team ${team.name} clicked")
            }
        })

        // Set the listener to update the UI based on the filtered results
        teamAdapter.listener = object : TeamAdapter.OnTeamsFilteredListener {
            override fun onTeamsFiltered(count: Int) {
                binding.textSearch.text = "$count Teams found"
            }
        }

        binding.teamsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = teamAdapter
        }
    }


    private fun setupSearchBar() {
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                teamAdapter.filter.filter(s.toString())
                binding.textSearch.text = "${teamAdapter.filteredTeams.size} Teams found"
            }
        
            override fun afterTextChanged(s: Editable?) {}
        })
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
            mainSkillLevel = mainSkillLevel,
            pokemonID = UUID.randomUUID().toString()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}