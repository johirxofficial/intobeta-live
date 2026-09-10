package com.intobeta.live

import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun login(@Url url: String): LoginInfo

    @GET
    suspend fun getCategories(@Url url: String): List<Category>

    @GET
    suspend fun getStreams(@Url url: String): List<Stream>
}
