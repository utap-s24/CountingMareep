package com.example.countingmareep.ui.splash

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.countingmareep.MainActivity
import com.example.countingmareep.databinding.FragmentCreateBinding
import com.example.countingmareep.network.ApiService
import com.example.countingmareep.network.UserResponse
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId


class CreateFragment : Fragment() {
    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.createAccountButton.setOnClickListener {
            createAccount()
        }

        return root
    }

    private fun createAccount() {
        val username = binding.usernameInput.text.toString()
        val email = binding.emailInput.text.toString()
        val password = binding.passwordInput.text.toString()
        val birthdayDayStr = binding.birthdayInputDay.text.toString()
        val birthdayMonthStr = binding.birthdayInputMonth.text.toString()
        val birthdayYearStr = binding.birthdayInputYear.text.toString()
        if(birthdayDayStr.isEmpty() || birthdayMonthStr.isEmpty() || birthdayYearStr.isEmpty()) {
            Toast.makeText(activity, "Birthday must be filled in", Toast.LENGTH_SHORT).show()
            return
        }
        val birthdayDay = birthdayDayStr.toInt()
        val birthdayMonth = birthdayMonthStr.toInt()
        val birthdayYear = birthdayYearStr.toInt()
        if(birthdayDay < 1 || birthdayDay > 31 || birthdayMonth < 1 || birthdayMonth > 12 || birthdayYear < 1900 || birthdayYear > 2024) {
            Toast.makeText(activity, "Invalid Birthday", Toast.LENGTH_SHORT).show()
            return
        }

        val birthday = toEpochMilli(LocalDate.of(birthdayYear, birthdayMonth, birthdayDay).atStartOfDay())
        val rank = 1
        val befriended = 0
        val hoursSlept = 0

        val client = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .baseUrl(MainActivity.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val userCall = service.createUser(username, email, password, birthday, rank, befriended, hoursSlept)


        userCall.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.d("CreateFragment", "Response: ${response.code()}")
                if (response.isSuccessful) {
                    Toast.makeText(getActivity(), "Account created successfully!", Toast.LENGTH_LONG).show()
                } else {
                    val gson = Gson()
                    val errorResponse = gson.fromJson(response.errorBody()?.charStream(), UserResponse::class.java)
                    Toast.makeText(getActivity(), "Failed to create account: ${errorResponse.msg}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(getActivity(), "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun toEpochMilli(localDateTime: LocalDateTime): Long {
        return localDateTime.atZone(ZoneId.systemDefault())
            .toInstant().toEpochMilli()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
