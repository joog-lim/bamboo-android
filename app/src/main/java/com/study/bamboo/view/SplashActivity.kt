package com.study.bamboo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.study.bamboo.R
import com.study.bamboo.utils.ViewModel
import com.study.bamboo.utils.ViewModel.postCreateViewModel
import com.study.bamboo.utils.ViewModel.signInViewModel
import com.study.bamboo.utils.ViewModel.splashViewModel
import com.study.bamboo.viewmodel.PostCreateViewModel
import com.study.bamboo.viewmodel.SignInViewModel
import com.study.bamboo.viewmodel.SplashViewModel

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        signInViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(SignInViewModel::class.java)

        postCreateViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(PostCreateViewModel::class.java)

        splashViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(SplashViewModel::class.java)

        splashViewModel.callGetVerifyAPI()


        splashViewModel.getVerifyResponse.observe(this, Observer {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        })
    }
}