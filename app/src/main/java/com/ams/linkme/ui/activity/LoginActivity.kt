package com.ams.linkme.ui.activity

import LoginViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ams.linkme.R

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 初始化视图
        emailEditText = findViewById(R.id.edit_text_username)
        passwordEditText = findViewById(R.id.edit_text_password)
        loginButton = findViewById(R.id.button_login)

        // 初始化 ViewModel
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        // 监听登录结果
        loginViewModel.loginResultLiveData.observe(this) { result ->
            when (result) {
                is LoginViewModel.LoginResult.Success -> {
                    navigateToMainActivity()
                }

                is LoginViewModel.LoginResult.Error -> {
                    Toast.makeText(this, result.errorMessage, Toast.LENGTH_SHORT).show()
                }

                else -> {
                    // 处理未覆盖到的情况
                }
            }
        }

        // 点击登录按钮触发登录逻辑
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            loginViewModel.login(email, password)
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}
