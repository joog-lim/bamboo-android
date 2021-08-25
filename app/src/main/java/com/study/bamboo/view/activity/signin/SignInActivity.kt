package com.study.bamboo.view.activity.signin

import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.study.bamboo.R
import com.study.bamboo.databinding.ActivitySignInBinding
import com.study.bamboo.view.activity.main.MainActivity
import com.study.bamboo.view.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : BaseActivity() {
    private val binding by binding<ActivitySignInBinding>(R.layout.activity_sign_in)

    private val signInViewModel: SignInViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        binding.activity = this
        supportActionBar!!.hide()
        binding.progressBar.visibility = View.GONE
        observeViewModel()
    }


    fun clickUserLogin(view: View) {
        binding.progressBar.visibility = View.VISIBLE
        signInViewModel.callGetPost(20, "60b8407473d81a1b4cc591a5", "PENDING")

    }

    fun clickAdminLogin(view: View) {
        val dialog = LoginDialog()
        dialog.show(supportFragmentManager, "AdminLoginDialog")

        val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        signInViewModel.display_size_x.value = size.x
    }

    private fun observeViewModel(){
        //post 게시물을 받아왔을때 MainActivity로 넘어가기
        signInViewModel.getPostResponse.observe(this, Observer {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        })

        signInViewModel.adminLoginResponse.observe(this, Observer {
            Log.d("로그", "어드민 로그인 API : ${it}")
            if (it == "null") {
                Toast.makeText(this, "비밀번호가 틀렸습니다", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "안녕하세요 관리자님!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}