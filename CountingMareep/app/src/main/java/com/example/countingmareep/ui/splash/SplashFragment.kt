package com.example.countingmareep.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.countingmareep.MainActivity
import com.example.countingmareep.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    companion object {
        const val TAG = "SplashFragment"
    }

    private var _binding: FragmentSplashBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val mainActivity = activity as MainActivity

        binding.loginButton.setOnClickListener {
            mainActivity.loginAccountRedirect()
        }

        binding.createButton.setOnClickListener {
            mainActivity.createAccountRedirect()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}