package com.study.presentation.view

import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.study.base.base.base.BaseActivity
import com.study.presentation.R
import com.study.presentation.databinding.ActivityUserBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityUserBinding>(R.layout.activity_user) {
    private lateinit var navi: BottomNavigationView
    private val navController: NavController by lazy {
        supportFragmentManager.findFragmentById(R.id.userNavHostFragment)!!.findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navi = binding.bottomNavigationView
        navController.let {
            navi.setupWithNavController(navController)
        }
        setSupportActionBar(binding.toolbar)
        //앱 바 구성성
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.userMainFragment,
                R.id.userRulesFragment,
                R.id.userMoreSeeFragment,
            )
        )
        Log.d("mainActivity", "onCreate: ${navController?.currentDestination?.id}")
        when (navController?.currentDestination?.id) {
            R.id.postCreateFragment -> {
                Log.d("TAG", "onCreate:dd ")
                findNavController(R.id.postCreateFragment).popBackStack()
            }

        }

        setupActionBarWithNavController(navController!!, appBarConfiguration)

    }

    override fun onBackPressed() {

        if (!navController.navigateUp())
            super.onBackPressed()


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}
