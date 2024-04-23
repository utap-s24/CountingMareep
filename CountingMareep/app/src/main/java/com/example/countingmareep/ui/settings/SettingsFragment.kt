package com.example.countingmareep.ui.settings

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.countingmareep.MainActivity
import com.example.countingmareep.ViewModel
import com.example.countingmareep.databinding.FragmentSettingsBinding
import com.example.countingmareep.network.ApiService
import com.example.countingmareep.network.UserDataResponse
import com.example.countingmareep.network.UserResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import androidx.navigation.fragment.findNavController
import com.example.countingmareep.R


class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val mainActivity = activity as MainActivity

        binding.themeToggle.setOnCheckedChangeListener { _, isChecked ->
            mainActivity.saveTheme(isChecked)
        }

        try {
            val ims: InputStream = mainActivity.assets.open("icons/${viewModel.getIcon()}.png")
            val d = Drawable.createFromStream(ims, null)
            binding.settingsImage.setImageDrawable(d)
            ims.close()
        } catch (ex: IOException) {
        }

        loadUserData()

        binding.saveButton.setOnClickListener {
            saveUserData()
        }

        binding.logoutButton.setOnClickListener {
            logout()
        }

        return root
    }

    private fun loadUserData() {
        val client = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .baseUrl(MainActivity.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val userCall = service.getUserData(viewModel.getSession())

        userCall.enqueue(object : Callback<UserDataResponse> {
            override fun onResponse(call: Call<UserDataResponse>, response: Response<UserDataResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        viewModel.setUserName(it.name.toString()) // Could cause issues
                        viewModel.setRank(it.rank)
                        viewModel.setBefriended(it.befriended)
                        viewModel.setHoursSlept(it.hoursSlept)
                        viewModel.setBirthday(it.birthday)
                        updateUI()
                    }
                } else {
                    Toast.makeText(context, "Failed to load data: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<UserDataResponse>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun saveUserData() {
        val name = binding.settingsName.text.toString()
        val rank = binding.settingsRankTV.text.toString().toInt()
        val befriended = binding.settingsBefriendedTV.text.toString().toInt()
        val hoursSlept = binding.settingsSleptTV.text.toString().toInt()
        val birthday = viewModel.getBirthday()

        val client = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .baseUrl(MainActivity.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val updateCall = service.updateUserData(viewModel.getSession(), name, rank, befriended, hoursSlept, birthday)

        updateCall.enqueue(object : Callback<UserDataResponse> {
            override fun onResponse(call: Call<UserDataResponse>, response: Response<UserDataResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Data updated successfully!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Failed to update data: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<UserDataResponse>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun updateUI() {
        binding.settingsName.text = viewModel.getUserName()
        binding.settingsRankTV.setText(viewModel.getRank().toString())
        binding.settingsBefriendedTV.setText(viewModel.getBefriended().toString())
        binding.settingsSleptTV.setText(viewModel.getHoursSlept().toString())
        binding.settingsBirthdayTV.text = "Birthday: " + fomratDateFromMillis(viewModel.getBirthday())
    }

    private fun fomratDateFromMillis(millis: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val netDate = Date(millis)
        return sdf.format(netDate)
    }

    private fun logout() {
        val sessionID = viewModel.getSession()
        val client = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .baseUrl(MainActivity.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val call = service.logout(sessionID)

        call.enqueue(object : Callback<UserResponse> {
        override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
            Log.d("Logout", "Response: ${response.code()}")
            if (response.isSuccessful) {
                // Assume viewModel has a method to clear the session data
                viewModel.clearSession()
                // Navigate to SplashFragment using the defined action
                findNavController().navigate(R.id.action_settings_to_splash)
                Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed to log out", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onFailure(call: Call<UserResponse>, t: Throwable) {
            Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_LONG).show()
        }
    })
}


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
