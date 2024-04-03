import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countingmareep.R
import com.example.countingmareep.databinding.CardLayoutBinding
import com.example.countingmareep.ui.box.Ingredient
import com.example.countingmareep.ui.box.Nature
import com.example.countingmareep.ui.box.Skill

// Define your data model class
data class PokemonDataModel(
    val name: String,
    val level: Int,
    val pokedexEntry: Int,
    val subSkills: List<Skill>,
    val ingredients: List<Ingredient>,
    val nature: Nature,
    val RP: Int
)

class BoxAdapter(private var dataList: List<PokemonDataModel>) :
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
        // TODO: Image
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(val cardBinding: CardLayoutBinding) :
        RecyclerView.ViewHolder(cardBinding.root) {

        private var currentPosition: Int = RecyclerView.NO_POSITION

        init {
            cardBinding.root.setOnClickListener {
                currentPosition.takeIf { it != RecyclerView.NO_POSITION }?.let { position ->
                    // Click Event Handle
    //                val position = getPos(this)
    //                // Toggle Favorite
    //                val local = viewModel.getFavoriteAt(position)
    //                local.let {
    //
    //              }
                }
            }
        }
    }
}
