package com.elbek.worldmovies.presentation.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.elbek.worldmovies.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private var currentUser: FirebaseUser? = null

    private var loginCheck = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadData()
        auth = FirebaseAuth.getInstance()
        binding.register.setOnClickListener {
            val reIntent = Intent(this, AuthenticationActivity::class.java)
            startActivity(reIntent)
        }
        binding.signIn.setOnClickListener {
            val userEmail = binding.userEmail.text.toString()
            val password = binding.userPassword.text.toString()
            if (userEmail.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(userEmail, password)
                    .addOnCompleteListener { p0 ->
                        if (p0.isSuccessful) {
                            loginCheck = true
                            saveData(loginCheck)
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                "Something went wrong",
                                Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Email or Password is incorrect", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun saveData(loginCheck: Boolean) {
        val sharedPreferences =
            this.getSharedPreferences("sharedPrefs_check", Context.MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        edit.apply {
            putBoolean("BOOLEAN_KEY_CHECK", loginCheck)
        }.apply()
    }

    private fun loadData() {
        val sharedPreferences =
            this.getSharedPreferences("sharedPrefs_check", Context.MODE_PRIVATE)
        val saveBoolean = sharedPreferences.getBoolean("BOOLEAN_KEY_CHECK", false)
        if (saveBoolean) {
            val intents = Intent(this, MainActivity::class.java)
            startActivity(intents)
            finish()
        } else {
            saveData(false)
        }
    }

    override fun onStart() {
        super.onStart()
        currentUser = auth.currentUser
    }
}