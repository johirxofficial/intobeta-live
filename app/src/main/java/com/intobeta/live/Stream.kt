package com.intobeta.live

import com.google.gson.annotations.SerializedName

data class Stream(
    @SerializedName("name") val name: String = "No Name",
    @SerializedName("stream_id") val streamId: Int = 0,
    @SerializedName("stream_icon") val streamIcon: String = "",
    @SerializedName("category_id") val categoryId: String = "0"
)
