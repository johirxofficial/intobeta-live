package com.intobeta.live.api
import com.intobeta.live.model.Category
import com.intobeta.live.model.LoginInfo
import com.intobeta.live.model.Stream
import retrofit2.http.GET
import retrofit2.http.Query
interface ApiService {
    @GET("player_api.php") suspend fun login(@Query("username") u:String, @Query("password") p:String): LoginInfo
    @GET("player_api.php") suspend fun getCategories(@Query("username") u:String, @Query("password") p:String, @Query("action") a:String="get_live_categories"): List<Category>
    @GET("player_api.php") suspend fun getStreams(@Query("username") u:String, @Query("password") p:String, @Query("action") a:String="get_live_streams"): List<Stream>
}
object XtreamUrlBuilder {
    fun buildLiveUrl(server:String, user:String, pass:String, id:Int): String {
        val s = server.trimEnd('/')
        return "$s/live/$user/$pass/$id.m3u8"
    }
}
