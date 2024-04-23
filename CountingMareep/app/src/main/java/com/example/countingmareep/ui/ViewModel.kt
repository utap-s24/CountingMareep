package com.example.countingmareep

import PokemonDataModel
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.example.countingmareep.network.ApiService
import com.example.countingmareep.network.PokemonResponse
import com.example.countingmareep.network.UserResponse
import com.example.countingmareep.ui.box.PokemonData
import com.example.countingmareep.ui.team_builder.PokemonTeam
import com.google.gson.Gson
import okhttp3.OkHttpClient
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

    fun loadPokemonBox(mainActivity: MainActivity) {
        val client = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://countingmareep.onrender.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val getCall = service.getPokemon(sessionID)

        getCall.enqueue(object : Callback<List<PokemonResponse>> {
            override fun onResponse(call: Call<List<PokemonResponse>>, response: Response<List<PokemonResponse>>) {
                if (response.isSuccessful) {
                    Toast.makeText(mainActivity, "Box Length ${response.body()?.size.toString()}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(mainActivity, "Bad Code ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<PokemonResponse>>, t: Throwable) {
                Toast.makeText(mainActivity, "Failed Some Reason ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getSession(): String {
        return sessionID
    }
}