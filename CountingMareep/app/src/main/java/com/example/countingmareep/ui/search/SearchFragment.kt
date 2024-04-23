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
import com.example.countingmareep.network.TeamResponse
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
    private lateinit var fakeTeams: List<TeamResponse>
    private val viewModel: ViewModel by activityViewModels()

    data class Team(
        val name: String,
        val pokemonList: List<PokemonDataModel>
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        viewModel.loadTeams()
        fakeTeams = viewModel.getAllTeams()

        setupRecyclerView()
        setupSearchBar()

        return binding.root
    }

    private fun setupRecyclerView() {
        teamAdapter = TeamAdapter(context, fakeTeams, object : TeamAdapter.OnTeamClickListener {
            override fun onTeamClick(team: TeamResponse) {
                /* ToDo: Tap support */
                Log.d(TAG, "Team ${team.teamName} clicked")
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}