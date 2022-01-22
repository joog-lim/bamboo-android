package com.example.presentation.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.presentation.utils.DiaryUtil.Companion.DIALOG_RESULT_KEY

fun <T> Fragment.setNavResult(key: String = DIALOG_RESULT_KEY, data: T) {
        findNavController().previousBackStackEntry?.also { stack ->
            stack.savedStateHandle.set(key, data)
        }
    }

fun hideKeyboardFrom(context: Context, view: View) {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0);
}
