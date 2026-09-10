package com.intobeta.live.model
import com.google.gson.annotations.SerializedName
data class Category(@SerializedName("category_id") val categoryId: String, @SerializedName("category_name") val categoryName: String, @SerializedName("parent_id") val parentId: Int)
data class Stream(@SerializedName("name") val name: String, @SerializedName("stream_id") val streamId: Int, @SerializedName("stream_icon") val streamIcon: String, @SerializedName("category_id") val categoryId: String)
data class LoginInfo(val user_info: UserInfo)
data class UserInfo(val auth: Int)
