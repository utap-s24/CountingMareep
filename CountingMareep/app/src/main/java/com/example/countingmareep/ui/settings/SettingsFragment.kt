package com.example.countingmareep.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.countingmareep.MainActivity
import com.example.countingmareep.ViewModel
import com.example.countingmareep.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    companion object {
        const val TAG = "SettingsFragment"
    }

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: ViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val settingsViewModel =
            ViewModelProvider(this).get(SettingsViewModel::class.java)

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.themeToggle.isChecked = viewModel.isDark()
        binding.themeToggle.setOnCheckedChangeListener { _, isChecked ->
            setThemeFromSwitch(isChecked)
        }

        return root
    }

    private fun setThemeFromSwitch(isDarkMode: Boolean) {
        val mainActivity = activity as MainActivity
        mainActivity.saveTheme(isDarkMode)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}