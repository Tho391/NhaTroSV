package com.example.android.nhatrosv.api

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class GoogleSignInClientHelper(context: Context) {
    companion object Client {
        private lateinit var mGoogleSignInClient: GoogleSignInClient
        private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestId()
            .requestEmail()
            .requestProfile()
            .build()
        fun signOut(){
            mGoogleSignInClient.signOut()
        }
    }

    init {
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso)
    }


    fun getClient(): GoogleSignInClient {
        return mGoogleSignInClient
    }


}