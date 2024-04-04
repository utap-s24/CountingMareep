package com.example.countingmareep

import com.example.countingmareep.ui.box.PokemonData
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun get_Ids() {
        val list = PokemonData.pokemon
        val outList: MutableList<Int> = mutableListOf()
        for (pokemon in list) {
            outList.add(pokemon.dexNumber)
        }
        print(outList)
    }
}