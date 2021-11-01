package com.study.bamboo.view.activity.signin

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.study.bamboo.R
import com.study.bamboo.databinding.ActivitySignInBinding
import com.study.bamboo.view.activity.main.MainActivity
import com.study.bamboo.base.BaseActivity
import com.study.bamboo.view.activity.splash.SplashActivity.Companion.deviceSizeX
import com.study.bamboo.view.fragment.admin.dialog.AcceptDialog
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception


@AndroidEntryPoint
class SignInActivity : BaseActivity() {

    private val binding by binding<ActivitySignInBinding>(R.layout.activity_sign_in)
    private val loginDialog = LoginDialog()
    private val signInViewModel: SignInViewModel by viewModels()

    companion object{
        var getPostCountResponse = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        binding.activity = this
        supportActionBar!!.hide()
        binding.progressBar.visibility = View.GONE
        observeViewModel()
    }


    fun clickUserLogin(view: View) {
       /* binding.progressBar.visibility = View.VISIBLE
        signInViewModel.callGetCount()*/

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun clickAdminLogin(view: View) {
        //다이얼로그

        try {
            if (!loginDialog.isAdded) {
                loginDialog.show(supportFragmentManager, "AdminLoginDialog")

            }
            val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            deviceSizeX = size.x
            initBundle(deviceSizeX)
        }catch (e : Exception){
            Log.d("로그","로그인 다이얼로그 오류 : $e")
        }

    }

    private fun initBundle(display_size_x: Int) {
        //bundleOf를 이용한 데이터 전달
        val bundle = bundleOf("displaySizeX" to display_size_x)
        Log.d("로그", "display_size_x : $display_size_x")
        loginDialog.arguments = bundle
    }

    private fun observeViewModel() {
        //post 게시물을 받아왔을때 MainActivity로 넘어가기
        signInViewModel.getPostResponse.observe(this, Observer {
            if(it != null){
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("count",it.size)
                startActivity(intent)
            }
        })



        signInViewModel.adminLoginResponse.observe(this, Observer {
            Log.d("로그", "어드민 로그인 API : ${it}")
            if (it == "null") {
                Toast.makeText(this, "비밀번호가 틀렸습니다", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "안녕하세요 관리자님!", Toast.LENGTH_SHORT).show()
            }
        })

    /*    signInViewModel.getCountResponse.observe(this, Observer {
            Log.d("로그","로그인 화면 count : $it")
            getPostCountResponse = it
            binding.progressBar.visibility = View.GONE
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        })*/


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}