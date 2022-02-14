package com.study.presentation.view.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.study.presentation.R
import com.study.presentation.databinding.ActivityAdminBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

// ViewBinding
@AndroidEntryPoint
class AdminActivity : AppCompatActivity() {
    lateinit var navi: BottomNavigationView
    private lateinit var navController: NavController
    private val binding by lazy { ActivityAdminBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        navi = binding.bottomNavigationView
        navController = findNavController(R.id.adminNavHostFragment)
        //앱 바 구성성
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.adminMainFragment,
                R.id.adminRulesFragment,
                R.id.adminMoreSeeFragment,
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navi.setupWithNavController(navController)

    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}
