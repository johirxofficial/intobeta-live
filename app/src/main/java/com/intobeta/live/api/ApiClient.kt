package com.intobeta.live.api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object ApiClient {
    fun getService(server:String): ApiService {
        val base = XtreamUrlBuilder.normalizeServer(server) + "/"
        return Retrofit.Builder().baseUrl(base).addConverterFactory(GsonConverterFactory.create()).build().create(ApiService::class.java)
    }
    // backward compatibility
    val api: ApiService get() = getService("http://filex.me:8080")
}
