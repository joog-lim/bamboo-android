package com.study.presentation.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.fragment.findNavController
import com.study.presentation.utils.DiaryUtil.Companion.DIALOG_RESULT_KEY

fun <T> Fragment.setNavResult(key: String = DIALOG_RESULT_KEY, data: T) {
    findNavController().previousBackStackEntry?.also { stack ->
        stack.savedStateHandle.set(key, data)
    }
}

fun Fragment.hideKeyBoard() {
    val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
}

inline fun <T> Fragment.getDialogNavResult(
    @IdRes navId: Int,
    key: String = DIALOG_RESULT_KEY,
    crossinline onChanged: (T?) -> Unit
) {
    val backStackEntry = findNavController().getBackStackEntry(navId)
    val observer = LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_RESUME && backStackEntry.savedStateHandle.contains(key)) {
            val result = backStackEntry.savedStateHandle.get<T>(key)
            onChanged(result)
            backStackEntry.savedStateHandle.remove<T>(key)
        }
    }
    backStackEntry.lifecycle.addObserver(observer)

    viewLifecycleOwner.lifecycle.addObserver(
        LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                backStackEntry.lifecycle.removeObserver(observer)
            }
        }
    )
}