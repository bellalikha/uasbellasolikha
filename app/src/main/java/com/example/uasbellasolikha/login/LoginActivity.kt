package com.example.uasbellasolikha.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uasbellasolikha.R
import com.example.uasbellasolikha.main.MainActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    private lateinit var btnLogin: MaterialButton
    private lateinit var inputUser: TextInputEditText
    private lateinit var inputPassword: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setInitLayout()
        setInputData()
    }

    private fun setInitLayout() {
        inputUser = findViewById(R.id.inputUser)
        inputPassword = findViewById(R.id.inputPassword)
        btnLogin = findViewById(R.id.btnLogin)

    }

    private fun setInputData() {
        btnLogin.setOnClickListener {
            val strUsername = inputUser.text.toString().trim()
            val strPassword = inputPassword.text.toString().trim()

            if (strUsername.isEmpty() || strPassword.isEmpty()) {
                Toast.makeText(
                    this@LoginActivity,
                    "Ups, Form harus diisi semua!",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                if (strUsername == "bella" && strPassword == "bella123") {

                    Toast.makeText(this@LoginActivity, "Login Berhasil!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Ups, Username atau Password Anda salah!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}