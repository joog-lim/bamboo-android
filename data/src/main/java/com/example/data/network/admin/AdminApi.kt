package com.example.data.network.admin

interface AdminApi {


    //게시물 상태 수정 (수락 상태, 거절 상태 등)
    @PATCH("algorithm/{id}/status")
    suspend fun patchPost(
        @Header("Authorization") Authorization: String,
        @Path("id") id: String,
        @Body request: SetStatusRequest,
    ): Response<UpdateStatus>


    // 게시물 수정
    @PATCH("algorithm/{id}")
    suspend fun acceptPatchPost(
        @Header("Authorization") Authorization: String,
        @Path("id") id: String,
        @Body bodyMap: Map<String, String>,

        ): Response<AcceptModify>

    //게시물 삭제
    @HTTP(method = "DELETE", path = "algorithm/{id}", hasBody = true)
    suspend fun deletePost(
        @Header("Authorization") Authorization: String,
        @Path("id") arg: String,
        @Body reason: HashMap<String, String>,
    ): Response<DeletePostDto>

    // 게시물 페이지로 조히
    @GET("algorithm/page")
    suspend fun getAlgorithmPage(
        @Header("Authorization") Authorization: String,
        @Query("page") page: Int,
        @Query("status") status: String
    ): Response<AcceptPost>


    //게시물 조회
    @GET("algorithm/count")
    suspend fun getCount(
        @Header("Authorization") Authorization: String,
    ): Response<PostCount>

    //관리자로 로그인
    @Headers("accept: application/json", "content-type: application/json")
    @POST("auth")
    suspend fun transferAdminLogin(@Body password: HashMap<String, String>): Response<AdminSignInDTO>?
}