package com.intobeta.live.ui
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.intobeta.live.api.ApiClient
import com.intobeta.live.api.XtreamUrlBuilder
import com.intobeta.live.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class LoginActivity: AppCompatActivity(){
    private lateinit var b: ActivityLoginBinding
    override fun onCreate(s:Bundle?){
        super.onCreate(s); b=ActivityLoginBinding.inflate(layoutInflater); setContentView(b.root)
        // তোমার account auto fill করে দিলাম test এর জন্য
        b.etServer.setText("http://filex.me:8080")
        b.etUser.setText("3114654477")
        b.etPass.setText("5787654467")
        b.btnLogin.setOnClickListener{
            val server=b.etServer.text.toString().trim(); val user=b.etUser.text.toString().trim(); val pass=b.etPass.text.toString().trim()
            if(server.isEmpty()||user.isEmpty()||pass.isEmpty()){ Toast.makeText(this,"Fill all",Toast.LENGTH_SHORT).show(); return@setOnClickListener }
            b.btnLogin.text="Connecting..."
            b.btnLogin.isEnabled=false
            CoroutineScope(Dispatchers.IO).launch{
                try{
                    val service = ApiClient.getService(server)
                    val info = service.login(XtreamUrlBuilder.loginUrl(server,user,pass))
                    withContext(Dispatchers.Main){
                        if(info.user_info.auth==1){
                            getSharedPreferences("intobeta", MODE_PRIVATE).edit().putString("server",XtreamUrlBuilder.normalizeServer(server)).putString("username",user).putString("password",pass).apply()
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java)); finish()
                        } else {
                            b.btnLogin.text="LOGIN"; b.btnLogin.isEnabled=true
                            Toast.makeText(this@LoginActivity,"Auth failed - check user/pass",Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch(e:Exception){ 
                    e.printStackTrace()
                    withContext(Dispatchers.Main){ 
                        b.btnLogin.text="LOGIN"; b.btnLogin.isEnabled=true
                        Toast.makeText(this@LoginActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show() 
                    } 
                }
            }
        }
    }
}
