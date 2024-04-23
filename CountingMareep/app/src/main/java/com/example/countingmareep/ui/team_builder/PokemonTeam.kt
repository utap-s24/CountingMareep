package com.example.countingmareep.ui.team_builder

import PokemonDataModel
import com.example.countingmareep.ui.box.PokemonData

class PokemonTeam(
    val teamName: String,
    val pok1: PokemonDataModel,
    val pok2: PokemonDataModel?,
    val pok3: PokemonDataModel?,
    val pok4: PokemonDataModel?,
    val pok5: PokemonDataModel?
) {
    private var listOfMons: MutableList<PokemonDataModel> = mutableListOf(pok1)
    init {
        if(pok2 != null) {
            listOfMons.add(pok2)
        }
        if(pok3 != null) {
            listOfMons.add(pok3)
        }
        if(pok4 != null) {
            listOfMons.add(pok4)
        }
        if(pok5 != null) {
            listOfMons.add(pok5)
        }
    }
    fun totalRP(): Int {
        var totalRP = 0
        for(pokemon in listOfMons) {
            totalRP += pokemon.RP
        }
        return totalRP
    }
}