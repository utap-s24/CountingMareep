package com.example.countingmareep.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.countingmareep.MainActivity
import com.example.countingmareep.databinding.FragmentCreateBinding

class CreateFragment : Fragment() {
    companion object {
        const val TAG = "CreateFragment"
    }

    private var _binding: FragmentCreateBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val mainActivity = activity as MainActivity

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}