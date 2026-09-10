
package com.intobeta.live.api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object ApiClient {
    fun getClient(serverUrl: String): Retrofit {
        var url = serverUrl
        if(!url.endsWith("/")) url += "/"
        return Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build()
    }
}
