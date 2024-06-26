package com.toren.producthub.presentation.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.toren.producthub.R
import com.toren.producthub.databinding.ActivityMainBinding
import com.toren.producthub.databinding.NavHeaderMainBinding
import com.toren.producthub.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var header: NavHeaderMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        header = NavHeaderMainBinding.bind(binding.navView.getHeaderView(0))

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_categories, R.id.nav_search,
                R.id.nav_likes, R.id.nav_orders, R.id.nav_profile
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.sign_out -> {
                    viewModel.signOut()
                    observeSignOut()
                    true
                }
                else -> {
                    NavigationUI.onNavDestinationSelected(menuItem, navController)
                    drawerLayout.closeDrawers()
                    true
                }
            }
        }

        observeUserSession()

        FirebaseApp.initializeApp(this)

        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_values)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d("MainActivity", "Config params updated: $updated")
                } else {
                    Log.d("MainActivity", "Fetch failed")
                }
            }

        remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                Log.e("onUpdate", "Updated keys: " + configUpdate.updatedKeys)

                if (configUpdate.updatedKeys.contains("backgroundColor")) {
                    remoteConfig.activate().addOnCompleteListener {
                        if (it.isSuccessful) {
                            val color = Firebase.remoteConfig.getString("backgroundColor")
                            binding.drawerLayout.setBackgroundColor(color.toColorInt())
                            Log.d("Pull Color", color)
                        }
                    }
                }
            }

            override fun onError(error: FirebaseRemoteConfigException) {
                Log.w("onError", "Config update error with code: " + error.code, error)
            }
        })

    }

    private fun observeSignOut() {
        viewModel.signOutResult.observe(this) {
            if (it.data == true) {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
                finish()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun observeUserSession() {
        viewModel.getUserResult.observe(this) {
            it?.let {
                header.headerUsernameTxt.text = it.username
                Glide
                    .with(this)
                    .load(it.image)
                    .into(header.imageView)
            }
        }
    }

}