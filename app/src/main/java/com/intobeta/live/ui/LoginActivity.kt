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
        super.onCreate(s)
        b=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(b.root)
        b.etServer.setText("http://filex.me:8080")
        b.etUser.setText("3114654477")
        b.etPass.setText("5787654467")
        b.btnLogin.setOnClickListener{
            val server=b.etServer.text.toString().trim()
            val user=b.etUser.text.toString().trim()
            val pass=b.etPass.text.toString().trim()
            if(server.isEmpty()||user.isEmpty()||pass.isEmpty()){
                Toast.makeText(this,"Fill all fields",Toast.LENGTH_SHORT).show(); return@setOnClickListener
            }
            b.btnLogin.isEnabled=false
            b.btnLogin.text="Connecting..."
            CoroutineScope(Dispatchers.IO).launch{
                try{
                    val service = ApiClient.getService(server)
                    val url = XtreamUrlBuilder.loginUrl(server,user,pass)
                    val info = service.login(url)
                    withContext(Dispatchers.Main){
                        // filex.me কখনো auth=1 দেয়, কখনো status=Active দেয় - দুটোই valid
                        val isValid = (info.user_info?.auth == 1) || (info.user_info?.status?.equals("Active", true) == true) || (info.user_info != null)
                        if(isValid){
                            getSharedPreferences("intobeta", MODE_PRIVATE).edit()
                                .putString("server",XtreamUrlBuilder.normalizeServer(server))
                                .putString("username",user).putString("password",pass).apply()
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        } else {
                            b.btnLogin.isEnabled=true; b.btnLogin.text="LOGIN"
                            Toast.makeText(this@LoginActivity,"Invalid account - auth failed",Toast.LENGTH_LONG).show()
                        }
                    }
                } catch(e:Exception){
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        b.btnLogin.isEnabled=true; b.btnLogin.text="LOGIN"
                        Toast.makeText(this@LoginActivity, "Connect Error: ${e.message}\nTry http:// not https://", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
