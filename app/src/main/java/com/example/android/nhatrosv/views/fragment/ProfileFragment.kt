package com.example.android.nhatrosv.views.fragment

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.android.nhatrosv.R
import com.example.android.nhatrosv.api.GoogleSignInClientHelper
import com.example.android.nhatrosv.models.Response
import com.example.android.nhatrosv.models.User
import com.example.android.nhatrosv.utils.CircleTransform
import com.example.android.nhatrosv.utils.TOAST
import com.example.android.nhatrosv.utils.clear
import com.example.android.nhatrosv.utils.launchActivity
import com.example.android.nhatrosv.views.activity.LoginActivity
import com.example.android.nhatrosv.views.activity.OnDataReceivedListener
import com.example.android.nhatrosv.views.activity.ProfileActivity
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment(), View.OnClickListener, OnDataReceivedListener {


    lateinit var linearLogout: LinearLayout
    lateinit var linearChangePass: LinearLayout
    lateinit var linearInfo: LinearLayout
    lateinit var imageViewProfile: ImageView
    lateinit var textViewName: TextView

    var response: Response? = null
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)
        //return super.onCreateView(inflater, container, savedInstanceState)

        sharedPreferences =
            requireContext().getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)

        linearLogout = rootView.findViewById(R.id.linear_logout)
        linearLogout.setOnClickListener(this)
        linearChangePass = rootView.findViewById(R.id.linear_changePass)
        linearChangePass.setOnClickListener(this)
        linearInfo = rootView.findViewById(R.id.linear_info)
        linearInfo.setOnClickListener(this)

        imageViewProfile = rootView.findViewById(R.id.imageView_profile)
        textViewName = rootView.findViewById(R.id.textView_name)

        user = activity?.intent?.getSerializableExtra("account") as User
        if (user != null) {
            textViewName.text = user.name
            Picasso.get()
                .load(Uri.parse(user.photoUrl))
                .transform(CircleTransform())
                .into(imageViewProfile)
            //TOAST("email nè: ${user.email}", Toast.LENGTH_LONG)
        }

        return rootView
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.linear_logout -> {
                signOut()
                p0.context.launchActivity<LoginActivity> { }
            }
            R.id.linear_changePass -> {
                showDialogChangePass()
            }
            R.id.linear_info -> {
                p0.context.launchActivity<ProfileActivity> { }
            }
        }
    }

    private fun showDialogChangePass() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_change_pass)
        val editTextCurrentPass: EditText = dialog.findViewById(R.id.editText_current_pass)
        val editTextNewPass: EditText = dialog.findViewById(R.id.editText_new_pass)
        val editTextConfirmPass: EditText = dialog.findViewById(R.id.editText_confirm_pass)
        val buttonConfirm: Button = dialog.findViewById(R.id.button_confirm)
        val buttonCancel: Button = dialog.findViewById(R.id.button_cancel)

        buttonConfirm.setOnClickListener {
            //todo gui request change pass
        }
        buttonCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun signOut() {
        sharedPreferences.clear()

        TOAST("log out", Toast.LENGTH_SHORT)
        activity?.finish()
        GoogleSignInClientHelper.signOut()
    }

    override fun onDataReceived(apartmentId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
