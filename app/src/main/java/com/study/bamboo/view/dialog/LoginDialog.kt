package com.study.bamboo.view.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.study.bamboo.databinding.ActivityLoginDialogBinding
import com.study.bamboo.utils.ViewModel
import com.study.bamboo.utils.ViewModel.signInViewModel
import com.study.bamboo.viewmodel.SignInViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class LoginDialog : DialogFragment() {
    private var _binding: ActivityLoginDialogBinding? = null
    private val binding get() = _binding!!


    override fun onResume() {
        super.onResume()

        //디바이스 크기 확인후 커스텀 다이어로그 팝업 크기 조정
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = ViewModel.signInViewModel.display_size_x.value
        if (deviceWidth != null) {
            params?.width = (deviceWidth * 0.9).toInt()
        }
        dialog?.window?.attributes = params as WindowManager.LayoutParams

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityLoginDialogBinding.inflate(inflater, container, false)
        val view = binding.root



        //다이얼로그 백그라운드 삭제 -> 모서리 둥글게
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)


        //로그인 눌렀을때의 처리
        binding.loginBtn.setOnClickListener {

                loginBtnClick()

        }
        return view
    }

     fun loginBtnClick(){
         Log.d("로그","edittext password : ${binding.passwordEdittext.text.toString()}")
        signInViewModel.callAdminLoginAPI(binding.passwordEdittext.text.toString())

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}