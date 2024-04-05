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
    private var userIcon: Int = 243
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
            2
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
            5
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
            3
        )
    )

    fun setTheme(isDark: Boolean): Unit {
        themeIsDark = isDark
    }

    fun setSelectedBox(index: Int): Unit {
        selectedBoxPokemon = index
    }

    fun getSelectedBox(): Int {
        return selectedBoxPokemon
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

    fun getIcon(): Int {
        return userIcon
    }
}