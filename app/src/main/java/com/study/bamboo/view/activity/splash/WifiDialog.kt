package com.study.bamboo.view.activity.splash

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.asLiveData
import com.study.bamboo.databinding.ActivityLoginDialogBinding
import com.study.bamboo.databinding.ActivityNoWifiDialogBinding
import com.study.bamboo.view.activity.signin.LoginDialog

class WifiDialog : DialogFragment() {

    private var _binding: ActivityNoWifiDialogBinding? = null
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        initDialog()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityNoWifiDialogBinding.inflate(inflater, container, false)


//        //다이얼로그 백그라운드 삭제 -> 모서리 둥글게
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)


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

}