package com.example.android.nhatrosv.api

import android.content.Context
import android.content.res.Resources
import com.example.android.nhatrosv.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class GoogleSignInClientHelper(context: Context) {
    companion object Client {
        private const val server_id = "987077358640-thuupdsknad7bm7irea40c67k8sn3vn7.apps.googleusercontent.com"
        private lateinit var mGoogleSignInClient: GoogleSignInClient
        private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(server_id)
            .requestId()
            .requestEmail()
            .requestProfile()
            //.requestServerAuthCode(R.string.server_client_id.toString())
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