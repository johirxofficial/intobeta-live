package com.intobeta.live

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var etServer: EditText
    private lateinit var etUser: EditText
    private lateinit var etPass: EditText
    private lateinit var btnLogin: Button
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etServer = findViewById(R.id.etServer)
        etUser = findViewById(R.id.etUser)
        etPass = findViewById(R.id.etPass)
        btnLogin = findViewById(R.id.btnLogin)
        prefs = getSharedPreferences("intobeta", MODE_PRIVATE)

        etServer.setText(prefs.getString("server", "http://filex.me:8080") ?: "http://filex.me:8080")
        etUser.setText(prefs.getString("username", "3114654477") ?: "3114654477")
        etPass.setText(prefs.getString("password", "5787654467") ?: "5787654467")

        btnLogin.setOnClickListener {
            val server = etServer.text?.toString()?.trim().orEmpty()
            val user = etUser.text?.toString()?.trim().orEmpty()
            val pass = etPass.text?.toString()?.trim().orEmpty()
            if (server.isEmpty() || user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            btnLogin.isEnabled = false
            btnLogin.text = "Connecting..."
            val normalized = XtreamUrlBuilder.normalizeServer(server)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val service = ApiClient.getService(normalized)
                    val info = service.login(XtreamUrlBuilder.loginUrl(normalized, user, pass))
                    val valid = info.user_info != null ||
                            info.user_info?.auth == 1 ||
                            info.user_info?.status?.equals("Active", ignoreCase = true) == true
                    withContext(Dispatchers.Main) {
                        if (valid) {
                            prefs.edit()
                                .putString("server", normalized)
                                .putString("username", user)
                                .putString("password", pass)
                                .apply()
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        } else {
                            btnLogin.isEnabled = true
                            btnLogin.text = "LOGIN"
                            Toast.makeText(this@LoginActivity, "Invalid credentials", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        btnLogin.isEnabled = true
                        btnLogin.text = "LOGIN"
                        Toast.makeText(this@LoginActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
