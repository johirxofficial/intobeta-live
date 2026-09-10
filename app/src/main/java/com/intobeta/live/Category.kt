package com.intobeta.live

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("category_id") val categoryId: String = "0",
    @SerializedName("category_name") val categoryName: String = "Unknown",
    @SerializedName("parent_id") val parentId: Int = 0
)
