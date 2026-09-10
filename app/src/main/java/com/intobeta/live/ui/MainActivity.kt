package com.intobeta.live.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.intobeta.live.api.ApiClient
import com.intobeta.live.api.XtreamUrlBuilder
import com.intobeta.live.databinding.ActivityMainBinding
import com.intobeta.live.model.Category
import com.intobeta.live.model.Stream
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var streamAdapter: StreamAdapter
    private var categories: List<Category> = emptyList()
    private var allStreams: List<Stream> = emptyList()

    private val prefs by lazy { getSharedPreferences("intobeta", MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.action_logout) {
                prefs.edit().clear().apply()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                true
            } else false
        }

        categoryAdapter = CategoryAdapter { cat ->
            filterByCategory(cat.categoryId)
        }
        streamAdapter = StreamAdapter { stream ->
            playStream(stream)
        }

        binding.rvCategory.layoutManager = LinearLayoutManager(this)
        binding.rvCategory.adapter = categoryAdapter

        binding.rvStreams.layoutManager = LinearLayoutManager(this)
        binding.rvStreams.adapter = streamAdapter

        loadData()
    }

    private fun loadData() {
        val server = prefs.getString("server", "")!!
        val username = prefs.getString("username", "")!!
        val password = prefs.getString("password", "")!!

        CoroutineScope(Dispatchers.IO).launch {
            try {
                categories = ApiClient.api.getCategories(server, username, password)
                allStreams = ApiClient.api.getStreams(server, username, password)
                withContext(Dispatchers.Main) {
                    categoryAdapter.submitList(categories)
                    streamAdapter.submitList(allStreams)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun filterByCategory(catId: String) {
        val filtered = if (catId == "0") allStreams else allStreams.filter { it.categoryId == catId }
        streamAdapter.submitList(filtered)
    }

    private fun playStream(stream: Stream) {
        val server = prefs.getString("server", "")!!
        val username = prefs.getString("username", "")!!
        val password = prefs.getString("password", "")!!
        val url = XtreamUrlBuilder.buildLiveUrl(server, username, password, stream.streamId)
        val intent = Intent(this, PlayerActivity::class.java)
        intent.putExtra("url", url)
        intent.putExtra("title", stream.name)
        startActivity(intent)
    }
}
