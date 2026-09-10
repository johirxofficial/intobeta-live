package com.intobeta.live

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("auth") val auth: Int = 0,
    @SerializedName("status") val status: String? = null
)
