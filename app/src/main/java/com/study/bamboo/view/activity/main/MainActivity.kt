package com.study.bamboo.view.activity.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.study.bamboo.R
import com.study.bamboo.base.BaseActivity
import com.study.bamboo.databinding.ActivityMainBinding
import com.study.bamboo.view.activity.signin.SignInActivity
import com.study.bamboo.view.activity.signin.SignInActivity.Companion.getPostCountResponse
import dagger.hilt.android.AndroidEntryPoint

// ViewBinding
@AndroidEntryPoint
class MainActivity : BaseActivity() {
    lateinit var navi: BottomNavigationView
    private lateinit var navController: NavController
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainViewModel by viewModels<MainViewModel>()


    override fun onStart() {
        super.onStart()
        observeViewModel()
        mainViewModel.callGetPost(20, "ACCEPTED")
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

    private fun observeViewModel() {
        mainViewModel.getPostResponse.observe(this, Observer {

        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
}