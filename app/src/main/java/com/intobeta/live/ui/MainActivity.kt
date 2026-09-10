package com.intobeta.live.ui
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.intobeta.live.R
import com.intobeta.live.api.ApiClient
import com.intobeta.live.api.XtreamUrlBuilder
import com.intobeta.live.databinding.ActivityMainBinding
import com.intobeta.live.model.Stream
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class MainActivity: AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var streamAdapter: StreamAdapter
    private var allStreams: List<Stream> = emptyList()
    private val prefs by lazy{ getSharedPreferences("intobeta", MODE_PRIVATE) }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setOnMenuItemClickListener{
            if(it.itemId==R.id.action_logout){ prefs.edit().clear().apply(); startActivity(Intent(this, LoginActivity::class.java)); finish(); true } else false
        }
        categoryAdapter=CategoryAdapter{ cat-> filterByCategory(cat.categoryId) }
        streamAdapter=StreamAdapter{ s-> play(s) }
        binding.rvCategory.layoutManager=LinearLayoutManager(this); binding.rvCategory.adapter=categoryAdapter
        binding.rvStreams.layoutManager=LinearLayoutManager(this); binding.rvStreams.adapter=streamAdapter
        load()
    }
    private fun load(){
        val server=prefs.getString("server","")!!; val user=prefs.getString("username","")!!; val pass=prefs.getString("password","")!!
        CoroutineScope(Dispatchers.IO).launch{
            try{
                val service = ApiClient.getService(server)
                val cats=service.getCategories(XtreamUrlBuilder.categoriesUrl(server,user,pass))
                val streams=service.getStreams(XtreamUrlBuilder.streamsUrl(server,user,pass))
                allStreams=streams
                withContext(Dispatchers.Main){
                    if(cats.isEmpty() && streams.isEmpty()){
                        Toast.makeText(this@MainActivity,"No data from server",Toast.LENGTH_LONG).show()
                    }
                    categoryAdapter.submitList(cats); streamAdapter.submitList(streams)
                }
            }catch(e:Exception){
                e.printStackTrace()
                withContext(Dispatchers.Main){ Toast.makeText(this@MainActivity,"Load Error: ${e.message}",Toast.LENGTH_LONG).show() }
            }
        }
    }
    private fun filterByCategory(id:String){
        try{ val f=if(id=="0") allStreams else allStreams.filter{ it.categoryId==id }; streamAdapter.submitList(f) }catch(_:Exception){}
    }
    private fun play(s:Stream){
        try{
            val server=prefs.getString("server","")!!; val user=prefs.getString("username","")!!; val pass=prefs.getString("password","")!!
            val url=XtreamUrlBuilder.buildLiveUrl(server,user,pass,s.streamId)
            val i=Intent(this, PlayerActivity::class.java); i.putExtra("url", url); i.putExtra("title", s.name); startActivity(i)
        }catch(e:Exception){ Toast.makeText(this,"Play Error: ${e.message}",Toast.LENGTH_SHORT).show() }
    }
}
