package com.example.countingmareep.ui.splash

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.countingmareep.MainActivity
import com.example.countingmareep.ViewModel
import com.example.countingmareep.databinding.FragmentLoginBinding
import com.example.countingmareep.network.ApiService
import com.example.countingmareep.network.UserResponse
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.loginButton.setOnClickListener {
            performLogin()
        }
        val mainActivity = activity as MainActivity
        binding.usernameInput.setText(mainActivity.getUser())
        binding.passwordInput.setText(mainActivity.getPass())

        return root
    }

    private fun performLogin() {
        val username = binding.usernameInput.text.toString()
        val password = binding.passwordInput.text.toString()

        val client = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://countingmareep.onrender.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val loginCall = service.login(username, password)

        loginCall.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val mainActivity = activity as MainActivity
                    if (response.body()?.sessionID != null) {
                        viewModel.setSession(response.body()?.sessionID!!)
                        mainActivity.saveUserPass(username, password)
                        Toast.makeText(getActivity(), "Login successful!", Toast.LENGTH_LONG).show()
                    }
                    mainActivity.loggedInRedirect()
                } else {
                    val gson = Gson()
                    val errorResponse = gson.fromJson(response.errorBody()?.charStream(), UserResponse::class.java)
                    Toast.makeText(
                        getActivity(),
                        "Login failed: ${errorResponse.msg}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(getActivity(), "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
