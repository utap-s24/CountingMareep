package com.example.countingmareep.ui.team_builder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.countingmareep.databinding.FragmentTeamBuilderBinding

class TeamBuilderFragment : Fragment() {
    companion object {
        const val TAG = "TeamBuilderFragment"
    }

    private var _binding: FragmentTeamBuilderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(TeamBuilderViewModel::class.java)

        _binding = FragmentTeamBuilderBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textTeamBuilder
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}