package com.study.bamboo.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.study.bamboo.R
import com.study.bamboo.databinding.ActivitySignInBinding
import com.study.bamboo.view.admin.AdminActivity
import com.study.bamboo.view.admin.AuthViewModel
import com.study.base.base.base.BaseActivity
import com.study.data.utils.DataStoreManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SignInActivity : BaseActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {
    @Inject
    lateinit var dataStore: DataStoreManager

    private var mGoogleSignInClient: GoogleSignInClient? = null
    private val getGoogleLogin =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                try {

                    val intent: Intent = result.data!!
                    val task: Task<GoogleSignInAccount> =
                        GoogleSignIn.getSignedInAccountFromIntent(intent)
                    val account = task.getResult(ApiException::class.java)
                    viewModel.postLogin(account?.idToken.toString())
                } catch (e: ApiException) {
                    Log.d("TAG", "onCreate: ${e.message}")
                    throw e
                }
            } else {
                if (result.resultCode == 0)
                    Toast.makeText(this, "학교 이메일로 로그인 해주세요.", Toast.LENGTH_SHORT).show()
                else
                    Log.d("TAG", "${ (result.resultCode)}: ")


            }


        }
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.fragment = this@SignInActivity

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(getString(R.string.client_id))
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this@SignInActivity, gso)



        with(viewModel) {
            isLoading.observe(this@SignInActivity) {
                if (it) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE

                }
            }
            isLoginResult.observe(this@SignInActivity) {
                Log.d("TAG", "onCreate: ${it}")
                saveToken(it?.accessToken.toString())
                if (it?.isAdmin == true) {
                    startActivity(Intent(this@SignInActivity, AdminActivity::class.java))
                } else {
                    startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                }
            }
            isFailure.observe(this@SignInActivity) {
                Log.d("TAG", "onCreate: ${it}")
                Toast.makeText(this@SignInActivity, it.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun saveToken(token: String) = lifecycleScope.launch { dataStore.saveToken(token) }
    fun guestOnClick() {
        lifecycleScope.launch {
            dataStore.deleteToken(dataStore.getToken())
        }
        startActivity(Intent(this, MainActivity::class.java))
    }


    fun startGoogleLogin() {
        lifecycleScope.launch {
            getGoogleLogin.launch(mGoogleSignInClient?.signInIntent)
            delay(3000)
        }
    }

}