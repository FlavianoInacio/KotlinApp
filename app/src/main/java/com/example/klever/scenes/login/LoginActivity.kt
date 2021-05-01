package com.example.klever.scenes.login
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.klever.scenes.carros.CarrosActivity
import com.example.klever.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import com.example.klever.databinding.ActivityLoginBinding


class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate), View.OnClickListener {
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buttonLogin.setOnClickListener(this)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel.loginSucess.observe(this, Observer {
                when(it){
                    true ->{
                        val intent =  Intent(applicationContext, CarrosActivity::class.java)
                        startActivity(intent)
                    }
                }
        })
        viewModel.loginError.observe(this, Observer {
            when(it){
                true ->{
                Toast.makeText(applicationContext,"Problema ao efetuar o login!",Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onClick(v: View?) {
        val user = usuario.text.toString();
        val password = senha.text.toString();
        viewModel.login(user,password)
    }
    override fun onCreatedView() = binding.run {

    }
}