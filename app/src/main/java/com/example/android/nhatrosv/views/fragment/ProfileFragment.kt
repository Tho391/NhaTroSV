package com.example.android.nhatrosv.views.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.android.nhatrosv.api.GoogleSignInClientHelper
import com.example.android.nhatrosv.R
import com.example.android.nhatrosv.utils.CircleTransform
import com.example.android.nhatrosv.models.User
import com.example.android.nhatrosv.views.activity.LoginActivity
import com.example.android.nhatrosv.utils.TOAST
import com.example.android.nhatrosv.utils.launchActivity
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment(), View.OnClickListener {


    lateinit var linearLayout: LinearLayout
    lateinit var imageViewProfile: ImageView
    lateinit var textViewName: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)
        //return super.onCreateView(inflater, container, savedInstanceState)

        linearLayout = rootView.findViewById(R.id.linear_logout)
        linearLayout.setOnClickListener(this)

        imageViewProfile = rootView.findViewById(R.id.imageView_profile)
        textViewName = rootView.findViewById(R.id.textView_name)

        val user = activity?.intent?.getSerializableExtra("account") as User?
        if (user != null) {
            textViewName.text = user.name
            Picasso.get()
                .load(Uri.parse(user.photoUrl))
                .transform(CircleTransform())
                .into(imageViewProfile)
            TOAST("email nè: ${user.email}", Toast.LENGTH_LONG)
        }

        return rootView
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.linear_logout -> {
                signOut()
                p0.context.launchActivity<LoginActivity> { }
            }
        }
    }

    private fun signOut() {
        TOAST("log out", Toast.LENGTH_SHORT)
        activity?.finish()
        GoogleSignInClientHelper.signOut()
    }

}
