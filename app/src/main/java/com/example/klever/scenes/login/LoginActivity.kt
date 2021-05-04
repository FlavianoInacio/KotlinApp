package com.example.klever.scenes.login
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.klever.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import com.example.klever.databinding.ActivityLoginBinding
import com.example.klever.infra.ResultadoLogin
import com.example.klever.scenes.carros.CarrosActivity


class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate), View.OnClickListener {
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buttonLogin.setOnClickListener(this)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onClick(v: View?) {
        val user = usuario.text.toString();
        val password = senha.text.toString();
        viewModel.login(user,password).observe(this, Observer {
            when(it){
                ResultadoLogin.SUCESSO->{
                    val intent = Intent(this,CarrosActivity::class.java)
                    startActivity(intent)
                }
                ResultadoLogin.ERROR->{
                    Toast.makeText(applicationContext,"Erro na tentativa de Login", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
    override fun onCreatedView() = binding.run {

    }
}