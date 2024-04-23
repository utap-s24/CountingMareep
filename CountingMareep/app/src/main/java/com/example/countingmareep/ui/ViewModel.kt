package com.example.countingmareep

import PokemonDataModel
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countingmareep.network.ApiService
import com.example.countingmareep.network.CreatePokemonRequest
import com.example.countingmareep.network.PokemonResponse
import com.example.countingmareep.network.UserResponse
import com.example.countingmareep.ui.box.Ingredient
import com.example.countingmareep.ui.box.Ingredients
import com.example.countingmareep.ui.box.Nature
import com.example.countingmareep.ui.box.PokemonData
import com.example.countingmareep.ui.box.modify.SubSkill
import com.example.countingmareep.ui.team_builder.PokemonTeam
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.internal.toImmutableList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
    private var pokemonList: MutableList<PokemonDataModel> = mutableListOf()
    private val teamsList: MutableList<PokemonTeam> = mutableListOf()

    init {
        Log.d("XXX", "VIEW MODEL INITING")
    }

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

    /**
     * Creates a HTTP Request to Update the DB as well.
     */
    fun addPokemon(pokemon: PokemonDataModel) {
        pokemonList.add(pokemon)

        val subSkillList: MutableList<Int> = mutableListOf()
        for (subSkill in pokemon.subSkills) {
            subSkillList.add(subSkill.id.ordinal)
        }

        val ingredientList: MutableList<List<Int>> = mutableListOf()
        for (ingredient in pokemon.ingredients) {
            ingredientList.add(listOf(ingredient.id.ordinal, ingredient.quantity))
        }

        val client = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .baseUrl(MainActivity.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val getCall = service.createPokemon(
            CreatePokemonRequest(
                sessionID,
                pokemon.name,
                pokemon.level,
                pokemon.pokedexEntry,
                subSkillList.toImmutableList(),
                ingredientList.toImmutableList(),
                pokemon.nature.nature,
                pokemon.RP,
                pokemon.mainSkillLevel,
                pokemon.pokemonID
            )
        )
        getCall.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("XXX", "Created Pokemon")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {

            }
        })
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

    fun loadPokemonBox(mainActivity: MainActivity) {
        val client = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .baseUrl(MainActivity.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val getCall = service.getPokemon(sessionID)
        pokemonList.clear()
        getCall.enqueue(object : Callback<List<PokemonResponse>> {
            override fun onResponse(
                call: Call<List<PokemonResponse>>,
                response: Response<List<PokemonResponse>>
            ) {
                if (response.isSuccessful) {
                    val list = response.body()
                    if (list != null) {
                        // Populate Pokemon Box
                        for (pokemonRes in list) {
                            // Recreate SubSkills
                            val subSkillList = pokemonRes.subSkills
                            val realSubSkills: MutableList<SubSkill> = mutableListOf()
                            for (subSkillID in subSkillList) {
                                realSubSkills.add(SubSkill(SubSkill.getSkillFromOrdinal(subSkillID)))
                            }
                            // Recreate Ingredients
                            val ingredientList = pokemonRes.ingredients
                            val realIngredients: MutableList<Ingredient> = mutableListOf()
                            for (ingredient in ingredientList) {
                                realIngredients.add(
                                    Ingredient(
                                        Ingredient.getIngredientFromOrdinal(ingredient[0]),
                                        ingredient[1]
                                    )
                                )
                            }
                            pokemonList.add(
                                PokemonDataModel(
                                    pokemonRes.name,
                                    pokemonRes.level,
                                    pokemonRes.pokedexEntry,
                                    realSubSkills,
                                    realIngredients,
                                    Nature.natureFromName(pokemonRes.nature),
                                    pokemonRes.RP,
                                    pokemonRes.mainSkillLevel,
                                    pokemonRes.pokemonID
                                )
                            )
                        }
                    }
                    mainActivity.loggedInRedirect()
                } else {
                    Toast.makeText(mainActivity, "Bad Code ${response.code()}", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<List<PokemonResponse>>, t: Throwable) {
                Toast.makeText(mainActivity, "Failed Some Reason ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun getSession(): String {
        return sessionID
    }
}