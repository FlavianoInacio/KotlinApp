package com.example.klever

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.klever.infra.RetrofitClient
import com.example.klever.models.Login
import com.example.klever.models.UserLogin
import com.example.klever.services.LoginService
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), View.OnClickListener {
   private  lateinit var shared: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shared = this.getSharedPreferences("user", Context.MODE_PRIVATE)
        setContentView(R.layout.activity_login)

        buttonLogin.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        val intent =  Intent(this,CarrosActivity::class.java)

        var usuario = usuario.text.toString();
        var senha = senha.text.toString();
        val userLogin = UserLogin(
            username = usuario,
            password = senha

        )
        val remote = RetrofitClient.createService(LoginService::class.java)
        val call : Call<Login> = remote.login(userLogin)
        call.enqueue(object : Callback<Login>{
            override fun onFailure(call: Call<Login>, t: Throwable) {
                System.out.println(t.message)
            }

            override fun onResponse(call: Call<Login>, res: Response<Login>) {
                val login = res.body()
                shared.edit().putString("token",login?.token).apply()
                startActivity(intent)
            }

        })
    }
}