package com.example.countingmareep.ui.search

import PokemonDataModel
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.countingmareep.R
import com.example.countingmareep.ui.box.PokemonData
import java.io.IOException

class PokemonAdapter(
    private val context: Context?,
    private val pokemonList: List<PokemonDataModel>
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pokemonImage: ImageView = view.findViewById(R.id.pokemon_image)
        val pokemonLevel: TextView = view.findViewById(R.id.pokemon_level)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.pokemon_card_layout, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]

        // Load the image from assets
        try {
            context?.assets?.open("pokemon/${pokemon.pokedexEntry}.png")?.use { inputStream ->
                val drawable = Drawable.createFromStream(inputStream, null)
                holder.pokemonImage.setImageDrawable(drawable)
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            // Set a default image if there's an exception
        }

        // Truncate the Pokémon's name to 6 characters and display it with the level
        val truncatedName = if (pokemon.name.length > 6) "${pokemon.name.substring(0, 6)}" else pokemon.name
        holder.pokemonLevel.text = "$truncatedName lv. ${pokemon.level}"

        // If this is the main Pokémon (first in the list), set a red border
        // Inside onBindViewHolder in PokemonAdapter
        if (position == 0) {
            // Apply a red border to the main Pokémon card
            val borderSize = 10 // Adjust the size as needed
            val borderColor = Color.parseColor("#f87258")
            val background = Color.WHITE
            val border = GradientDrawable().apply {
                setStroke(borderSize, borderColor)
                setColor(background)
            }
            holder.itemView.background = border
        } else {
            // Reset to default for other Pokémon cards
            (holder.itemView as CardView).setCardBackgroundColor(Color.WHITE)
        }
    }


    override fun getItemCount(): Int = pokemonList.size
}
