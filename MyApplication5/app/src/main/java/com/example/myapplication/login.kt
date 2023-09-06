package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import android.util.Patterns

class login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        val btnLogin = findViewById<Button>(R.id.btn_login)
        btnLogin.setOnClickListener {
            loginUser()

        }
        val btnSignUp = findViewById<Button>(R.id.btn_signUp)
        btnSignUp.setOnClickListener {
            irActividad(SignUp::class.java)
        }
        val btnLogOut = findViewById<Button>(R.id.btn_logout)
        btnLogOut.setOnClickListener { logOut() }
    }

    private fun logOut() {
        auth.signOut()
        showToast("Logged out successfully!")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun irActividad(clase: Class<*>){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
    private fun loginUser() {
        val emailEditText = findViewById<EditText>(R.id.input_email)
        val passwordEditText = findViewById<EditText>(R.id.input_password)
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        if (isValidEmail(email)) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        showToast("Login successful!")
                        irActividad(SelectCategory::class.java)
                    } else {
                        val errorMessage =
                            task.exception?.message ?: "Login failed. Please try again."
                        showToast(errorMessage)
                    }
                }
        }else {
            showToast("Invalid email format. Please enter a valid email address.")
        }
    }
}