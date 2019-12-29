package com.example.android.nhatrosv.views.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.android.nhatrosv.R
import com.example.android.nhatrosv.api.GoogleSignInClientHelper
import com.example.android.nhatrosv.models.Token
import com.example.android.nhatrosv.models.User
import com.example.android.nhatrosv.utils.TOAST
import com.example.android.nhatrosv.utils.launchActivity
import com.example.android.nhatrosv.utils.put
import com.example.android.nhatrosv.viewModels.LoginActivityViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
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
    val RC_PERM = 1005
    val RC_CALL = 1006
    val RC_FINE_LOCATION = 1007
    val RC_COARSE_LOCATION = 1008
    lateinit var signInButton: SignInButton
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var buttonLogin: Button
    lateinit var edtUser: EditText
    lateinit var edtPass: EditText
    lateinit var textViewRegister: TextView

    private val perms =
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CALL_PHONE
        )
    private val rationale = "We need to access your location & phone"
    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //init admod
        MobileAds.initialize(this)
//        val testDeviceIds = listOf("33BE2250B43518CCDA7DE426D04EE231")
//        val configuration =
//            RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
//        MobileAds.setRequestConfiguration(configuration)

        //Create an interstitial ad object
        mInterstitialAd = InterstitialAd(this)
        //mInterstitialAd.adUnitId = getString(R.string.ADMOD_UNIT_ID)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        signInButton = findViewById(R.id.sign_in_button)
        signInButton.setOnClickListener(this)

        buttonLogin = findViewById(R.id.button_login)
        buttonLogin.setOnClickListener(this)
        edtUser = findViewById(R.id.editText_username)
        edtPass = findViewById(R.id.editText_password)

        textViewRegister = findViewById(R.id.textView_register)
        textViewRegister.setOnClickListener(this)

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.


        mGoogleSignInClient = GoogleSignInClientHelper(this).getClient()
        requestPerm()

    }

    override fun onStart() {
        super.onStart()

        // [START on_start_sign_in]
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        // [START on_start_sign_in]
// Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            updateUI(account)
        }
        // [END on_start_sign_in]
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun signIn(user: String, pass: String) {
        //gửi request đến server
        //có token -> updateUI
        //lỗi -> hiển thị dialog

        val viewModel = LoginActivityViewModel()
        viewModel.login(user, pass).observe(this,
            Observer {
                if (it != null) {
                    saveToken(it)
                    updateUI()
                }
            })

    }

    private fun saveToken(token: Token) {
        val sharedPreferences = getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        sharedPreferences.put("token", token.accessToken)
    }

    private fun updateUI() {
        launchActivity<MainActivity> {}
        finish()
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val user: User

        Log.i("token", account.idToken)

        val id = account.id
        val textViewToken: TextView = findViewById(R.id.editText)
        textViewToken.text = account.idToken
        val displayName = account.displayName
        val email = account.email
        val photoUrl = account.photoUrl.toString()
        user = User(id, displayName, email, null, photoUrl, null, null)

        val viewModel = LoginActivityViewModel()
        viewModel.login(user)

        launchActivity<MainActivity> {
            this.putExtra("account", user)
        }
        finish()
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account =
                completedTask.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
            //updateUI(account)
            if (account != null)
                updateUI(account)

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("tài khoản", "signInResult:failed code=" + e.statusCode)
            //updateUI(null)
        }
    }

    private fun hasCallPerm(): Boolean {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CALL_PHONE))
            return true
        return false
    }

    private fun hasFineLocationPerm(): Boolean {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION))
            return true
        return false
    }

    private fun hasCoarseLocationPerm(): Boolean {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_COARSE_LOCATION))
            return true
        return false
    }

    private fun requestPerm() {
        if (!EasyPermissions.hasPermissions(this, *perms))
            EasyPermissions.requestPermissions(this, rationale, RC_PERM, *perms)

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.sign_in_button -> {
                if (!EasyPermissions.hasPermissions(this, *perms))
                    EasyPermissions.requestPermissions(this, rationale, RC_PERM, *perms)
                else
                    signIn()
            }
            R.id.button_login -> {
                if (!EasyPermissions.hasPermissions(this, *perms))
                    EasyPermissions.requestPermissions(this, rationale, RC_PERM, *perms)
                else {
                    val user = edtUser.text.toString()
                    val pass = edtPass.text.toString()
                    signIn(user, pass)
                }
            }
            R.id.textView_register -> {
                launchActivity<RegisterActivity> { }
            }
        }
    }


    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        //EasyPermissions.requestPermissions(this, rationale, requestCode, perms.toString())

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        // Some permissions have been granted\
        TOAST("Permission has been granted", Toast.LENGTH_SHORT)
        signIn()
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
            when (requestCode) {
                RC_SIGN_IN -> {
                    val task =
                        GoogleSignIn.getSignedInAccountFromIntent(data)
                    handleSignInResult(task)
                }
//                RC_COARSE_LOCATION -> requestPerm()
//                RC_CALL -> requestPerm()
//                RC_FINE_LOCATION -> requestPerm()
                RC_PERM -> {
                    requestPerm()
                }
            }
    }
}
