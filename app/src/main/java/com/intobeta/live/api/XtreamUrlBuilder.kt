
package com.intobeta.live.api
object XtreamUrlBuilder {
    fun getLiveUrl(server: String, user: String, pass: String, streamId: Int): String {
        var s = server; if(!s.endsWith("/")) s+="/"
        return "${s}live/$user/$pass/$streamId.m3u8"
    }
}
