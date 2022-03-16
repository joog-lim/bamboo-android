package com.study.presentation.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.study.presentation.utils.DiaryUtil.Companion.DIALOG_RESULT_KEY

fun <T> Fragment.setNavResult(key: String = DIALOG_RESULT_KEY, data: T) {
        findNavController().previousBackStackEntry?.also { stack ->
            stack.savedStateHandle.set(key, data)
        }
    }

fun Fragment.hideKeyBoard(){
    val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
}