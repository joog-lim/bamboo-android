package com.study.bamboo.view.fragment.user.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.study.bamboo.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {
     fun postEmoji(token: String) = viewModelScope.launch {
        try {
            repository.postEmoji(token).apply {
                if (this.isSuccessful) {
                    Log.d("TAG", "postEmoji: 성공")
                }

            }
        } catch (e: Exception) {
            Log.d("TAG", "UserViewModel - postEmoji() : $e")
        }
    }

    fun deleteEmoji(token: String) = viewModelScope.launch {
        try {
            repository.deleteEmoji(token).apply {
                if (this.isSuccessful) {
                    Log.d("TAG", "postEmoji: 성공")
                }

            }
        } catch (e: Exception) {
            Log.d("TAG", "UserViewModel - postEmoji() : $e")
        }
    }

}