package com.example.countingmareep.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.countingmareep.R
import java.util.Locale

class TeamAdapter(
    private val context: Context?,
    private var teams: List<SearchFragment.Team>,
    private val onTeamClickListener: OnTeamClickListener
) : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>(), Filterable {
    var listener: OnTeamsFilteredListener? = null

    interface OnTeamsFilteredListener {
        fun onTeamsFiltered(count: Int)
    }
    interface OnTeamClickListener {
        fun onTeamClick(team: SearchFragment.Team)
    }

    var filteredTeams: List<SearchFragment.Team> = teams

    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val teamName: TextView = view.findViewById(R.id.team_name)
        val pokemonRecyclerView: RecyclerView = view.findViewById(R.id.pokemon_list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.team_card_layout, parent, false)
        return TeamViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val team = filteredTeams[position]
        holder.teamName.text = team.name

        val pokemonAdapter = PokemonAdapter(context, team.pokemonList)
        holder.pokemonRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = pokemonAdapter
        }
        holder.itemView.setOnClickListener {
            onTeamClickListener.onTeamClick(team)
        }
    }

    override fun getItemCount(): Int {
        return filteredTeams.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                filteredTeams = if (charSearch.isEmpty()) {
                    teams
                } else {
                    teams.filter { it.name.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT)) }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredTeams
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredTeams = results?.values as List<SearchFragment.Team>
                notifyDataSetChanged()
                // Notify the fragment to update the counter
                listener?.onTeamsFiltered(filteredTeams.size)
            }

        }
    }
}
