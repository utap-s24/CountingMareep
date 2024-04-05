package com.example.countingmareep

import PokemonDataModel
import androidx.lifecycle.ViewModel
import com.example.countingmareep.ui.box.Ingredient
import com.example.countingmareep.ui.box.Ingredients
import com.example.countingmareep.ui.box.Nature
import com.example.countingmareep.ui.box.PokemonData
import com.example.countingmareep.ui.box.modify.SubSkill
import com.example.countingmareep.ui.box.modify.Skills
import java.util.Random

class ViewModel : ViewModel() {
    private var themeIsDark: Boolean = false
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
            2
        )
    )

    fun setTheme(isDark: Boolean): Unit {
        themeIsDark = isDark
    }

    fun isDark(): Boolean {
        return themeIsDark
    }

    fun addPokemon(pokemon: PokemonDataModel): Unit {
        pokemonList.add(pokemon)
    }

    fun getDataList(): List<PokemonDataModel> {
//        val outList: MutableList<PokemonDataModel> = mutableListOf()
//        val rand = Random()
//        for (pokemon in PokemonData.pokemon) {
//            outList.add(PokemonDataModel(
//                pokemon.name, rand.nextInt(50), pokemon.dexNumber,
//                listOf(SubSkill(Skills.BerryFindingS)),
//                listOf(Ingredient(Ingredients.FANCY_APPLE, 1)),
//                Nature.getRandom(),
//                rand.nextInt(3000),
//                2
//            ))
//        }
        return pokemonList
    }
}