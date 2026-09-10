package com.intobeta.live.ui
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.intobeta.live.databinding.ActivityLoginBinding
import com.intobeta.live.api.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class LoginActivity: AppCompatActivity(){
    private lateinit var b: ActivityLoginBinding
    override fun onCreate(s:Bundle?){
        super.onCreate(s); b=ActivityLoginBinding.inflate(layoutInflater); setContentView(b.root)
        b.btnLogin.setOnClickListener{
            val server=b.etServer.text.toString().trim(); val user=b.etUser.text.toString().trim(); val pass=b.etPass.text.toString().trim()
            if(server.isEmpty()||user.isEmpty()||pass.isEmpty()){ Toast.makeText(this,"Fill all",Toast.LENGTH_SHORT).show(); return@setOnClickListener }
            CoroutineScope(Dispatchers.IO).launch{
                try{
                    val info = ApiClient.api.login(user,pass)
                    withContext(Dispatchers.Main){
                        if(info.user_info.auth==1){
                            getSharedPreferences("intobeta", MODE_PRIVATE).edit().putString("server",server).putString("username",user).putString("password",pass).apply()
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java)); finish()
                        } else Toast.makeText(this@LoginActivity,"Auth failed",Toast.LENGTH_SHORT).show()
                    }
                } catch(e:Exception){ withContext(Dispatchers.Main){ Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_LONG).show() } }
            }
        }
    }
}
