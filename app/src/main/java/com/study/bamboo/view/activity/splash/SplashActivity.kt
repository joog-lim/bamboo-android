package com.study.bamboo.view.activity.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.study.bamboo.R
import com.study.bamboo.utils.ViewModel.postCreateViewModel
import com.study.bamboo.utils.ViewModel.splashViewModel
import com.study.bamboo.view.activity.signin.SignInActivity
import com.study.bamboo.view.activity.postcreate.PostCreateViewModel

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar!!.hide()
        initViewModel()
        observeViewModel()
        callViewModelApi()
    }

    private fun initViewModel(){
        postCreateViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(PostCreateViewModel::class.java)

        splashViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(SplashViewModel::class.java)
    }

    private fun observeViewModel(){
        splashViewModel.getVerifyResponse.observe(this, Observer {
            if(it != null){
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