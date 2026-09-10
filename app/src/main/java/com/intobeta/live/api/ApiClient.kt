package com.intobeta.live.api
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
object ApiClient {
    private val client = OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).readTimeout(15, TimeUnit.SECONDS).build()
    fun getService(server:String): com.intobeta.live.api.ApiService {
        val base = XtreamUrlBuilder.normalizeServer(server) + "/"
        return Retrofit.Builder().baseUrl(base).client(client).addConverterFactory(GsonConverterFactory.create()).build().create(com.intobeta.live.api.ApiService::class.java)
    }
}
