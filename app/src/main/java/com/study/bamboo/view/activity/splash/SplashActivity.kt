package com.study.bamboo.view.activity.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.study.bamboo.R
import com.study.bamboo.base.BaseActivity
import com.study.bamboo.utils.Variable
import com.study.bamboo.view.activity.signin.SignInActivity
import com.study.bamboo.view.activity.postcreate.PostCreateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private val splashViewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar!!.hide()
        observeViewModel()
        callViewModelApi()
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
    }

    private fun callViewModelApi(){
        splashViewModel.callGetVerifyAPI()
    }
}