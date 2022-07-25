package com.neilkrishna_kabara.tic_tac_toe_online
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
import androidx.core.view.GravityCompat
import androidx.fragment.app.commit
import com.neilkrishna_kabara.tic_tac_toe_online.databinding.ActivityMainBinding
import com.neilkrishna_kabara.tic_tac_toe_online.login_fragment.LoginFragment
import com.neilkrishna_kabara.tic_tac_toe_online.model.PlayerDetails
import com.neilkrishna_kabara.tic_tac_toe_online.object_class.Data
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.messaging.FirebaseMessaging
import com.neilkrishna_kabara.tic_tac_toe_online.room_database.AppDatabases
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

interface FragmentStateCheck{
    fun state(state: Boolean)
}

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), LoginFragment.LoginFragmentInterface,NotificationFragment.NotificationFragmentInterface {

    private lateinit var binding: ActivityMainBinding

    //
    private val fragmentManager = supportFragmentManager

    // Views
    private lateinit var navigationView: NavigationView
    private lateinit var profileImg: ImageView
    private lateinit var name: TextView
    private lateinit var notificationCounterText: TextView
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var onlineBtn: Button
    private lateinit var offlineBtn: Button

    //data
    private var isFragmentOpen by Delegates.notNull<Boolean>()

    private lateinit var db: AppDatabases

    companion object {
        //        public var signedInGoogle = false
//        public var signedInGuest = false
        lateinit var mGoogleSignInClient: GoogleSignInClient
    }
    private var intentArrayIsGoogleSignedIn: Boolean? = null
    private var intentArrayIsGuestSignedIn: Boolean? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        wasGuestLoggedIn = intent.getBooleanExtra("wasGuestLoggedIn", false)
        intentArrayIsGoogleSignedIn = intent.getBooleanExtra("intent_data_isGoogleSignIn", false)
        intentArrayIsGuestSignedIn = intent.getBooleanExtra("intent_data_isGuestSignIn", false)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            // TODO: To be implemented
//            .requestIdToken()
            .requestId()
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        db = AppDatabases.getDatabase(this)

        isFragmentOpen = false

        val drawerLayout = binding.drawerLayout
        val toolbar = binding.toolbar
        val bell = toolbar.findViewById<ImageView>(R.id.notif_icon)
        val view = toolbar.findViewById<View>(R.id.bell)
        notificationCounterText = view.findViewById(R.id.counter)

        navigationView = binding.navigationView
        val headerView =
            LayoutInflater.from(this).inflate(R.layout.nav_header, navigationView, false)
        navigationView.addHeaderView(headerView)
        profileImg = headerView.findViewById(R.id.profilePic)
        name = headerView.findViewById(R.id.name)

        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,  R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        onlineBtn = binding.bOnline
        offlineBtn = binding.bOffline

        val obj = NotificationFragment(this, this)
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

        bell.setOnClickListener {
            isFragmentOpen = true
            Toast.makeText(this, "Bell Clicked", Toast.LENGTH_SHORT).show()
            fragmentManager.commit {
                replace(R.id.notif_fragment_container, obj, "NotificationFragment")
                addToBackStack(null)
            }
        }

        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.signOut -> {
                    Toast.makeText(this, "Sign Out clicked", Toast.LENGTH_SHORT).show()
                    if (Data.isSignedInGoogle == true){
                        mGoogleSignInClient.signOut().addOnCompleteListener{ v->
                            if (v.isSuccessful){
                                Toast.makeText(this, "Sign Out successful", Toast.LENGTH_SHORT).show()
                                guestLogin()
                            }
                        }
                    }
                }
                R.id.invite_new_player ->{
                    Toast.makeText(this, "Invite new Player clicked", Toast.LENGTH_SHORT).show()
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
//                    val fragmentAlreadyExists: Boolean
                    val t = fragmentManager.findFragmentById(R.id.loginFragmentContainer)
                    val fragmentAlreadyExists = if(t != null){
                        t.isVisible
                    }else{
                        Toast.makeText(this, "t was null", Toast.LENGTH_SHORT).show()
                        false
                    }

                    if (Data.isSignedInGoogle == false && !fragmentAlreadyExists){
                        drawerLayout.closeDrawer(GravityCompat.START)
                        startFragment(true)
                    }else if (Data.isSignedInGoogle == true){
                        Toast.makeText(this, "U r already Signed In", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            true
        }

        onlineBtn.setOnClickListener {
            if (Data.isSignedInGoogle == true || Data.isSignedInGuest==true) {
                startActivity(Intent(this, OnlineConnection::class.java))
            }
        }

        offlineBtn.setOnClickListener {
            if (Data.isSignedInGoogle == true || Data.isSignedInGuest==true) {
                startActivity(Intent(this, OfflinePlay::class.java))
            }

        }

//        if (googleSignedInAccount == null) {
//            if (wasGuestSignedIn){
//                Log.d(TAG, "No Google Account found but Guest Login found. Logging In as Guest")
//                updateUIAfterSignIn(null, guestId = Data.guestId, guestName = Data.guestName)
//                startFragment(true)
//
//            }else{
//                Data.isSignedInGoogle = false
//                Data.isSignedInGuest = false
////                LoginStatus.NotSignedIn.statusValue
//
//                startFragment(false)
//            }
//        }else{
//            updateUIAfterSignIn(googleSignedInAccount)
//            Log.d(TAG, "Google Account Found")
//        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
        {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "Start: onStart")

        if (intentArrayIsGoogleSignedIn != null && intentArrayIsGuestSignedIn != null) {
            Log.d(TAG, "Intent Array not null")
//            val isGoogleSignedIn = intentArray!![0]
//            val isGuestSignIn = intentArray!![1]
            if (intentArrayIsGoogleSignedIn as Boolean){
                val account = GoogleSignIn.getLastSignedInAccount(this.applicationContext)
                updateUIAfterSignIn(account)
                Log.d(TAG, "Google Account Found")
            }else{
                if (intentArrayIsGuestSignedIn as Boolean){
                    Log.d(TAG, "No Google Account found but Guest Login found. Logging In as Guest")

                    updateUIAfterSignIn(null, guestId = Data.guestId, guestName = Data.guestName)
                    startFragment(true)
                }else{
                    Log.d(TAG, "No Google Account found and no Guest Login found")
                    Data.isSignedInGoogle = false
                    Data.isSignedInGuest = false
                    startFragment(false)
                }
            }
            intentArrayIsGoogleSignedIn = null
            intentArrayIsGuestSignedIn = null
        }else{
            Log.d(TAG, "Intent Array null")
        }

//        val account = GoogleSignIn.getLastSignedInAccount(this)
//        if (account != null) {
//            updateUIAfterSignIn(account)
//            Log.d(TAG, "Google Account Found")
//
//        }
//        /* check if GuestSignIn is previously present.
//            this means that app was previously stated and user Signed Out or logged in as Guest.
//            Guest Login is done on starting if user chooses GuestLogin or everytime after user Sign's Out.
//             */
//        else {
//
////            val sharedPreferences = getSharedPreferences("GuestData", MODE_PRIVATE)
////            val wasGuestSignedIn = sharedPreferences.getBoolean("wasGuestSignedIn", false)
//            if (wasGuestSignedIn){
//                Log.d(TAG, "No Google Account found but Guest Login found. Logging In as Guest")
//                updateUIAfterSignIn(null, guestId = Data.guestId, guestName = Data.guestName)
//                startFragment(true)
//
//            }else{
//                Data.isSignedInGoogle = false
//                Data.isSignedInGuest = false
////                LoginStatus.NotSignedIn.statusValue
//
//                startFragment(false)
//            }
//            val userDao = db.userDao()
//            val previousPlayerDao = db.previousPlayerDoa()
//            val invitedPlayerDao = db.invitedPlayersDao()
//            GlobalScope.launch {
//                val data = userDao.getAll()
//                val data2 = previousPlayerDao.getAll()
//                val data3 = invitedPlayerDao.getAll()
//                Log.d(TAG, "Data from GUEST room database = $data")
//                Log.d(TAG, "Data from PREVIOUS PLAYER room database = $data2")
//                Log.d(TAG, "Data from INVITED PLAYER room database = $data3")
//            }
//
//            val db = MyDatabaseHelperGuest(this)
//            val cur = db.readAllData()
//            // Previous GUEST LOGIN was PRESENT therefore login as guest and show the fragment for only GOOGLE SIGN IN
//            if (cur != null) {
//                Log.d(TAG, "No Google Account found but Guest Login found. Logging In as Guest")
//                cur.use {
//                    while (it.moveToFirst()) {
//                        Data.guestId = it.getString(1)
//                        Data.guestName = it.getString(2)
//                    }
//                }
//                // Logging In and creating PLAYER CREDENTIALS using ALREADY EXISTING Guest Credentials
//                updateUIAfterSignIn(null, guestId = Data.guestId, guestName = Data.guestName)
////                Data.player = PlayerDetails(this, Data.guestId, Data.guestName, null, null)
//
//
//                startFragment(true)
//
//            }
//            // Previous GUEST LOGIN was ABSENT therefore only show the fragment for BOTH: GOOGLE SIGN IN and GUEST
//            else {
//                Log.d(TAG, "Neither Google Account nor Guest Login found")
//                Data.isSignedInGoogle = false
//                Data.isSignedInGuest = false
////                LoginStatus.NotSignedIn.statusValue
//
//                startFragment(false)
//            }
//
//        }
        Log.d(TAG, "Exit: onStart")
    }

//    private fun checkDatabaseForGuestLogin(): Cursor? {
//        val db = MyDatabaseHelperGuest(this)
//        val cur = db.readAllData()
//        return cur
//    }

    private fun guestLogin() {
        val db = MyDatabaseHelperGuest(this)
        val cur = db.readAllData()
        // Previous GUEST LOGIN was PRESENT therefore login as guest and show the fragment for only GOOGLE SIGN IN
        if (cur != null) {
            Log.d(TAG, "No Google Account found but Guest Login found. Logging In as Guest")
            cur.use {
                while (it.moveToFirst()) {
                    Data.guestId = it.getString(1)
                    Data.guestName = it.getString(2)
                }
            }
            // Logging In and creating PLAYER CREDENTIALS using ALREADY EXISTING Guest Credentials
            updateUIAfterSignIn(null, guestId = Data.guestId, guestName = Data.guestName)

        }
        // Previous GUEST LOGIN was ABSENT therefore only show the fragment for BOTH: GOOGLE SIGN IN and GUEST
        else {
            Log.d(TAG, "No Guest Login found. Creating One...")
            Data.player = PlayerDetails(this, Data.guestId, Data.guestName, null, null)
            db.addRecord(Data.player.getID(), Data.player.getName())
            Data.isSignedInGoogle = false
            Data.isSignedInGuest = true
            updateUIAfterSignIn(null, guestId = Data.guestId, guestName = Data.guestName)

        }
        FirebaseMessaging.getInstance().token.addOnSuccessListener { result ->
            if (result != null) {
                Data.myToken = result
                Log.d("TAG", "Got the Token Creator= $Data.myToken")
                // DO your thing with your firebase token
            }
        }
        Toast.makeText(this, "Logged in as Guest", Toast.LENGTH_SHORT).show()

    }

    private fun startFragment(onlyGoogleLogin: Boolean) {
        Log.d(TAG, "MainActivity: startFragment: Start")
        val loginFragment = LoginFragment(onlyGoogleLogin, this)
        fragmentManager.commit {
            setCustomAnimations(R.anim.slide_up, R.anim.scale_down )
            replace(R.id.loginFragmentContainer, loginFragment, "LoginFragment")
            addToBackStack(null)
        }
    }

    private fun updateUIAfterSignIn(
        account: GoogleSignInAccount?,
        guestId: String? = null,
        guestName: String? = null
    ) {
        Log.d(TAG, "updateUIAfterSignIn: Start")

        // Guest Login
        if (account == null) {
            Log.d(TAG, "updateUIAfterSignIn: Guest Account Login starts")

            Data.player = PlayerDetails(this, guestId, guestName, null, null)
            Picasso.get().load(Data.player.getPhotoURL()).placeholder(R.mipmap.ic_launcher)
                .into(profileImg)
            name.text = Data.player.getName()

            Data.isSignedInGoogle = false
            Data.isSignedInGuest = true

        }
        // Google Login
        else {
            Log.d(TAG, "updateUIAfterSignIn: Google Account Found")
            Data.account = account
            Data.player = PlayerDetails(
                this,
                account.id,
                account.displayName,
                account.email,
                account.photoUrl ?: null
            )
//            Picasso.get().load(account.photoUrl).placeholder(R.mipmap.ic_launcher).into(profileImg)
            Picasso.get().load(Data.player.getPhotoURL()).placeholder(R.mipmap.ic_launcher)
                .into(profileImg)
            name.text = Data.player.getName()

            Data.isSignedInGoogle = true
            Data.isSignedInGuest = false

            FirebaseMessaging.getInstance().token.addOnSuccessListener { result ->
                if (result != null) {
                    Data.myToken = result
                    Log.d(TAG, "updateUIAfterSignIn: Got the Token Creator= ${Data.myToken}")
                    // DO your thing with your firebase token
                }
            }

            clearGuestData()

            Toast.makeText(this, "updateUIAfterSignIn: SignIn Successful", Toast.LENGTH_SHORT)
                .show()

        }
        Log.d(TAG, "updateUIAfterSignIn: Exit")
    }

    private fun clearGuestData() {
        val db = MyDatabaseHelperGuest(this)
        db.readAllData()?.use {
            if (it.moveToFirst()) {
                while (!it.isAfterLast) {
                    val guestId = it.getString(1)
//                    Data.guestName = it.getString(2)
                    db.deleteRow(guestId)
                    Log.d(TAG, "clearGuestData: Cleared guest Entry")
                }
            }
        }
    }

    override fun loginFragmentUpdateUI(
        account: GoogleSignInAccount?,
    ) {
        if (account == null) {
            guestLogin()
        } else {
            updateUIAfterSignIn(account)
        }
    }

    override fun setCounterToZero() {
        notificationCounterText.text = NotificationCounter.setCounterToZero()
    }

    override fun onBackPressed() {
        when(fragmentManager.findFragmentById(R.id.loginFragmentContainer)){
            is LoginFragment -> {
                if(Data.isSignedInGoogle==false && Data.isSignedInGuest==false){
                    guestLogin()
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
}