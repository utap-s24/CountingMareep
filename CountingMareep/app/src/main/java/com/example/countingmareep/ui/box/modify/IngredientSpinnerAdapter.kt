package com.example.countingmareep.ui.box.modify

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.example.countingmareep.R
import java.io.IOException

// Source: https://stackoverflow.com/questions/3609231/how-is-it-possible-to-create-a-spinner-with-images-instead-of-text
data class SpinnerItem(val imageSrc: String)

class IngredientSpinnerAdapter(context: Context, private val spinnerItems: List<SpinnerItem>)
    : ArrayAdapter<SpinnerItem>(context, 0, spinnerItems) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    private fun createItemView(position: Int, recycledView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        val view = recycledView ?: LayoutInflater.from(context).inflate(R.layout.ingredient_item_layout, parent, false)
        val imageView = view.findViewById<ImageView>(R.id.imageViewIcon)
        try {
            context.assets.open("ingredients/${item!!.imageSrc}.png").use { inputStream ->
                val drawable = Drawable.createFromStream(inputStream, null)
                imageView.setImageDrawable(drawable)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
//        view.findViewById<ImageView>(R.id.imageViewIcon).setImageResource(item?.imageResId ?: 0)
//        view.findViewById<TextView>(R.id.textViewName).text = item?.text

        return view
    }
}
