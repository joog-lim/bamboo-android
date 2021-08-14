package com.study.bamboo.model.retrofit

import com.study.bamboo.model.dto.AdminSignInDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AdminLoginAPI {
    @Headers("accept: application/json",
        "content-type: application/json")
    @POST("auth")
    fun transferAdminLogin(@Body password: HashMap<String,String>): Call<AdminSignInDTO>?
}