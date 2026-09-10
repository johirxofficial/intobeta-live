package com.intobeta.live.ui
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.intobeta.live.databinding.ActivityPlayerBinding
class PlayerActivity: AppCompatActivity(){
    private lateinit var b: ActivityPlayerBinding
    private var player: ExoPlayer? = null
    override fun onCreate(s:Bundle?){
        super.onCreate(s); b=ActivityPlayerBinding.inflate(layoutInflater); setContentView(b.root)
        val url=intent.getStringExtra("url")?:return; val title=intent.getStringExtra("title")?:""
        b.tvTitle.text=title
        player=ExoPlayer.Builder(this).build()
        b.playerView.player=player
        player?.setMediaItem(MediaItem.fromUri(url)); player?.prepare(); player?.play()
    }
    override fun onDestroy(){ super.onDestroy(); player?.release() }
}
