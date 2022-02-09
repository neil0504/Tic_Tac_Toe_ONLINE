package com.example.tic_tac_toe_online.LoginFragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tic_tac_toe_online.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.squareup.picasso.Picasso

private const val TAG = "LoginFragment"
class LoginFragment(context: Context, profileImg: ImageView, name: TextView) : Fragment(R.layout.fragment_login) {
    var profImg = profileImg
    var profName = name
    private lateinit var googleSignInButton: SignInButton
    private lateinit var guestLogInButton: Button

    private var thisContext: Context = context

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val SIGN_IN = 1

    private lateinit var mListener: AttachLogin

    interface AttachLogin{
        fun executeUpdateUI(acc: GoogleSignInAccount?)
    }

    fun setAttachLoginListener(listener: AttachLogin){
        mListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG_MAIN_ACTIVITY, "LoginFragment onCreate: Start")
//        thisContext = view.context

        googleSignInButton = view.findViewById(R.id.googleSignIn)
        guestLogInButton = view.findViewById(R.id.guestLogIn)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(thisContext, gso)

        googleSignInButton.setOnClickListener {
            signIn()
        }

        guestLogInButton.setOnClickListener {
            loginAsGuest()
            activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }
        Log.d(TAG_MAIN_ACTIVITY, "LoginFragment onCreate: End")

    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == SIGN_IN)
        {
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
            mListener.executeUpdateUI(account)
            Log.d(TAG, "handleSignInResult: Account sent")
            activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d(TAG, "signInResult:failed code=" + e.statusCode)
//            mListener.executeUpdateUI(null)
            Log.d(TAG, "handleSignInResult: null sent")
            loginAsGuest()
            activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }
//        finally {
//            loginAsGuest()
//            Log.d(TAG, "handleSignInResult: Loging In as Guest. Error Occured during SignIn")
//        }
    }
    fun loginAsGuest(){
        signedInGuest = true
        signedInGoogle = false
        val db = MyDatabaseHelperGuest(thisContext)
        val cur = db.readAllData()
        if (cur == null){
            guestId = "Guest_1"
            guestName = guestId
            player = PlayerDetails(thisContext, guestId, guestName, null, null)
//            db.addRecord(guestId!!, guestName!!)
            db.addRecord(player.getID(), player.getName())
//            Picasso.get().load(guestDrawable).placeholder(R.mipmap.ic_launcher).into(profImg)
            Picasso.get().load(player.getPhotoURL()).placeholder(R.mipmap.ic_launcher).into(profImg)
//            profName.text = guestId
            profName.text = player.getName()

        }else{
            cur.use {
                while (it.moveToFirst()){
                    guestId = it.getString(1)
                    guestName = it.getString(2)
                }
            }
            player = PlayerDetails(thisContext, guestId, guestName, null, null)
//            Picasso.get().load(guestDrawable).placeholder(R.mipmap.ic_launcher).into(profImg)
            Picasso.get().load(player.getPhotoURL()).placeholder(R.mipmap.ic_launcher).into(profImg)
//            profName.text = guestId
            profName.text = player.getName()
        }
        FirebaseMessaging.getInstance().token.addOnSuccessListener { result ->
            if(result != null){
                myToken = result
                Log.d("TAG_MAIN_ACTIVITY", "Got the Token Creater= $myToken")
                // DO your thing with your firebase token
            }
        }
        Toast.makeText(thisContext, "Logged in as Guest", Toast.LENGTH_SHORT).show()
    }


}