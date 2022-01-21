package com.example.presentation.view

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.base.base.base.BaseFragment
import com.example.presentation.R
import com.example.presentation.databinding.FragmentSignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

//todo
// base 모듈 만들어서 login, splash  빼고 옮겨야함
@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {
    private lateinit var getGoogleLogin: ActivityResultLauncher<Intent>
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private val viewModel: AuthViewModel by viewModels()

    override fun FragmentSignInBinding.onCreateView() {
        binding.fragment = this@SignInFragment
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(getString(R.string.client_id))
            .build().let {
                mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), it)
            }
        getGoogleLogin =

            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val intent: Intent = result.data!!
                    val task: Task<GoogleSignInAccount> =
                        GoogleSignIn.getSignedInAccountFromIntent(intent)
                    try {
                        val account = task.getResult(ApiException::class.java)
                        viewModel.postLogin(account.idToken!!)
                    } catch (e: ApiException) {
                        throw e
                    }

                } else {
                    Toast.makeText(
                        requireContext(),
                        "로그인 실패 에러코드 ${result.resultCode}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    override fun FragmentSignInBinding.onViewCreated() {

        with(viewModel) {
            isLoading.observe(viewLifecycleOwner) {
                if (it) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE

                }
            }
            isLoginResult.observe(viewLifecycleOwner) {
                if (it?.isAdmin == true) {
                    startActivity(Intent(requireContext(), MainActivity::class.java))
//                    saveToken(it.accessToken)
                } else {
                    startActivity(Intent(requireContext(), AdminActivity::class.java))
                }
            }
            isFailure.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }
//   private fun saveToken(token:String)=lifecycleScope.launch { dataStore.saveToken(token) }

    fun startGoogleLogin() {
        binding.loginBtn.setOnClickListener {
            getGoogleLogin.launch(mGoogleSignInClient!!.signInIntent)
        }
    }

}