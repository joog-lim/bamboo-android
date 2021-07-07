package com.study.bamboo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignInViewModel  : ViewModel() {
    val display_size_x get()= _display_size_x
    private val _display_size_x : MutableLiveData<Int> = MutableLiveData<Int>()

    init {
        display_size_x.value=0
    }
}