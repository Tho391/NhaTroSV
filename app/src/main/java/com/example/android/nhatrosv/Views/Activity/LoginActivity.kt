package com.example.android.nhatrosv.Views.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.example.android.nhatrosv.R
import com.google.android.gms.common.SignInButton


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var signInButton: SignInButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signInButton = findViewById(R.id.sign_in_button)
        signInButton.setOnClickListener(this)

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

    }

    override fun onClick(p0: View?) {
        when (p0?.id){
            R.id.sign_in_button ->{
                lauchActivity<MainActivity> {  }
            }
        }
    }
}
