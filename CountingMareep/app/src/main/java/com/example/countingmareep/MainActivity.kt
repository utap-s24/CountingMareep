package com.example.countingmareep

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.countingmareep.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    companion object {
//        val BASE_URL = "http://192.168.1.244:3030/"
        val BASE_URL = "https://countingmareep.onrender.com/"
    }
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ViewModel by viewModels()
    private lateinit var navigationSet: Set<Int>
    lateinit var navController: NavController

    // Shared Prefs
    private val PREFS_FILENAME = "my_app_settings"
    private val PREFS_THEME = "MY_THEME"
    private val PREFS_NAV = "MY_NAV"
    private val PREFS_NAME = "MY_USERNAME"
    private val PREFS_PASS = "MY_PASSWORD"
    private lateinit var appSettings: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        appSettings = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        val currentTheme = appSettings.getBoolean(PREFS_THEME, true)
        Log.d("CURRENT THEME", currentTheme.toString())
        if (currentTheme) {
            setTheme(R.style.Theme_CountingMareep_DARK)
        } else {
            setTheme(R.style.Theme_CountingMareep_LIGHT)
        }
        viewModel.setTheme(currentTheme)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigationSet = setOf(
            R.id.navigation_box,
            R.id.navigation_team_builder,
            R.id.navigation_search,
            R.id.navigation_settings
        )

        val navView: BottomNavigationView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(navigationSet)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val navDest = appSettings.getInt(PREFS_NAV, -1)
        if (navDest == -1) {
            loginRedirect()
        } else {
            navController.navigate(navDest)
            resetNavPref()
        }
    }

    fun loginRedirect(): Unit {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // Don't display the back arrow for these
            if (destination.id == R.id.navigation_splash
                || destination.id == R.id.navigation_modify
                || navigationSet.contains(destination.id)
            ) {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportActionBar?.setDisplayShowHomeEnabled(false)
            }
        }
        navController.navigate(R.id.navigation_splash)
        binding.navView.visibility = View.GONE
    }

    fun loggedInRedirect(): Unit {
        // Don't get back to splash page
        while (navController.popBackStack()) {
        }
        navController.navigate(R.id.navigation_box)
        binding.navView.visibility = View.VISIBLE
    }

    fun loginAccountRedirect(): Unit {
        navController.navigate(R.id.navigation_login)
    }

    fun createAccountRedirect(): Unit {
        navController.navigate(R.id.navigation_create)
    }

    fun saveTheme(isDark: Boolean): Unit {
        val currentDest = navController.currentDestination
        val currentNav = if (currentDest != null) {
            currentDest.id
        } else {
            R.id.navigation_box
        }

        val editor = appSettings.edit()
        editor.putBoolean(PREFS_THEME, isDark)
        editor.putInt(PREFS_NAV, currentNav)
        editor.apply()
        this.recreate()
    }

    fun resetNavPref(): Unit {
        val editor = appSettings.edit()
        editor.putInt(PREFS_NAV, -1)
        editor.apply()
    }

    fun setTitle(title: String) {
        supportActionBar?.title = title
    }

    fun saveUserPass(username: String, password: String) {
        val editor = appSettings.edit()
        editor.putString(PREFS_NAME, username)
        editor.putString(PREFS_PASS, password)
        editor.apply()
    }

    fun getUser(): String {
        val name = appSettings.getString(PREFS_NAME, "")
        if(name == null) {
            return ""
        } else {
            return name
        }
    }

    fun getPass(): String {
        val pass = appSettings.getString(PREFS_PASS, "")
        if(pass == null) {
            return ""
        } else {
            return pass
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle up button click
        Log.d("Menu Select ID", item.itemId.toString())
        if (item.itemId == android.R.id.home) {
            // Perform your desired action, such as navigating up or finishing the activity
            val navController = findNavController(R.id.nav_host_fragment_activity_main)
            return navController.popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }
}