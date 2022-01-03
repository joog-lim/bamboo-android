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
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.study.bamboo.R
import com.study.bamboo.databinding.ActivitySignInBinding
import com.study.bamboo.base.BaseActivity
import com.study.bamboo.view.activity.splash.SplashActivity.Companion.deviceSizeX
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

import com.google.android.gms.auth.api.signin.GoogleSignIn

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.study.bamboo.view.activity.main.MainActivity
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SignInActivity : BaseActivity() {

    private val binding by binding<ActivitySignInBinding>(R.layout.activity_sign_in)
    private val loginDialog = LoginDialog()
    private val signInViewModel: SignInViewModel by viewModels()

    var mGoogleSignInClient: GoogleSignInClient? = null
    private val RC_SIGN_IN = 123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        binding.activity = this
        supportActionBar!!.hide()
        binding.progressBar.visibility = View.GONE
        observeViewModel()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail() // email addresses도 요청함
            .requestIdToken(getString(R.string.client_id))
            .build()

        // 위에서 만든 GoogleSignInOptions을 사용해 GoogleSignInClient 객체를 만듬

        // 위에서 만든 GoogleSignInOptions을 사용해 GoogleSignInClient 객체를 만듬
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        // 기존에 로그인 했던 계정을 확인한다.

        // 기존에 로그인 했던 계정을 확인한다.


        // 로그인 되있는 경우 (토큰으로 로그인 처리)

        // 로그인 되있는 경우 (토큰으로 로그인 처리)

        getToken()

    }

    override fun onStart() {
        super.onStart()
        val gsa = GoogleSignIn.getLastSignedInAccount(this)
        if (gsa != null && gsa.id != null) {
            postLogin(gsa.idToken)

        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val acct = completedTask.getResult(ApiException::class.java)
            if (acct != null) {

                postLogin(acct.idToken)
                Log.d("TAG", "handleSignInResult: ${acct.idToken}")

            }
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("TAG", "signInResult:failed code=" + e.statusCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    fun clickUserLogin(view: View) {
        /* binding.progressBar.visibility = View.VISIBLE
         signInViewModel.callGetCount()*/

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
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
        } catch (e: Exception) {
            Log.d("로그", "로그인 다이얼로그 오류 : $e")
        }
    }

    private fun getToken() {
        signInViewModel.token.observe(this, {

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(token, it)
            startActivity(intent)
            finish()


        })

    }


    // token 이 제대로 됐는지 test
    private fun postLogin(token: String) {

        lifecycleScope.launch {
            Log.d("TAG", "postLogin: $token")
            signInViewModel.postLogin(token)
        }
    }

    fun clickUserGoogleLogin(view: View) {
        //유저가 구글 로그인을 할때
        signIn()
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
            if (it != null) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("count", it.size)
                startActivity(intent)
                finish()
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

    companion object {
        const val token = ""
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}