package com.study.bamboo.data.network.user


import com.study.bamboo.data.network.models.Emoji
import com.study.bamboo.data.network.models.user.request.EmojiRequest
import com.study.bamboo.data.network.models.user.GetVerifyDTO
import com.study.bamboo.data.network.models.user.report.ReportRequest
import com.study.bamboo.data.network.models.user.UserGetPostDTO
import com.study.bamboo.data.network.models.user.getcount.GetCount
import com.study.bamboo.data.network.models.user.postcreate.PostCreateRequest
import com.study.bamboo.data.network.models.user.postcreate.PostCreateResponse
import com.study.bamboo.data.network.models.user.report.ReportResponse
import com.study.bamboo.data.network.models.user.response.LoginResponse
import retrofit2.Response
import retrofit2.http.*

interface UserApi {
    @GET("post/AlgorithemPage")
    suspend fun getPost(
        @Query("page") page : Int,
        @Query("status") status : String
    ) : Response<UserGetPostDTO>

    @POST("post/create")
    suspend fun transferPostCreate(
        @Body request : PostCreateRequest
    ): Response<PostCreateResponse>

    @GET("verify")
    suspend fun getVerify() : Response<GetVerifyDTO?>

    @GET("post/count")
    suspend fun getCount() : Response<GetCount>

    @PATCH("post/{id}/report")
    suspend fun report(
        @Path("id") id: String,
        @Body body: ReportRequest,
        ): Response<ReportResponse>


    @POST("account/account/login")
    suspend fun postLogin(
        @Header("Authorization") Authorization: String,
    ): Response<LoginResponse>

    @POST("account/emoji/{emoji}")
    suspend fun postEmoji(
        @Header("Authorization") Authorization: String,
        @Path("emoji") emoji: String,
        @Body body : EmojiRequest?
    ) : Response<Emoji>

    @DELETE("account/emoji/{emoji}")
    suspend fun deleteEmoji(
        @Header("Authorization") Authorization: String,
        @Path("emoji") emoji: String,
        @Body body : EmojiRequest?
    ) : Response<Emoji>

}