package com.intobeta.live.model
import com.google.gson.annotations.SerializedName
data class Category(@SerializedName("category_id") val categoryId: String="0", @SerializedName("category_name") val categoryName: String="Unknown", @SerializedName("parent_id") val parentId: Int=0)
data class Stream(@SerializedName("name") val name: String="No Name", @SerializedName("stream_id") val streamId: Int=0, @SerializedName("stream_icon") val streamIcon: String="", @SerializedName("category_id") val categoryId: String="0")
data class LoginInfo(@SerializedName("user_info") val user_info: UserInfo? = null)
data class UserInfo(@SerializedName("auth") val auth: Int=0, @SerializedName("status") val status: String? = null)
