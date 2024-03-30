package com.example.countingmareep.ui.box

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.countingmareep.MainActivity
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val boxViewModel =
            ViewModelProvider(this).get(BoxViewModel::class.java)

        _binding = FragmentBoxBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textBox
        boxViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}