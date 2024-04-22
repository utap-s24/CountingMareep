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

    private var themeIsDark: Boolean = false
    private var userIcon: Int = 243
    private var userRank: Int = 1
    private var userBefriended: Int = 32
    private var hoursSlept: Int = 1876 // Hours

    private var selectedBoxPokemon: Int = -1
    private val pokemonList: MutableList<PokemonDataModel> = mutableListOf(
        PokemonDataModel(
            "Garry",
            50,
            26,
            listOf(
                SubSkill(Skills.BerryFindingS),
                SubSkill(Skills.HelpingBonus),
                SubSkill(Skills.InventoryUpS),
                SubSkill(Skills.SkillLevelS),
                SubSkill(Skills.HelpingSpeedM)
            ),
            listOf(
                Ingredient(Ingredients.FANCY_APPLE, 1),
                Ingredient(Ingredients.WARMING_GINGER, 2),
                Ingredient(Ingredients.WARMING_GINGER, 3),
            ),
            Nature.natureFromName("Bold"),
            3075,
            2,
            UUID.randomUUID().toString()
        ),
        PokemonDataModel(
            "Mellow",
            32,
            40,
            listOf(
                SubSkill(Skills.HelpingBonus),
                SubSkill(Skills.SkillLevelM),
                SubSkill(Skills.HelpingSpeedM),
                SubSkill(Skills.SleepEXPBonus),
                SubSkill(Skills.SkillTriggerS)
            ),
            listOf(
                Ingredient(Ingredients.HONEY, 1),
                Ingredient(Ingredients.PURE_OIL, 2),
                Ingredient(Ingredients.HONEY, 4),
            ),
            Nature.natureFromName("Sassy"),
            2306,
            5,
            UUID.randomUUID().toString()
        ),
        PokemonDataModel(
            "Blaster",
            43,
            9,
            listOf(
                SubSkill(Skills.IngredientFinderM),
                SubSkill(Skills.InventoryUpM),
                SubSkill(Skills.HelpingSpeedS),
                SubSkill(Skills.SkillTriggerM),
                SubSkill(Skills.InventoryUpS)
            ),
            listOf(
                Ingredient(Ingredients.MOOMOO_MILK, 2),
                Ingredient(Ingredients.SOOTHING_CACAO, 3),
                Ingredient(Ingredients.BEAN_SAUSAGE, 7),
            ),
            Nature.natureFromName("Mild"),
            2177,
            3,
            UUID.randomUUID().toString()
        )
    )

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
}