import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countingmareep.MainActivity
import com.example.countingmareep.R
import com.example.countingmareep.ViewModel
import com.example.countingmareep.databinding.CardLayoutBinding
import com.example.countingmareep.ui.box.Ingredient
import com.example.countingmareep.ui.box.NatureData
import com.example.countingmareep.ui.box.modify.SubSkill
import java.io.IOException
import java.io.InputStream


// Define your data model class
data class PokemonDataModel(
    var name: String,
    var level: Int,
    val pokedexEntry: Int,
    val subSkills: List<SubSkill>,
    val ingredients: List<Ingredient>,
    val nature: NatureData,
    var RP: Int,
    var mainSkillLevel: Int,
    val pokemonID: String
)

class BoxAdapter(
    private var dataList: List<PokemonDataModel>,
    private var mainActivity: MainActivity,
    private var viewModel: ViewModel
) :
    RecyclerView.Adapter<BoxAdapter.ViewHolder>() {

    fun submitList(newList: List<PokemonDataModel>) {
        dataList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = CardLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        val binding = holder.cardBinding
        binding.pokemonNicknameTV.text = item.name
        binding.pokemonLevelTV.text = item.level.toString()
        try {
            val ims: InputStream = mainActivity.assets.open("pokemon/${item.pokedexEntry}.png")
            val d = Drawable.createFromStream(ims, null)
            binding.pokemonImage.setImageDrawable(d)
            ims.close()
        } catch (ex: IOException) {
            return
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(val cardBinding: CardLayoutBinding) :
        RecyclerView.ViewHolder(cardBinding.root) {

        private var currentPosition: Int = RecyclerView.NO_POSITION

        init {
            cardBinding.root.setOnClickListener {
                viewModel.setSelectedBox(dataList[adapterPosition].pokemonID)
                mainActivity.navController.navigate(R.id.navigation_view)
            }
        }
    }
}
