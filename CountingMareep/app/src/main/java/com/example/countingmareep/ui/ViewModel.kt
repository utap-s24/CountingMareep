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

    fun setTheme(isDark: Boolean): Unit {
        themeIsDark = isDark
    }

    fun isDark(): Boolean {
        return themeIsDark
    }

    fun getDataList(): List<PokemonDataModel> {
        val outList: MutableList<PokemonDataModel> = mutableListOf()
        val rand = Random()
        for (pokemon in PokemonData.pokemon) {
            outList.add(PokemonDataModel(
                pokemon.name, rand.nextInt(50), pokemon.dexNumber,
                listOf(SubSkill(Skills.BerryFindingS)),
                listOf(Ingredient(Ingredients.FANCY_APPLE, 1)),
                Nature.getRandom(),
                rand.nextInt(3000),
                2
            ))
        }

        return outList
    }
}