
package com.intobeta.live.api
import com.intobeta.live.model.*
import retrofit2.http.GET
import retrofit2.http.Query
interface XtreamApi {
    @GET("player_api.php")
    suspend fun login(@Query("username") u: String, @Query("password") p: String): LoginResponse
    @GET("player_api.php")
    suspend fun getLiveCategories(@Query("username") u: String, @Query("password") p: String, @Query("action") a: String="get_live_categories"): List<Category>
    @GET("player_api.php")
    suspend fun getLiveStreams(@Query("username") u: String, @Query("password") p: String, @Query("action") a: String="get_live_streams", @Query("category_id") catId: String): List<LiveStream>
    @GET("player_api.php")
    suspend fun getVodCategories(@Query("username") u: String, @Query("password") p: String, @Query("action") a: String="get_vod_categories"): List<Category>
}
