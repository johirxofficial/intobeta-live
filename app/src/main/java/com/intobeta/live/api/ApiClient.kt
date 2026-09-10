package com.intobeta.live.api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object ApiClient {
    val api: ApiService = Retrofit.Builder().baseUrl("https://google.com/").addConverterFactory(GsonConverterFactory.create()).build().create(ApiService::class.java)
}
