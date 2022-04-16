package com.elbek.worldmovies.presentation.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.elbek.worldmovies.databinding.ActivityAuthenticationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthenticationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthenticationBinding
    private lateinit var auth: FirebaseAuth
    private var currentUser: FirebaseUser? = null
    private var loginCheck = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        loadData()
        binding.login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.signUp.setOnClickListener {
            val userName = binding.userName.text.toString()
            val userEmail: String = binding.userEmail.text.toString()
            val password = binding.userPassword.text.toString()
            val rePassword = binding.passwordAgain.text.toString()
            if (password == rePassword && userName.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(userEmail, password)
                    .addOnCompleteListener { p0 ->
                        if (p0.isSuccessful) {
                            loginCheck = true
                            saveData(loginCheck)
                            val intents =
                                Intent(this@AuthenticationActivity, MainActivity::class.java)
                            startActivity(intents)
                            finish()
                        } else {
                            Toast.makeText(
                                this@AuthenticationActivity,
                                "Something went wrong",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Password or UserName is incorrect", Toast.LENGTH_SHORT).show()
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
            val intents = Intent(this@AuthenticationActivity, MainActivity::class.java)
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