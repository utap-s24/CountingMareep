package com.example.countingmareep

import PokemonDataModel
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.countingmareep.ui.box.Ingredient
import com.example.countingmareep.ui.box.Ingredients
import com.example.countingmareep.ui.box.Nature
import com.example.countingmareep.ui.box.PokemonData
import com.example.countingmareep.ui.box.modify.SubSkill
import com.example.countingmareep.ui.box.modify.Skills
import com.example.countingmareep.ui.team_builder.PokemonTeam
import java.util.Random
import java.util.UUID

class ViewModel : ViewModel() {
    companion object {
        val POKEMON_AMOUNT: Int = PokemonData.pokemon.size
    }

    private var sessionID: String = ""
    private var themeIsDark: Boolean = false
    private var userIcon: Int = 243
    private var userRank: Int = 1
    private var userBefriended: Int = 32
    private var hoursSlept: Int = 1876 // Hours

    private var selectedBoxPokemon: Int = -1
    private val pokemonList: MutableList<PokemonDataModel> = mutableListOf()

    private val teamsList: MutableList<PokemonTeam> = mutableListOf()

    fun setTheme(isDark: Boolean) {
        themeIsDark = isDark
    }

    fun setSelectedBox(uuid: String) {
        var index = 0
        for (pokemon in pokemonList) {
            if (pokemon.pokemonID == uuid) {
                selectedBoxPokemon = index
                break
            }
            index++
        }
    }

    fun getSelectedBox(): Int {
        return selectedBoxPokemon
    }

    fun isDark(): Boolean {
        return themeIsDark
    }

    fun addPokemon(pokemon: PokemonDataModel) {
        val ingredientsList = pokemon.ingredients
        Log.d("Ingredient Type", ingredientsList[0].id.ordinal.toString())
        Log.d("Ingredient Quantity", ingredientsList[0].quantity.toString())

        pokemonList.add(pokemon)
    }

    fun getDataList(): List<PokemonDataModel> {
        return pokemonList
    }

    fun getRank(): Int {
        return userRank
    }

    fun setRank(rank: Int) {
        userRank = rank
    }

    fun getBefriended(): Int {
        return userBefriended
    }

    fun setBefriended(friended: Int) {
        userBefriended = friended
    }

    fun getHoursSlept(): Int {
        return hoursSlept
    }

    fun setHoursSlept(hours: Int) {
        hoursSlept = hours
    }

    fun addTeam(team: PokemonTeam) {
        teamsList.add(team)
    }

    fun getTeamCount(): Int {
        return teamsList.size
    }

    fun getIcon(): Int {
        return userIcon
    }

    fun setSession(id: String) {
        sessionID = id
    }

    fun getSession(): String {
        return sessionID
    }
}