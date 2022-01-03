package com.example.data.network.user


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