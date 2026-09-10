package com.intobeta.live.api
import com.intobeta.live.model.Category
import com.intobeta.live.model.LoginInfo
import com.intobeta.live.model.Stream
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
interface ApiService {
    @GET
    suspend fun login(@Url url:String): LoginInfo
    @GET
    suspend fun getCategories(@Url url:String): List<Category>
    @GET
    suspend fun getStreams(@Url url:String): List<Stream>
}
object XtreamUrlBuilder {
    fun normalizeServer(s:String):String {
        var server = s.trim()
        if(!server.startsWith("http")) server = "http://$server"
        return server.trimEnd('/')
    }
    fun loginUrl(server:String, user:String, pass:String):String {
        return "${normalizeServer(server)}/player_api.php?username=$user&password=$pass"
    }
    fun categoriesUrl(server:String, user:String, pass:String):String {
        return "${normalizeServer(server)}/player_api.php?username=$user&password=$pass&action=get_live_categories"
    }
    fun streamsUrl(server:String, user:String, pass:String):String {
        return "${normalizeServer(server)}/player_api.php?username=$user&password=$pass&action=get_live_streams"
    }
    fun buildLiveUrl(server:String, user:String, pass:String, id:Int): String {
        val s = normalizeServer(server)
        return "$s/live/$user/$pass/$id.m3u8"
    }
}
