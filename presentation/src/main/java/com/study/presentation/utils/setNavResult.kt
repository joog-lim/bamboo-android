package com.study.presentation.utils

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.study.presentation.utils.DiaryUtil.Companion.DIALOG_RESULT_KEY

fun <T> Fragment.setNavResult(key: String = DIALOG_RESULT_KEY, data: T) {
        findNavController().previousBackStackEntry?.also { stack ->
            stack.savedStateHandle.set(key, data)
        }
    }

