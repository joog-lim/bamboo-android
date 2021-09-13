package com.study.bamboo.data.network.user


import com.study.bamboo.data.network.models.admin.*
import retrofit2.Response
import retrofit2.http.*

interface AdminApi {


    //게시물 삭제
    @HTTP(method = "DELETE", path = "post/{id}/delete", hasBody = true)
    suspend fun deletePost(
        @Header("Authorization") Authorization: String,
        @Path("id") arg: String,
        @Body reason: HashMap<String, String>,
    ): Response<DeletePostDto>

    //게시물 상태 수정 가능(수락 상태, 거절 상태 등)
    @POST("post/{id}/setStatus")
    suspend fun patchPost(
        @Header("Authorization") Authorization: String,
        @Path("id") id: String,
        @Body status: HashMap<String, String>,
    ): Response<UpdateStatus>
    // 수락 수정

    @PATCH("post/{id}/modify")
    suspend fun acceptPatchPost(
        @Header("Authorization") Authorization: String,
        @Path("id") id: String,
        @Body bodyMap: Map<String, String>,

    ): Response<AcceptModify>






    @GET("post/AlgorithemPage")
    suspend fun getAcceptPage(
        @Header("Authorization") Authorization:String,
        @Query("page") page:Int,
        @Query("status") status:String
    ): Response<AcceptPost>

    @GET("post/AlgorithemPage")
    suspend fun getPendingPost(
        @Header("Authorization") Authorization: String,
        @Query("page") page:Int,
        @Query("status") status:String
    ): Response<PendingPost>

    @GET("post/AlgorithemPage   ")
    suspend fun getDeletePost(
        @Header("Authorization") Authorization: String,
        @Query("page") page:Int,
        @Query("status") status:String
    ): Response<DeletePost>

    @GET("post/AlgorithemPage")
    suspend fun getRejectPost(
        @Header("Authorization") Authorization: String,
        @Query("page") page:Int,
        @Query("status") status:String
    ): Response<AdminRejectPost>

    //각각의 status 게시물 가져오기
    @GET("post/count")
    suspend fun getCount(
        @Header("Authorization") Authorization: String,
    ): Response<PostCount>

    //관리자로 로그인
    @Headers("accept: application/json", "content-type: application/json")
    @POST("auth")
    suspend fun transferAdminLogin(@Body password: HashMap<String, String>): Response<AdminSignInDTO>?
}