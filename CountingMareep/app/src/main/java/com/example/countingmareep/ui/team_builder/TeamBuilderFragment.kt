package com.example.countingmareep.ui.team_builder

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.countingmareep.databinding.FragmentTeamBuilderBinding
import com.example.countingmareep.ui.box.PokemonData
import java.io.IOException
import java.util.Random

class TeamBuilderFragment : Fragment() {

    private var _binding: FragmentTeamBuilderBinding? = null
    private val binding get() = _binding!!
    private val random = Random()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTeamBuilderBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Populate the main Pokémon and the four previews
        populatePokemonPreview(binding.imagePokemonMain, binding.textPokemonMainDetails)
        populatePokemonPreview(binding.imagePokemonPreview1, binding.textPokemonPreview1Details)
        populatePokemonPreview(binding.imagePokemonPreview2, binding.textPokemonPreview2Details)
        populatePokemonPreview(binding.imagePokemonPreview3, binding.textPokemonPreview3Details)
        populatePokemonPreview(binding.imagePokemonPreview4, binding.textPokemonPreview4Details)

        return root
    }

    private fun populatePokemonPreview(imageView: ImageView, textView: TextView) {
        val pokemon = PokemonData.getRandom()
        val level = random.nextInt(30) + 1  // Random level from 1 to 30

        // Load the image from assets using the dexNumber
        val assetPath = "pokemon/${pokemon.dexNumber}.png"
        try {
            context?.assets?.open(assetPath)?.use { inputStream ->
                val drawable = Drawable.createFromStream(inputStream, null)
                imageView.setImageDrawable(drawable)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            // Handle the case where the image is not found. Maybe set a default image.
        }

        textView.text = "${pokemon.name} lv. $level"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
