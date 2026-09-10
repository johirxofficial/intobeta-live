package com.intobeta.live

import com.google.gson.annotations.SerializedName

data class LoginInfo(
    @SerializedName("user_info") val user_info: UserInfo? = null
)
