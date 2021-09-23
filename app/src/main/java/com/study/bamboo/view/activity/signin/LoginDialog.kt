package com.study.bamboo.view.activity.signin

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.study.bamboo.databinding.ActivityLoginDialogBinding
import com.study.bamboo.view.activity.main.AdminActivity
import com.study.bamboo.view.fragment.admin.AdminViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginDialog : DialogFragment() {
    private var _binding: ActivityLoginDialogBinding? = null
    private val binding get() = _binding!!
    var token = ""


    private lateinit var signInViewModel: SignInViewModel
    private lateinit var adminViewModel: AdminViewModel
    private var toast: Toast? = null

    override fun onResume() {
        super.onResume()
        binding.passwordEdittext.setText("")
        initDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adminViewModel = ViewModelProvider(requireActivity()).get(AdminViewModel::class.java)
        signInViewModel = ViewModelProvider(requireActivity()).get(SignInViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityLoginDialogBinding.inflate(inflater, container, false)
        adminViewModel.readToken.asLiveData().observe(viewLifecycleOwner, { value ->
            token = value.token
            Log.d(TAG, "readToken: $token")

        })


//        //다이얼로그 백그라운드 삭제 -> 모서리 둥글게
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)


        //로그인 눌렀을때의 처리
        binding.loginBtn.setOnClickListener {
            loginBtnClick()
        }
        return binding.root
    }

    private fun initDialog() {
        //        //디바이스 크기 확인후 커스텀 다이어로그 팝업 크기 조정
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = arguments?.getInt("displaySizeX")
        if (deviceWidth != null) {
            params?.width = (deviceWidth * 0.9).toInt()
        }

        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }


    @SuppressLint("ShowToast")
    private fun loginBtnClick() {
        // editText가 null이면 button click 못하게 막는다.
        view?.let { it1 -> hideKeyboardFrom(requireContext(), it1) }


        signInViewModel.callAdminLoginAPI(binding.passwordEdittext.text.toString())


        signInViewModel.adminLoginResponse.observe(requireActivity(), {
            adminViewModel.saveToken(it)

            if (it.isNotEmpty()) {
                val intent = Intent(requireContext(), AdminActivity::class.java)
                startActivity(intent)

            }

        })

        signInViewModel.loginSuccess.observe(viewLifecycleOwner) {
            toast = Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT)
            toast?.show()

        }
    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0);
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "LoginDialog"
    }
}