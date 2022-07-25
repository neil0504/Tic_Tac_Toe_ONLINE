package com.neilkrishna_kabara.tic_tac_toe_online.login_fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.neilkrishna_kabara.tic_tac_toe_online.MainActivity
import com.neilkrishna_kabara.tic_tac_toe_online.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import java.lang.Exception

private const val TAG = "LoginFragment"
class LoginFragment(private val onlyGoogleSignIn: Boolean, private val listener: LoginFragmentInterface) : Fragment() {

    interface LoginFragmentInterface{
        fun loginFragmentUpdateUI(account: GoogleSignInAccount?)
    }
    private lateinit var binding: FragmentLoginBinding


    // Constants:
    private val SIGN_IN = 1

    // views
    private lateinit var googleSignInButton: SignInButton
    private lateinit var guestLogInButton: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        googleSignInButton = binding.googleSignIn
        guestLogInButton = binding.guestLogIn

        if (onlyGoogleSignIn) {
            guestLogInButton.isEnabled = false
            guestLogInButton.visibility = View.GONE
        } else {
            guestLogInButton.isEnabled = true
            guestLogInButton.visibility = View.VISIBLE
        }

        googleSignInButton.setOnClickListener {
            signIn()
        }
        guestLogInButton.setOnClickListener {
            loginAsGuest()
        }
        Log.d(TAG, "LoginFragment onCreate: End")
    }

    private fun signIn() {
        val signInIntent: Intent = MainActivity.mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN) {
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        Log.d(TAG, "handleSignInResult: Start")
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
            listener.loginFragmentUpdateUI(account)
            activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }catch (e: ApiException){
            Log.d(TAG, "handleSignInResult: ApiException found ")

        }catch (e: Exception){
            Log.d(TAG, "handleSignInResult: Exception Found....throwing Exception")
            throw Exception("Exception occurred Details: ${e.message}")

        }finally {
            // TODO: login as Guest as Google Login failed and display a toast as well
        }
        Log.d(TAG, "handleSignInResult: End")
    }

    private fun loginAsGuest(){
        listener.loginFragmentUpdateUI(null)
        activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()

    }
}