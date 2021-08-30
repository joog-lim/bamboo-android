package com.study.bamboo.view.activity.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.study.bamboo.data.network.models.user.GetVerifyDTO

class SplashViewModel : ViewModel() {

    //verify 값 받아오기
    val getVerifyResponse get() = _getVerifyResponse
    private val _getVerifyResponse: MutableLiveData<GetVerifyDTO> = MutableLiveData<GetVerifyDTO>()


    //verify 호출로 id와 질문 가져오기
    fun callGetVerifyAPI(){
//        val retService = RetrofitClient().getService().create(GetVerifyAPI::class.java)
//        retService.getVerify().enqueue(object : Callback<GetVerifyDTO> {
//            override fun onResponse(call: Call<GetVerifyDTO>, response: Response<GetVerifyDTO>) {
//                Log.d("로그","Verify 값 : ${response.body()?.id}")
//                if(response.body()?.id != null){
//                    _getVerifyResponse.value = response.body()
//                }
//            }
//
//            override fun onFailure(call: Call<GetVerifyDTO>, t: Throwable) {
//
//            }
//
//        })
    }
}