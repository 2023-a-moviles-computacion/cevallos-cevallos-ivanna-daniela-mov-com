package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date

class SignUp : AppCompatActivity() {
    var query: Query? = null
    val arreglo: ArrayList<Person> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val botoGuardarPersona = findViewById<Button>(R.id.btn_sinUp)
        botoGuardarPersona .setOnClickListener {
            guardarPersona()
            }


    }

    private fun registrarPerson() {
        val emailEditText  = findViewById<EditText>(R.id.input_email_signu)
        val passwordEditText = findViewById<EditText>(R.id.input_pass)
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        if (isValidEmail(email)) {
            val auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        showToast("Eureka! You are now registered.")
                        clearInputFields()
                    } else {
                        val errorMessage =
                            task.exception?.message ?: "Registration failed. Please try again."
                        showToast(errorMessage)
                    }
                }
        } else {
            showToast("Invalid email format. Please enter a valid email address.")
        }
    }
    private fun guardarPersona() {
        val db = Firebase.firestore
        val people = db.collection("people")
        val name = findViewById<EditText>(R.id.input_name)
        val email = findViewById<EditText>(R.id.input_email_signu)
        val mobileNumber = findViewById<EditText>(R.id.input_phone)
        val dayBirth = findViewById<EditText>(R.id.input_day)
        val monthBirth = findViewById<EditText>(R.id.input_month)
        val yearBirth = findViewById<EditText>(R.id.input_year)

        var gender = ""
        val radioGroupGender = findViewById<RadioGroup>(R.id.radioGroupGender)

        // Move the gender determination here, inside the button click listener
        gender = when (radioGroupGender.checkedRadioButtonId) {
            R.id.rb_male -> "Male"
            R.id.rb_female -> "Female"
            R.id.rb_others -> "Others"
            else -> "" // Handle case when no gender is selected
        }

        val data1 = hashMapOf(
            "name" to name.text.toString(),
            "email" to email.text.toString(),
            "mobile number" to mobileNumber.text.toString(),
            "Birthday" to "${dayBirth.text.toString()}/${monthBirth.text.toString()}/${yearBirth.text.toString()}",
            "gender" to gender,
        )
        val identificador = (Date().time / 1000).toInt()
        people
            .document(identificador.toString())
            .set(data1)
            .addOnSuccessListener {
                Toast.makeText(this, "Eureka you are now sign in!", Toast.LENGTH_SHORT).show()
                registrarPerson()

            }
            .addOnFailureListener {  }

    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun clearInputFields() {
        val name = findViewById<EditText>(R.id.input_name)
        val passwordEditText = findViewById<EditText>(R.id.input_pass)
        val email = findViewById<EditText>(R.id.input_email_signu)
        val mobileNumber = findViewById<EditText>(R.id.input_phone)
        val dayBirth = findViewById<EditText>(R.id.input_day)
        val monthBirth = findViewById<EditText>(R.id.input_month)
        val yearBirth = findViewById<EditText>(R.id.input_year)
        val radioGroupGender = findViewById<RadioGroup>(R.id.radioGroupGender)

        // Clear the input fields
        name.text.clear()
        email.text.clear()
        mobileNumber.text.clear()
        dayBirth.text.clear()
        monthBirth.text.clear()
        yearBirth.text.clear()

        // Clear the selected radio button
        radioGroupGender.clearCheck()
    }
}