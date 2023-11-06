package com.androdu.foody.models


import com.google.gson.annotations.SerializedName

data class ApiResponseError(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)