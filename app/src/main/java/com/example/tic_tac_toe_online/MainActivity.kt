package com.example.tic_tac_toe_online

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {
    lateinit var toggle: ActionBarDrawerToggle
    val TAG = "MainActivity"
    private lateinit var logIn: SignInButton
    private lateinit var logOut: Button
    private lateinit var googleApiClient: GoogleApiClient
    private val SIGN_IN = 1
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var img: ImageView
    private lateinit var navigationView: NavigationView
    private lateinit var name: TextView
    private var signedIn = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigationView = findViewById(R.id.navigationView)

        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.item1 -> {
                    Toast.makeText(this, "Sign Out clicked", Toast.LENGTH_SHORT).show()
                    if (signedIn)
                    {
                        mGoogleSignInClient.signOut()
                            .addOnCompleteListener {
                                if (it.isSuccessful)
                                {
                                    Toast.makeText(this, "Sign Out successfull", Toast.LENGTH_SHORT).show()
                                    img.setImageResource(R.drawable.guest)
                                    name.text = R.string.guest.toString()
                                }
                                else
                                {
                                    Toast.makeText(this, "Sign Out successfull", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                }


            }
            true
        }
        val online_btn: Button = findViewById(R.id.b_online)
        val offline_btn: Button = findViewById(R.id.b_offline)
        logIn = findViewById(R.id.signIn)
        logOut = findViewById(R.id.signOut)


        navigationView = findViewById(R.id.navigationView)
        val headerView = LayoutInflater.from(this).inflate(R.layout.nav_header, navigationView, false)
        navigationView.addHeaderView(headerView)

        img = headerView.findViewById(R.id.profilePic)
        name = headerView.findViewById(R.id.name)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        logIn.setOnClickListener {
            signIn()
        }

        logOut.setOnClickListener {
            signOut()
        }

        val online = View.OnClickListener { v ->
            val b = v as Button
            startActivity(Intent(this, OnlineConnection::class.java))

        }

        val offline = View.OnClickListener { v ->
            val b = v as Button
            val intent = Intent(this, OfflinePlay::class.java)
            startActivity(intent)

        }
        online_btn.setOnClickListener(online)
        offline_btn.setOnClickListener(offline)


    }

    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener {
                if (it.isSuccessful)
                {
                    Toast.makeText(this, "Sign Out successfull", Toast.LENGTH_SHORT).show()
                    img.setImageResource(R.drawable.guest)
                    name.text = R.string.guest.toString()
                }
                else
                {
                    Toast.makeText(this, "Sign Out successfull", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
        {
            return true
        }
        else
        {

        }
        return super.onOptionsItemSelected(item)
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
            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d(TAG, "signInResult:failed code=" + e.statusCode)
            updateUI(null)
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null)
        {
            signedIn = true
//            val img = account.photoUrl
            Picasso.get().load(account.photoUrl).placeholder(R.mipmap.ic_launcher).into(img)
            name.text = ""
            name.text = account.givenName
        }

    }

//    private fun updateUI(acc: GoogleSignInAccount?) {
//        if (acc != null)
//        {
//
//        }
//
//    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("Not yet implemented")
    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null)
        {
            updateUI(account)
            Log.d(TAG, "account is not null")
        }
    }


}