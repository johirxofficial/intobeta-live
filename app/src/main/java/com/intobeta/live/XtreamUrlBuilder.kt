package com.intobeta.live

object XtreamUrlBuilder {
    fun normalizeServer(s: String): String {
        var server = s.trim()
        if (!server.startsWith("http")) server = "http://$server"
        return server.trimEnd('/')
    }

    fun loginUrl(server: String, user: String, pass: String): String =
        "${normalizeServer(server)}/player_api.php?username=$user&password=$pass"

    fun categoriesUrl(server: String, user: String, pass: String): String =
        "${normalizeServer(server)}/player_api.php?username=$user&password=$pass&action=get_live_categories"

    fun streamsUrl(server: String, user: String, pass: String): String =
        "${normalizeServer(server)}/player_api.php?username=$user&password=$pass&action=get_live_streams"

    fun buildLiveUrl(server: String, user: String, pass: String, id: Int): String =
        "${normalizeServer(server)}/live/$user/$pass/$id.m3u8"
}
