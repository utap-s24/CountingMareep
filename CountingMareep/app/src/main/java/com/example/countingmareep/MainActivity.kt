package com.example.countingmareep

import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.countingmareep.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ViewModel by viewModels()
    private lateinit var navigationSet: Set<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
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
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(navigationSet)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        loginRedirect()
    }

    fun loginRedirect(): Unit {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.navigation_splash || navigationSet.contains(destination.id)) {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportActionBar?.setDisplayShowHomeEnabled(false)
            }
        }
        navController.navigate(R.id.navigation_splash)
        binding.navView.visibility = View.GONE
    }

    fun loggedInRedirect(): Unit {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navController.navigate(R.id.navigation_box)
        binding.navView.visibility = View.VISIBLE
    }

    fun setTitle(title: String) {
        supportActionBar?.title = title
    }
}