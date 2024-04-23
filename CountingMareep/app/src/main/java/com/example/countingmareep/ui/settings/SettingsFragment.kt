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
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.io.InputStream

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
                        viewModel.setRank(it.rank)
                        viewModel.setBefriended(it.befriended)
                        viewModel.setHoursSlept(it.hoursSlept)
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
        val rank = binding.settingsRankTV.text.toString().toInt()
        val befriended = binding.settingsBefriendedTV.text.toString().toInt()
        val hoursSlept = binding.settingsSleptTV.text.toString().toInt()
        val birthday = binding.settingsBirthdayTV.text.toString().toLong()

        val client = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .baseUrl(MainActivity.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val updateCall = service.updateUserData(viewModel.getSession(), rank, befriended, hoursSlept)

        updateCall.enqueue(object : Callback<UserDataResponse> {
            override fun onResponse(call: Call<UserDataResponse>, response: Response<UserDataResponse>) {
                // Create a log message to print the response body and status code and check if the response is successful in kotlin using Log.d
                Log.d("Response", "Response body: ${response.body()}")
                Log.d("Response", "Response code: ${response.code()}")
                Log.d("Response", "Response successful: ${response.isSuccessful}")        
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
        binding.settingsRankTV.setText(viewModel.getRank().toString())
        binding.settingsBefriendedTV.setText(viewModel.getBefriended().toString())
        binding.settingsSleptTV.setText(viewModel.getHoursSlept().toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
