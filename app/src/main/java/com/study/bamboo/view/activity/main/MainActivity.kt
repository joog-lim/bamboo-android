package com.study.bamboo.view.activity.main

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.study.bamboo.R
import com.study.bamboo.databinding.ActivityMainBinding
import com.study.bamboo.view.fragment.user.UserMainFragment

// ViewBinding
class MainActivity : AppCompatActivity() {
    lateinit var navi: BottomNavigationView
    private lateinit var navController: NavController
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onStart() {
        super.onStart()
        observeViewModel()
        mainViewModel.callGetPost(20, "60b8407473d81a1b4cc591a5", "PENDING")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        navi = binding.bottomNavigationView
        supportActionBar!!.hide()
        navController = findNavController(R.id.navHostFragment)
        //앱 바 구성성
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.userMainFragment,
                R.id.userRulesFragment,
                R.id.userMoreSeeFragment,
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navi.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun observeViewModel(){
        mainViewModel.getPostResponse.observe(this, Observer {

        })
    }


}