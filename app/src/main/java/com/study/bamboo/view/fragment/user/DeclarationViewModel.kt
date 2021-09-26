package com.study.bamboo.view.fragment.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.study.bamboo.data.network.models.user.report.ReportRequest
import com.study.bamboo.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeclarationViewModel @Inject constructor(
    val repository: UserRepository
): ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    fun setMessage(message : String){
        _message.value = message
    }

    fun report(id : String?, body : ReportRequest) = viewModelScope.launch {
        if (id != null) {
            repository.report(id, body).let { response ->
                if (response.isSuccessful){
                    _message.value = "success"
                }else{
                    _message.value ="fail"
                }
            }
        }
    }
}