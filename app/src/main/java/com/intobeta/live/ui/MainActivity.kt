
package com.intobeta.live.ui
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.intobeta.live.api.ApiClient
import com.intobeta.live.api.XtreamApi
import com.intobeta.live.api.XtreamUrlBuilder
import com.intobeta.live.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var api: XtreamApi
    private lateinit var server: String; private lateinit var user: String; private lateinit var pass: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val prefs = getSharedPreferences("intobeta", MODE_PRIVATE)
        server = prefs.getString("server","")!!; user = prefs.getString("user","")!!; pass = prefs.getString("pass","")!!
        api = ApiClient.getClient(server).create(XtreamApi::class.java)

        binding.toolbar.setOnMenuItemClickListener {
            if(it.itemId==com.intobeta.live.R.id.action_logout){ prefs.clear(); finish(); true } else false
        }

        loadCategories()
    }

    private fun loadCategories(){
        lifecycleScope.launch {
            try {
                val cats = api.getLiveCategories(user, pass)
                binding.rvCategory.layoutManager = LinearLayoutManager(this@MainActivity)
                binding.rvCategory.adapter = CategoryAdapter(cats){ cat ->
                    loadStreams(cat.categoryId)
                }
            } catch(e: Exception){ e.printStackTrace() }
        }
    }
    private fun loadStreams(catId: String){
        lifecycleScope.launch {
            val streams = api.getLiveStreams(user, pass, catId = catId)
            binding.rvStreams.layoutManager = LinearLayoutManager(this@MainActivity)
            binding.rvStreams.adapter = StreamAdapter(streams){ stream ->
                val url = XtreamUrlBuilder.getLiveUrl(server, user, pass, stream.streamId)
                val i = Intent(this@MainActivity, PlayerActivity::class.java)
                i.putExtra("url", url); i.putExtra("name", stream.name)
                startActivity(i)
            }
        }
    }
}
