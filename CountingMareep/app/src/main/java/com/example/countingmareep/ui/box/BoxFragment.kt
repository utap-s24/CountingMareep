package com.example.countingmareep.ui.box

import BoxAdapter
import MarginItemDecoration
import PokemonDataModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.countingmareep.MainActivity
import com.example.countingmareep.R
import com.example.countingmareep.ViewModel
import com.example.countingmareep.databinding.FragmentBoxBinding

class BoxFragment : Fragment() {
    companion object {
        const val TAG = "BoxFragment"
    }

    private var _binding: FragmentBoxBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: ViewModel by activityViewModels()
    private lateinit var sortItems: Array<String>
    private var isDescending = true
    private lateinit var dataList: List<PokemonDataModel>
    private lateinit var adapter: BoxAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val boxViewModel =
            ViewModelProvider(this).get(BoxViewModel::class.java)

        _binding = FragmentBoxBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // TODO: ACTUAL DATA
        sortItems = resources.getStringArray(R.array.spinner_entries)
        // UI Shenanigans
        val mainActivity = activity as MainActivity
        val recyclerView = binding.boxRV
        recyclerView.layoutManager = GridLayoutManager(mainActivity, 4)
        recyclerView.addItemDecoration(MarginItemDecoration(10, 4, GridLayoutManager.VERTICAL))
        // Get Data
        Log.d("Box Data List", viewModel.getDataList().toString())
        dataList = viewModel.getDataList().sortedBy { it.RP }
        // Create Adapter
        adapter = BoxAdapter(dataList, mainActivity, viewModel)
        recyclerView.adapter = adapter

        binding.ascDescButton.setOnClickListener {
            if (isDescending) {
                binding.ascDescButton.text = "▲"
                isDescending = false
            } else {
                binding.ascDescButton.text = "▼"
                isDescending = true
            }
            sortItems(binding.sortSpinner.selectedItemPosition)
        }

        binding.sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                sortItems(position)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {

            }
        }

        binding.createButton.setOnClickListener {
            mainActivity.navController.navigate(R.id.navigation_modify)
        }

        return root
    }

    private fun sortItems(position: Int) {
        dataList = when (position) {
            0 -> if (isDescending) dataList.sortedByDescending { it.RP } else dataList.sortedBy { it.RP } // Sort by RP
            1 -> if (isDescending) dataList.sortedByDescending { it.level } else dataList.sortedBy { it.level } // Sort by level
            2 -> if (isDescending) dataList.sortedByDescending { it.name } else dataList.sortedBy { it.name } // Sort by name
            3 -> if (isDescending) dataList.sortedByDescending { it.pokedexEntry } else dataList.sortedBy { it.pokedexEntry } // Sort by pokedexEntry
            else -> dataList
        }
        adapter.submitList(dataList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}