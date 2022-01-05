package com.study.bamboo.view.activity.splash

import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.study.bamboo.R
import com.study.bamboo.base.BaseActivity
import com.study.bamboo.utils.NetworkHandler
import com.study.bamboo.utils.Variable
import com.study.bamboo.view.activity.signin.SignInActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private lateinit var networkHandler: NetworkHandler
    private val splashViewModel by viewModels<SplashViewModel>()
    private lateinit var wifiDialog : WifiDialog
    companion object{
        var deviceSizeX : Int = 0
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar!!.hide()

        networkHandler = NetworkHandler(this)
        when (networkHandler.isNetworkAvailable()) {
            true -> {
                observeViewModel()
                callViewModelApi()
            }
            false -> noUseWifi()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }


    private fun noUseWifi() {
        //다이얼로그
        wifiDialog = WifiDialog()
        wifiDialog.show(supportFragmentManager, "noUseWifiDialog")
        val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        deviceSizeX = size.x
    }


    private fun observeViewModel(){
        splashViewModel.getVerifyResponse.observe(this, Observer {
            if(it != null){
                Variable.VerifyDTO = it
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
            } else{
                callViewModelApi()
            }

        })

        splashViewModel.errorResponse.observe(this, Observer {
            if (it != null){
                Toast.makeText(this,"서버오류 발생",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun callViewModelApi(){
        splashViewModel.callGetVerifyAPI()
    }
}