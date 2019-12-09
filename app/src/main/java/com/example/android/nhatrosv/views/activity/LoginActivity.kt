package com.example.android.nhatrosv.views.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.android.nhatrosv.api.GoogleSignInClientHelper
import com.example.android.nhatrosv.R
import com.example.android.nhatrosv.models.User
import com.example.android.nhatrosv.utils.launchActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import pub.devrel.easypermissions.EasyPermissions


class LoginActivity : AppCompatActivity(), View.OnClickListener,
    EasyPermissions.PermissionCallbacks {
    val RC_SIGN_IN = 1000
    lateinit var signInButton: SignInButton
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val perms =
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CALL_PHONE
        )
    val rationale = "We need to access your location"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signInButton = findViewById(R.id.sign_in_button)
        signInButton.setOnClickListener(this)

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.


        mGoogleSignInClient = GoogleSignInClientHelper(this).getClient()
        requestPerm()
    }

    fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account =
                completedTask.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
            //updateUI(account)
            val user:User
            if (account!= null){
                val displayName = account.displayName
                val email = account.email
                val photoUrl = account.photoUrl.toString()
                user = User(null,displayName,email,null,photoUrl)
                launchActivity<MainActivity> {
                    this.putExtra("account",user)
                }
                finish()
            }

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("tài khoản", "signInResult:failed code=" + e.statusCode)
            //updateUI(null)
        }
    }

    override fun onStart() {
        super.onStart()
        //val account = GoogleSignIn.getLastSignedInAccount(this)
    }

    private fun requestPerm() {
//        if (!EasyPermissions.hasPermissions(this, perms.toString())) {
//            // Do not have permissions, request them now
//            EasyPermissions.requestPermissions(
//                this, rationale,
//                1111, perms.toString()
//            )
//        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.sign_in_button -> {
                //launchActivity<MainActivity> {  }
                //launchActivity<MainActivity> {  }
                //launchActivity<MainActivity> { }
//                val intent = Intent(this,MainActivity::class.java)
//                startActivity(intent)
                //finish()

                signIn()
            }
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

        //EasyPermissions.requestPermissions(this, rationale, requestCode, perms.toString())
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        // Some permissions have been granted
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK)
            if (requestCode == RC_SIGN_IN) {
                val task =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            }
    }
}
