package com.intobeta.live

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var rvCategory: RecyclerView
    private lateinit var rvStreams: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var streamAdapter: StreamAdapter

    private var allStreams: List<Stream> = emptyList()
    private var server: String = ""
    private var username: String = ""
    private var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        rvCategory = findViewById(R.id.rvCategory)
        rvStreams = findViewById(R.id.rvStreams)

        val prefs = getSharedPreferences("intobeta", MODE_PRIVATE)
        server = prefs.getString("server", "").orEmpty()
        username = prefs.getString("username", "").orEmpty()
        password = prefs.getString("password", "").orEmpty()

        categoryAdapter = CategoryAdapter { category -> filterByCategory(category.categoryId) }
        streamAdapter = StreamAdapter { stream -> playStream(stream) }

        rvCategory.layoutManager = LinearLayoutManager(this)
        rvStreams.layoutManager = LinearLayoutManager(this)
        rvCategory.adapter = categoryAdapter
        rvStreams.adapter = streamAdapter

        load()
    }

    private fun load() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = ApiClient.getService(server)
                val categories = service.getCategories(
                    XtreamUrlBuilder.categoriesUrl(server, username, password)
                )
                val streams = service.getStreams(
                    XtreamUrlBuilder.streamsUrl(server, username, password)
                )
                withContext(Dispatchers.Main) {
                    val cats = ArrayList<Category>()
                    cats.add(Category(categoryId = "0", categoryName = "All"))
                    cats.addAll(categories)
                    categoryAdapter.submitList(cats)
                    allStreams = streams
                    streamAdapter.submitList(streams)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun filterByCategory(id: String) {
        val filtered = if (id == "0") allStreams else allStreams.filter { it.categoryId == id }
        streamAdapter.submitList(filtered)
    }

    private fun playStream(stream: Stream) {
        if (stream.streamId == 0) {
            Toast.makeText(this, "Invalid stream id", Toast.LENGTH_SHORT).show()
            return
        }
        val url = XtreamUrlBuilder.buildLiveUrl(server, username, password, stream.streamId)
        val intent = Intent(this, PlayerActivity::class.java)
        intent.putExtra("url", url)
        intent.putExtra("title", stream.name)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_logout) {
            getSharedPreferences("intobeta", MODE_PRIVATE).edit().clear().apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
