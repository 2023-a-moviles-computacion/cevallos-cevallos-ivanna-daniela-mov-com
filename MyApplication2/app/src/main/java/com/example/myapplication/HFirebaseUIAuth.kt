package com.example.myapplication

import android.app.PendingIntent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class HFirebaseUIAuth : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hfirebase_uiauth)

        val btnLogin = findViewById<Button>(R.id.btn_login_firebase)
        btnLogin.setOnClickListener {
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build()
            )
            val logearseIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()

            respuestaLoginAuthui.launch(logearseIntent)
        }
        val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
        btnLogout.setOnClickListener { seDeslogeo() }
    }

    private val respuestaLoginAuthui = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ){
        res: FirebaseAuthUIAuthenticationResult->
        if (res.resultCode === RESULT_OK){
            if(res.idpResponse != null){
                seLogeo(res.idpResponse!!)
            }
        }
    }
    fun seLogeo(
        res: IdpResponse
    ){
        val btnLogin : Button = findViewById<Button>(R.id.btn_login_firebase)
        val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
        btnLogout.visibility = View.VISIBLE
        btnLogin.visibility = View.INVISIBLE
        if(res.isNewUser == true){
            registrarUsuarioPorPrimeraVez(res)
        }
    }
    fun registrarUsuarioPorPrimeraVez(usuario: IdpResponse){

    }
    fun seDeslogeo(){
        val btnLogin = findViewById<Button>(R.id.btn_login_firebase)
        val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
        btnLogout.visibility = View.INVISIBLE
        btnLogin.visibility = View.VISIBLE
        FirebaseAuth.getInstance().signOut()
    }

}