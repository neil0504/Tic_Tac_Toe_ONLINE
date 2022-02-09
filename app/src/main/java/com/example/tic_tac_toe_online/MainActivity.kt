package com.example.tic_tac_toe_online

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
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
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.commit
import com.example.tic_tac_toe_online.LoginFragment.LoginFragment
import com.example.tic_tac_toe_online.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.messaging.FirebaseMessaging
import com.squareup.picasso.Picasso
import kotlin.properties.Delegates

interface FragmentStateCheck{
    fun state(state: Boolean)
}

var account: GoogleSignInAccount? = null
var myToken: String? = null
@SuppressLint("StaticFieldLeak")
lateinit var notificationCounterText: TextView
@SuppressLint("StaticFieldLeak")
lateinit var MainActivityContext: Context

var signedInGoogle = false
var signedInGuest = false

var guestId: String = "Guest_1"
var guestName: String = guestId
const val guestDrawable = R.drawable.guest
const val TAG_MAIN_ACTIVITY = "MainActivityTAG"
lateinit var player: PlayerDetails
class MainActivity : AppCompatActivity(){
    private lateinit var toggle: ActionBarDrawerToggle

//    private lateinit var logIn: SignInButton
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var profileImg: ImageView
    private lateinit var navigationView: NavigationView
    private lateinit var name: TextView

    private lateinit var binding: ActivityMainBinding
    var isFragmentOpen by Delegates.notNull<Boolean>()
    private lateinit var loginFragment: LoginFragment


    private lateinit var onlineBtn: Button
    private lateinit var offlineBtn: Button

    private val fragmentManager = supportFragmentManager

//    private lateinit var mListener: SignOutInterface
//
//    interface SignOutInterface{
//        fun signOut()
//    }
//
//    fun setSignOutInterfaceListener(listener: SignOutInterface){
//        mListener = listener
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val fragmentmanagaer = supportFragmentManager

        MainActivityContext = this

        isFragmentOpen = false

//        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val drawerLayout = binding.drawerLayout
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val bell = toolbar.findViewById<ImageView>(R.id.notif_icon)
        val view = toolbar.findViewById<View>(R.id.bell)
        notificationCounterText= view.findViewById(R.id.counter)
//        navigationView = findViewById(R.id.navigationView)
        navigationView = binding.navigationView
        val headerView = LayoutInflater.from(this).inflate(R.layout.nav_header, navigationView, false)
//        val headerView = navigationView.inflateHeaderView(R.layout.nav_header)
        navigationView.addHeaderView(headerView)
        profileImg = headerView.findViewById(R.id.profilePic)
        name = headerView.findViewById(R.id.name)


        // Starting LoginFragment if the User is not Signed In Already as the App starts.
        loginFragment = LoginFragment(this, profileImg, name)

        loginFragment.setAttachLoginListener(object : LoginFragment.AttachLogin{
            override fun executeUpdateUI(acc: GoogleSignInAccount?) {
                updateUI(acc)
            }

        })
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,  R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        onlineBtn = findViewById(R.id.b_online)
        offlineBtn = findViewById(R.id.b_offline)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val obj = NotificationFragment(notificationCounterText)

        MyDatabaseHelper_Invites.setUpdateUIInterfaceListener(object : MyDatabaseHelper_Invites.UpdateUIInterface{
            override fun updateUI(position: Int) {
                if (isFragmentOpen){
                    obj.state(true)
                }else{
                    Handler(mainLooper).post {
                        val num = NotificationCounter.increaseNumber()
                        notificationCounterText.text = num
                    }

                }

            }

        })

//        val obj2 = NotificationCounter(toolbar.findViewById(R.id.bell))


        bell.setOnClickListener{
            isFragmentOpen = true
            val num = NotificationCounter.increaseNumber()
            notificationCounterText.text = num
            Toast.makeText(this, "Bell Clicked", Toast.LENGTH_SHORT).show()
//            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.replace(R.id.notif_fragment_container, obj, "NotificationFragment")
            fragmentTransaction.commit()
        }
        // Code to handle selection in
        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.item1 -> {
                    Toast.makeText(this, "Sign Out clicked", Toast.LENGTH_SHORT).show()
                    if (signedInGoogle)
                    {
//                        mListener.signOut()
                        mGoogleSignInClient.signOut()
                            .addOnCompleteListener { v ->
                                if (v.isSuccessful)
                                {
                                    Toast.makeText(this, "Sign Out successfull", Toast.LENGTH_SHORT).show()
                                    loginFragment.loginAsGuest()
//                                    profileImg.setImageResource(R.drawable.guest)
//                                    name.text = getString(R.string.guest)
                                }
                                else
                                {
                                    Toast.makeText(this, "Sign Out Uncessfull", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                    else{
                        Toast.makeText(this, "No account found to Signout", Toast.LENGTH_SHORT).show()
                    }

                }
                R.id.invite_new_player ->{
                    Toast.makeText(this, "Sign Out clicked", Toast.LENGTH_SHORT).show()
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    startActivity(shareIntent)
                }

                R.id.signIn ->{
                    val fragmentAlreadyExixts: Boolean
                    val t = fragmentManager.findFragmentById(R.id.loginFragmentContainer)
                    fragmentAlreadyExixts = if(t != null){
                        t.isVisible
                    }else{
                        Toast.makeText(this, "t was null", Toast.LENGTH_SHORT).show()
                        false
                    }

//                    when(){
//                        is LoginFragment -> {
//                            fragmentAlreadyExixts = true
//                            Toast.makeText(this, "Fragment already exists", Toast.LENGTH_SHORT).show()
//                        }
//                    }
                    if (!signedInGoogle && !fragmentAlreadyExixts){
                        drawerLayout.closeDrawer(GravityCompat.START)
                        startFragment()
                    }
                    else if (signedInGoogle){
                        Toast.makeText(this, "U r already Signed In", Toast.LENGTH_SHORT).show()
                    }
                }


            }
            true
        }

        onlineBtn.setOnClickListener {
            if (signedInGoogle || signedInGuest) {
                startActivity(Intent(this, OnlineConnection::class.java))
            }
        }

        offlineBtn.setOnClickListener {
            if (signedInGoogle || signedInGuest) {
                startActivity(Intent(this, OfflinePlay::class.java))
            }

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
        {
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    private fun updateUI(acc: GoogleSignInAccount?) {
        if (acc != null) {
//            Log.d(TAG_MAIN_ACTIVITY, "Inside UpdateUI Method: Name = ${acc.displayName} and profileURL = ${acc.photoUrl}")
            account = acc
            signedInGoogle = true
            signedInGuest = false
            player = PlayerDetails(this, acc.id!!, acc.displayName!!, acc.email, acc.photoUrl?: null)
//            Picasso.get().load(acc.photoUrl).placeholder(R.mipmap.ic_launcher).into(profileImg)
            Picasso.get().load(player.getPhotoURL()).placeholder(R.mipmap.ic_launcher).into(profileImg)
            name.text = ""
//            name.text = acc.displayName
            name.text = player.getName()

//            val a = PlayerDetails.getInstance(acc.id!!, acc.displayName!!, acc.email!!, acc.photoUrl!!.toString())

            FirebaseMessaging.getInstance().isAutoInitEnabled = true

            FirebaseMessaging.getInstance().token.addOnSuccessListener { result ->
                if(result != null){
                    myToken = result
                    Log.d("TAG_MAIN_ACTIVITY", "Got the Token Creater= $myToken")
                    // DO your thing with your firebase token
                }
            }
            Toast.makeText(this, "SignIn Successful", Toast.LENGTH_SHORT).show()
        }

    }

    private fun startFragment(){
        fragmentManager.commit {
            setCustomAnimations(
                R.anim.slide_up,
                R.anim.scale_down
            )
            replace(R.id.loginFragmentContainer, loginFragment, "LoginFragment")
            addToBackStack(null)
        }
    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        Log.d(TAG_MAIN_ACTIVITY, "onStart: Start")
        if (account != null)
        {
            updateUI(account)
            Log.d(TAG_MAIN_ACTIVITY, "account is not null")
            signedInGoogle = true
            signedInGuest = false
        }else{
            Log.d(TAG_MAIN_ACTIVITY, "onStart: Start with account = null")
            signedInGoogle = false
            signedInGuest = false
            startFragment()
        }
    }

    override fun onBackPressed() {
//        TODO: If backpressed on Login page as well b4 signing into google (means no SignIn happened as of google or of guest)....fix that
        val f = fragmentManager.findFragmentById(R.id.loginFragmentContainer)
        val f1 = fragmentManager.findFragmentById(R.id.notif_fragment_container)

        when(f){
            is LoginFragment -> {
                if(!signedInGoogle && !signedInGuest){
                    loginFragment.loginAsGuest()
                }
            }
        }

        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            isFragmentOpen = false
        } else {
            super.onBackPressed()
        }
    }

    //    private fun signIn() {
//        val signInIntent: Intent = mGoogleSignInClient.signInIntent
//        startActivityForResult(signInIntent, SIGN_IN)
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(requestCode == SIGN_IN)
//        {
//            val task: Task<GoogleSignInAccount> =
//                GoogleSignIn.getSignedInAccountFromIntent(data)
//            handleSignInResult(task)
//        }
//    }

//    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
//        try {
//            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
//
//            // Signed in successfully, show authenticated UI.
//            updateUI(account)
//        } catch (e: ApiException) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.d(TAG, "signInResult:failed code=" + e.statusCode)
//            updateUI(null)
//        }
//    }

//    supportFragmentManager.commit {
//            setCustomAnimations(
//                enter=R.anim.slide_up,
//            exit=R.anim.fade_out,
//            popEnter=R.anim.scale_down,
//            popExit=R.anim.fade_in)
//        }
//        supportFragmentManager.beginTransaction().setCustomAnimations(
//            enter = R.anim.slide_up,
//            exit = R.anim.slide_down
//        popEnter = R.anim).addToBackStack(null).replace(R.id.loginFragmentContainer, loginFragment).commit()

//        logIn.setOnClickListener {
//            signIn()
//        }


}