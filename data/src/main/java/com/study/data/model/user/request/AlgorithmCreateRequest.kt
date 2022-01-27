package com.study.data.model.user.request

import com.google.gson.annotations.SerializedName

data class AlgorithmCreateRequest(
    val title: String,
    val content: String,
    val tag: String,
    @SerializedName("verify")
    val verifier: Verifier
)