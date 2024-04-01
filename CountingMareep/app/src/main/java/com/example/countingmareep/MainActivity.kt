package com.example.countingmareep

import android.content.Context
import android.content.SharedPreferences
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
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ViewModel by viewModels()
    private lateinit var navigationSet: Set<Int>
    private lateinit var navController: NavController
    // Shared Prefs
    private val PREFS_FILENAME = "my_app_settings"
    private val PREFS_THEME = "MY_THEME"
    private lateinit var appSettings: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        appSettings = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        val currentTheme = appSettings.getInt(PREFS_THEME, AppCompatDelegate.MODE_NIGHT_YES)
        Log.d("CURRENT THEME", currentTheme.toString())
        AppCompatDelegate.setDefaultNightMode(currentTheme)
        
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

        loginRedirect()

    }

    fun loginRedirect(): Unit {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // Don't display the back arrow for these
            if (destination.id == R.id.navigation_splash || navigationSet.contains(destination.id)) {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportActionBar?.setDisplayShowHomeEnabled(false)
            }
        }
        navController.navigate(R.id.navigation_splash)
        binding.navView.visibility = View.GONE
    }

    fun loggedInRedirect(): Unit {
        // Don't get back to splash page
        while(navController.popBackStack()) {}
        navController.navigate(R.id.navigation_box)
        binding.navView.visibility = View.VISIBLE
    }

    fun createAccountRedirect(): Unit {
        navController.navigate(R.id.navigation_create)
    }

    fun saveTheme(isDark: Boolean): Unit {
        val theme = if(isDark) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }
        val editor = appSettings.edit()
        editor.putInt(PREFS_THEME, theme)
        editor.apply()

        this.recreate()
    }

    fun setTitle(title: String) {
        supportActionBar?.title = title
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