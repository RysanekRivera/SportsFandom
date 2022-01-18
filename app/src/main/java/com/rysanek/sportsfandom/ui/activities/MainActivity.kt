package com.rysanek.sportsfandom.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.rysanek.sportsfandom.R
import com.rysanek.sportsfandom.databinding.ActivityMainBinding
import com.rysanek.sportsfandom.databinding.FragmentSearchBinding
import com.rysanek.sportsfandom.domain.utils.fullScreenMode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        fullScreenMode()

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        setupSearchView()

        setContentView(binding.root)
    }

    override fun onNavigateUp(): Boolean {
        return navController.popBackStack() || super.onNavigateUp()
    }

    private fun setupSearchView() {
        binding.searchView.setOnSearchClickListener {
            if (R.id.searchFragment != navController.currentDestination?.id ?: -1) {
                navController.navigate(R.id.searchFragment)
            }
        }

        binding.searchView.setOnCloseListener {
            navController.popBackStack(R.id.searchFragment, true)
            false
        }
    }
}