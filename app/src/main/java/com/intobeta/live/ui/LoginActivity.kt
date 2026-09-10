
package com.intobeta.live.ui
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.intobeta.live.api.ApiClient
import com.intobeta.live.api.XtreamApi
import com.intobeta.live.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("intobeta", MODE_PRIVATE)
        if(prefs.contains("server")){ startActivity(Intent(this, MainActivity::class.java)); finish(); return }

        binding.btnLogin.setOnClickListener {
            val server = binding.etServer.text.toString().trim()
            val user = binding.etUser.text.toString().trim()
            val pass = binding.etPass.text.toString().trim()
            if(server.isEmpty()||user.isEmpty()||pass.isEmpty()){ Toast.makeText(this,"সব ফিল্ড পূরণ করো",Toast.LENGTH_SHORT).show(); return@setOnClickListener }
            binding.btnLogin.isEnabled=false
            lifecycleScope.launch {
                try {
                    val api = ApiClient.getClient(server).create(XtreamApi::class.java)
                    val res = api.login(user, pass)
                    if(res.userInfo.auth==1){
                        prefs.edit().putString("server", server).putString("user", user).putString("pass", pass).apply()
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java)); finish()
                    } else Toast.makeText(this@LoginActivity,"Auth Failed",Toast.LENGTH_SHORT).show()
                } catch(e: Exception){ Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_LONG).show() }
                binding.btnLogin.isEnabled=true
            }
        }
    }
}
