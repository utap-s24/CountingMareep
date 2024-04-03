package com.example.countingmareep

import PokemonDataModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countingmareep.ui.box.Ingredient
import com.example.countingmareep.ui.box.Ingredients
import com.example.countingmareep.ui.box.Nature
import com.example.countingmareep.ui.box.Skill
import com.example.countingmareep.ui.box.Skills
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
        for (i in 1..50) {
            outList.add(PokemonDataModel(
                "Barry", rand.nextInt(50), 26,
                listOf(Skill(Skills.BerryFindingS)),
                listOf(Ingredient(Ingredients.FANCY_APPLE, 1)),
                Nature(),
                rand.nextInt(3000)
            ))
        }

        return outList
    }
}