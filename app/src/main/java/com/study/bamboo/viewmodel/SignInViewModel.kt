package com.study.bamboo.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.study.bamboo.model.dto.AdminSignInDTO
import com.study.bamboo.model.retrofit.AdminLoginAPI
import com.study.bamboo.model.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInViewModel : ViewModel() {
    val display_size_x get() = _display_size_x
    private val _display_size_x: MutableLiveData<Int> = MutableLiveData<Int>()


    val adminLoginResponse get() = _adminLoginResponse
    private val _adminLoginResponse: MutableLiveData<String> = MutableLiveData<String>()

    init {
        display_size_x.value = 0
    }

    fun callAdminLoginAPI(password: String) {


        val retService = RetrofitClient().getService().create(AdminLoginAPI::class.java)



        val passwordRequest = HashMap<String,String>()
        passwordRequest.put("password",password)

        retService.transferAdminLogin(passwordRequest)?.enqueue(object : Callback<AdminSignInDTO> {
            override fun onResponse(
                call: Call<AdminSignInDTO>,
                response: Response<AdminSignInDTO>
            ) {
                _adminLoginResponse.value = response.body()?.token.toString()
            }

            override fun onFailure(call: Call<AdminSignInDTO>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }

}