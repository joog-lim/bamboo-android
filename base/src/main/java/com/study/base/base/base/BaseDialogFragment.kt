package com.study.base.base.base

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment


open class BaseDialogFragment<T : ViewDataBinding>(@LayoutRes val layoutRes: Int) :
    DialogFragment() {


    lateinit var binding: T

    private var deviceSizeX = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialogCorner()
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        binding.onCreateView()
        binding.onViewCreated()
        findDialogSize()

        return binding.root
    }

    open fun T.onCreateView() = Unit
    open fun T.onViewCreated() = Unit

    private fun dialogCorner() {
        //다이얼로그 백그라운드 삭제 -> 모서리 둥글게
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
    }

    override fun onResume() {
        super.onResume()
        aApplySize()
    }

    private fun aApplySize() {
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = deviceSizeX
        params?.width = (deviceWidth * 0.9).toInt()


        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    protected fun hideKeyboardFrom(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0);
    }

    private fun findDialogSize() {
        //다이얼로그
        val windowManager =
            this@BaseDialogFragment.activity?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        deviceSizeX = size.x
    }
}