package com.neilkrishna_kabara.tic_tac_toe_online

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.neilkrishna_kabara.tic_tac_toe_online.databinding.ActivitySplashScreenBinding

private const val TAG = "SplashScreen"
class SplashScreen : AppCompatActivity() {
    private val binding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Log.d(TAG, "onCreate: Start")
        val sharedPreferences = getSharedPreferences("GuestData", MODE_PRIVATE)
        val wasGuestLoggedIn = sharedPreferences.getBoolean("wasGuestLoggedIn", false)
        val account = GoogleSignIn.getLastSignedInAccount(this.applicationContext)
        val bundle: Bundle = Bundle()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("wasGuestLoggedIn", wasGuestLoggedIn)
            startActivity(intent)
            finish()
        }, 4000)
        Log.d(TAG, "onCreate: End")


    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: Start")
        val intent = Intent(this, MainActivity::class.java)
        if (googleSignedInAccount == null) {
            val wasGuestLoggedIn = getSharedPreferences("GuestData", MODE_PRIVATE).getBoolean("wasGuestLoggedIn", false)
            if (!wasGuestLoggedIn)
            {
                intent.putExtra("intent_data_isGoogleSignIn", false)
                intent.putExtra("intent_data_isGuestSignIn", false)
            }else{
                intent.putExtra("intent_data_isGoogleSignIn", true)
                intent.putExtra("intent_data_isGuestSignIn", wasGuestLoggedIn)
            }
        }else{
            intent.putExtra("intent_data_isGoogleSignIn", true)
            intent.putExtra("intent_data_isGuestSignIn", false)

        }
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent)
            finish()
        }, 4000)

        Log.d(TAG, "onStart: End")

    }
}