import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.countingmareep.MainActivity
import com.example.countingmareep.R
import com.example.countingmareep.databinding.MiniCardLayoutBinding
import com.example.countingmareep.ui.box.PokemonBaseData
import java.io.IOException
import java.io.InputStream


class ModifyAdapter(
    private var dataList: List<PokemonBaseData>,
    private var mainActivity: MainActivity
) :
    RecyclerView.Adapter<ModifyAdapter.ViewHolder>() {
    private var selected = 0;
    private var originalColor: ColorStateList? = null

    fun submitList(newList: List<PokemonBaseData>) {
        dataList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = MiniCardLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        val binding = holder.cardBinding
        try {
            val ims: InputStream = mainActivity.assets.open("pokemon/${item.dexNumber}.png")
            val d = Drawable.createFromStream(ims, null)
            binding.pokemonImage.setImageDrawable(d)
            ims.close()
        } catch (ex: IOException) {
            return
        }
        if(originalColor == null) {
            originalColor = binding.root.cardBackgroundColor
        }
        if (selected == position) {
            binding.root.setBackgroundColor(Color.parseColor("#FF03DAC5"))
        } else {
            binding.root.setBackgroundColor(originalColor!!.defaultColor)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(val cardBinding: MiniCardLayoutBinding) :
        RecyclerView.ViewHolder(cardBinding.root) {
        init {
            cardBinding.root.setOnClickListener {
                val oldSelected = selected
                selected = adapterPosition
                notifyItemChanged(oldSelected)
                notifyItemChanged(selected)
            }
        }
    }
}
