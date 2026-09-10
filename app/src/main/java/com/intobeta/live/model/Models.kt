
package com.intobeta.live.model
import com.google.gson.annotations.SerializedName
data class LoginResponse(@SerializedName("user_info") val userInfo: UserInfo, @SerializedName("server_info") val serverInfo: ServerInfo)
data class UserInfo(val auth: Int, val status: String, @SerializedName("exp_date") val expDate: String)
data class ServerInfo(val url: String, val port: String)
data class Category(@SerializedName("category_id") val categoryId: String, @SerializedName("category_name") val categoryName: String)
data class LiveStream(@SerializedName("stream_id") val streamId: Int, @SerializedName("name") val name: String, @SerializedName("stream_icon") val icon: String)
