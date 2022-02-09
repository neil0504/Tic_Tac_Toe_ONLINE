package com.example.tic_tac_toe_online

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


//var cc: Context? = null
var joiningCodeFromIntent: String? = null
class OnlineConnection : AppCompatActivity() {
    private val TAG = "OnlineConnectionTAGTAG"

    lateinit var data: ArrayList<InvitationFragment_Lobby>

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online_connection)
        Log.d("TATA", "My Token -=-= $myToken")

        // New code when user clicks Notification and code is passed in intent
        val c = intent.extras
        if (c != null){
            joiningCodeFromIntent = c.get("code") as String?
        }
        else{
            joiningCodeFromIntent = null
        }

//        cc = this

        val obj = OnlineConnectionFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.replace(R.id.invitationContainer, obj)
        fragmentTransaction.commit()

        obj.setTurnOnFragment(object : OnlineConnectionFragment.TurnOnFragment{
            override fun turnOn(cursor: Cursor?) {
                val obj2 = InvitationFragment(cursor)

//                supportFragmentManager.commit {
//                    setReorderingAllowed(true)
//                    // Replace whatever is in the fragment_container view with this fragment
//                    replace<InvitationFragment>(R.id.invitationContainer, obj2)
//                }
                supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.invitationContainer, obj2).commit()
            }

        })


    }

    override fun onBackPressed() {
        when(supportFragmentManager.findFragmentById(R.id.invitationContainer)){
            is OnlineConnectionFragment -> {
                Log.d(TAG, "BackPressed from OnlineConnectionFragment")

            }
            is InvitationFragment -> {
                Log.d(TAG, "BackPressed from InvitationFragment")
                FirebaseDatabase.getInstance().reference.child("codes").addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val data = snapshot.children
//                        snapshot.child("codes/$code").ref.removeValue()
                        data.forEach {
                            if (it.value == code!!){
                                it.ref.removeValue()
                                Log.d("05042", "Child Removed for Code")
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
//                FirebaseDatabase.getInstance().reference.child("codes").child(code!!).removeValue()
                FirebaseDatabase.getInstance().reference.child("Creater_Joiner").child(code!!).removeValue()
//                FirebaseDatabase.getInstance().reference.child("").child(code!!).removeValue()
            }
        }
        super.onBackPressed()
    }

//    private fun addDataset2(sharedPreferences: SharedPreferences?) {
//        Log.d("*****", "Inside addDataset 2 method")
//        val data = DataSource_for_rv2_Invitation_Fragment.createDataSet(sharedPreferences)
//        invitationFragment_InvitesAdapter.submitList(data)
//    }
//
//    private fun initRecyclerView2() {
//        Log.d("*****", "Inside Init Recycler View 2 method")
//        recycler_view2.apply {
//            layoutManager = LinearLayoutManager(this@OnlineConnection)
//            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
//            addItemDecoration(topSpacingItemDecoration)
//            invitationFragment_InvitesAdapter = InvitationFragment_Invites_RecyclerViewAdapter()
//            adapter = invitationFragment_InvitesAdapter
//        }
//    }
//
//    private fun addDataset1() {
//        Log.d("*****", "Inside addDataset 1 method")
//        data = DataSource_for_rv1_Invitation_Fragment.createDataSet()
//        invitationfragment_LobbyRecyclerviewAdapter.submitList(data)
//    }
//
//    private fun initRecyclerView1() {
//        Log.d("*****", "Inside Init Recycler View 1 method")
//        recycler_view1.apply {
//            layoutManager = LinearLayoutManager(this@OnlineConnection)
//            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
//            addItemDecoration(topSpacingItemDecoration)
//            invitationfragment_LobbyRecyclerviewAdapter = InvitationFragment_Lobby_RecyclerView_Adapter()
//            adapter = invitationfragment_LobbyRecyclerviewAdapter
////            invitationfragment_LobbyRecyclerviewAdapter.setOnClickListenerr(InvitationFragment_Lobby_RecyclerView_Adapter.OnRowSelect{
//
////            })
//            invitationfragment_LobbyRecyclerviewAdapter.setOnItemListenerr(object : InvitationFragment_Lobby_RecyclerView_Adapter.OnItemClickListener{
//                override fun onItemClick(position: Int) {
//                    Toast.makeText(this@OnlineConnection, "Row clicked", Toast.LENGTH_SHORT).show()
//                    itemSelected = true
//                    val obj = data[position]
//                    idd = obj.id
//                    name = obj.name
//                    email = obj.email
//                    photo_URL = obj.photo_URL
//                }
//
//            })
//
//        }
//    }

//    private fun addFragment(sp: SharedPreferences?) {
//        val obj = InvitationFragment(sp)
//        supportFragmentManager.beginTransaction().replace(R.id.invitationContainer, obj, "ABC").commit()
//    }

//    private fun accepted()
//    {
//        startActivity(Intent(this, OnlinePlay::class.java))
//        introText.visibility = View.VISIBLE
//        codeText.visibility = View.VISIBLE
//        bCreate.visibility = View.VISIBLE
//        bJoin.visibility = View.VISIBLE
//        info.visibility = View.VISIBLE
//        progressBar.visibility = View.GONE
//    }
//
//    private fun isValueAvailable(snapshot: DataSnapshot, code: String): Boolean
//    {
//        val data = snapshot.children
//        data.forEach {
//            val value = it.value.toString()
//            if (value == code)
//            {
//                keyValue = it.key.toString()
//                return true
//            }
//        }
//        return false
//    }
}

//    private lateinit var introText: TextView
//    private lateinit var codeText: EditText
//    private lateinit var bJoin: Button
//    private lateinit var bCreate: Button
//    private lateinit var progressBar: ProgressBar
//    private lateinit var info: ImageView
//    private lateinit var cordLayout: CoordinatorLayout
//    private lateinit var container: FragmentContainerView
//    private lateinit var linearLayoutContainer: LinearLayout
//    lateinit var recycler_view2: RecyclerView
//    lateinit var recycler_view1: RecyclerView
//    lateinit var invitationFragment_InvitesAdapter: InvitationFragment_Invites_RecyclerViewAdapter
//    lateinit var invitationfragment_LobbyRecyclerviewAdapter: InvitationFragment_Lobby_RecyclerView_Adapter

//    private var idd: String? = null
//    private var name: String? = null
//private var email: String? = null
//private var photo_URL: String? = null
//lateinit var playBtn: Button


//        introText = findViewById(R.id.textView)
//        codeText = findViewById(R.id.et)
//        bJoin = findViewById(R.id.b_join)
//        bCreate = findViewById(R.id.b_create)
//        progressBar = findViewById(R.id.progressBar)
//        info = findViewById(R.id.imageView)
//        cordLayout = findViewById(R.id.coordinatorLayout)
//        container = findViewById(R.id.invitationContainer)
//        linearLayoutContainer = findViewById(R.id.linearLayoutContainer)
//        linearLayoutContainer.visibility = View.VISIBLE

//        playBtn = findViewById(R.id.playButton)
//        recycler_view2 = findViewById(R.id.rv2_invites)
//        recycler_view1 = findViewById(R.id.rv1_lobby)


//        info.setOnTouchListener { v, event ->
//            when (event?.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    MaterialAlertDialogBuilder(this)
//                        .setTitle("Info")
//                        .setMessage("One Player needs to 'CREATE' a unique code to create a game session\n(For that enter a unique code in the editor and press CREATE).\nThe other Player needs to enter the same code in the editor and press JOIN button to join the game sesion")
//                        .setPositiveButton("Dismiss") { dialog, which ->
//                            // Respond to neutral button press
//                        }.show()
//                }
//            }
//
//            v?.onTouchEvent(event) ?: true
//        }

//        bCreate.setOnClickListener {
//            code = null
//            codeFound = false
//            checkTemp = true
//            keyValue = null
//            code = codeText.text.toString()
//            introText.visibility = View.GONE
//            codeText.visibility = View.GONE
//            bCreate.visibility = View.GONE
//            bJoin.visibility = View.GONE
//            info.visibility = View.GONE
//            progressBar.visibility = View.VISIBLE
//
//
//
//            if (code != null && code != "")
//            {
////                val obj = InvitationFragment(sharedPreferences)
////                val obj = Temp()
////                val fragmentManager = supportFragmentManager
////                val fragmentTransaction = fragmentManager.beginTransaction()
////                fragmentTransaction.replace(R.id.invitationContainer, obj)
////                fragmentTransaction.commit()
//
//                isCodemaker = true
//                FirebaseDatabase.getInstance().reference.child("codes").addValueEventListener(object: ValueEventListener{
//                    override fun onDataChange(snapshot: DataSnapshot) {
//
//                        val check = isValueAvailable(snapshot, code!!)
//                        Handler().postDelayed({
//                                if (check)
//                                {
//                                    introText.visibility = View.VISIBLE
//                                    codeText.visibility = View.VISIBLE
//                                    bCreate.visibility = View.VISIBLE
//                                    bJoin.visibility = View.VISIBLE
//                                    info.visibility = View.VISIBLE
//                                    progressBar.visibility = View.GONE
//
//                                }
//                                else
//                                {
//                                    FirebaseDatabase.getInstance().reference.child("codes").push().setValue(code)
//                                    val name: String?
//                                    val email: String?
//                                    var sharedPreferences: SharedPreferences? = null
//                                    if (account != null)
//                                    {
//                                        sharedPreferences = getSharedPreferences(account!!.id, Context.MODE_PRIVATE)
//                                        name = account!!.displayName
//                                        email = account!!.email
//                                        FirebaseDatabase.getInstance().reference.child("Details")
//                                            .child(code!!).child("Creator").child("name").setValue(name)
//                                        FirebaseDatabase.getInstance().reference.child("Details")
//                                            .child(code!!).child("Creator").child("email")
//                                            .setValue(email)
//                                        FirebaseDatabase.getInstance().reference.child("Details")
//                                            .child(code!!).child("Creator").child("id")
//                                            .setValue(account!!.id)
//                                        FirebaseDatabase.getInstance().reference.child("Details")
//                                            .child(code!!).child("Creator").child("photo")
//                                            .setValue("account!!.photoUrl")
//                                    }
////                                    FirebaseDatabase.getInstance().reference.child("codes"). child("Details").child("Creator").push().setValue()
////                                    isValueAvailable(snapshot, code!!)
//                                    checkTemp = false
//                                    if(account!= null) {
//
////                                        linearLayoutContainer.visibility = View.VISIBLE
////                                        initRecyclerView1()
////                                        addDataset1()
////                                        initRecyclerView2()
////                                        addDataset2(sharedPreferences)
////                                        playBtn.setOnClickListener {
////                                            if(itemSelected){
////                                                FirebaseDatabase.getInstance().reference.child("Play with").child(code!!).child("Player ID").setValue(idd)
////                                            }
////                                        }
////                                        addFragment(sharedPreferences)
//                                        val obj = InvitationFragment(sharedPreferences)
////                                        val obj = NotificationFragment()
////                                        supportFragmentManager.beginTransaction()
////                                            .add(R.id.invitationContainer, obj).commit()
//                                        val fragmentManager = supportFragmentManager
//                                        val fragmentTransaction = fragmentManager.beginTransaction()
//                                        fragmentTransaction.replace(
//                                            R.id.invitationContainer,
//                                            obj
//                                        )
//                                        fragmentTransaction.addToBackStack(null)
//                                        fragmentTransaction.commit()
////                                        val obj = Temp()
////                                        val fragmentManager = supportFragmentManager
////                                        val fragmentTransaction = fragmentManager.beginTransaction()
////                                        fragmentTransaction.replace(R.id.invitationContainer, obj)
////                                        fragmentTransaction.commit()
////                                        linearLayoutContainer.visibility = View.VISIBLE
//
//                                        Toast.makeText(
//                                            this@OnlineConnection,
//                                            "Fragment Creat Statement Done",
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//                                    }
//                                    while(!itemSelected){
//                                        if(itemSelected) {
//                                            Handler().postDelayed({
//                                                accepted()
//                                                Toast.makeText(
//                                                    this@OnlineConnection,
//                                                    "Please don't go back",
//                                                    Toast.LENGTH_SHORT
//                                                ).show()
//                                            }, 300)
//                                        }
//                                    }
//
//                                }
//
//                        }, 2000)
//                    }
//
//                    override fun onCancelled(p0: DatabaseError) {
//                        TODO("Not yet implemented")
//                    }
//                })
//            }
//            else
//            {
//                introText.visibility = View.VISIBLE
//                codeText.visibility = View.VISIBLE
//                bCreate.visibility = View.VISIBLE
//                bJoin.visibility = View.VISIBLE
//                info.visibility = View.VISIBLE
//                progressBar.visibility = View.GONE
//                Toast.makeText(this, "Please Enter a valid Code", Toast.LENGTH_SHORT).show()
//            }
//
//
//        }

//        bJoin.setOnClickListener {
//            code = null
//            codeFound = false
//            checkTemp = true
//            keyValue = null
//            code = codeText.text.toString()
//            if (code != null && code != "")
//            {
//                introText.visibility = View.GONE
//                bJoin.visibility = View.GONE
//                bCreate.visibility = View.GONE
//                codeText.visibility = View.GONE
//                info.visibility = View.GONE
//                progressBar.visibility = View.VISIBLE
//                isCodemaker = false
//                FirebaseDatabase.getInstance().reference.child("codes").addValueEventListener(object : ValueEventListener{
//                    override fun onDataChange(p0: DataSnapshot) {
//                        val data: Boolean = isValueAvailable(p0, code!!)
//                        val name: String?
//                        val email: String?
//                        val id_: String?
//                        if (data) {
//
//                            if (account != null) {
//                                id_ = account!!.id
//                                name = account!!.displayName
//                                email = account!!.email
//                                FirebaseDatabase.getInstance().reference.child("Details")
//                                    .child(code!!).child("Joining").child("name")
//                                    .setValue(name)
//                                FirebaseDatabase.getInstance().reference.child("Details")
//                                    .child(code!!).child("Joining").child("email")
//                                    .setValue(email)
//                                FirebaseDatabase.getInstance().reference.child("Details")
//                                    .child(code!!).child("Joining").child("id")
//                                    .setValue(account!!.id)
//                                FirebaseDatabase.getInstance().reference.child("Details")
//                                    .child(code!!).child("Joining").child("photoURL")
//                                    .setValue(account!!.photoUrl)
//                            }
//                            else{
//                                id_ = null
//                            }
//                        }
//                        Handler().postDelayed({
//                            if (data)
//                            {
//                                codeFound = true
//                                FirebaseDatabase.getInstance().reference.child("Joiners").child(code!!).child(
//                                    account!!.id).child("id").setValue(account!!.id)
//                                FirebaseDatabase.getInstance().reference.child("Joiners").child(code!!).child(
//                                    account!!.id).child("name").setValue(account!!.displayName)
//                                FirebaseDatabase.getInstance().reference.child("Joiners").child(code!!).child(
//                                    account!!.id).child("email").setValue(account!!.email)
//                                FirebaseDatabase.getInstance().reference.child("Joiners").child(code!!).child(
//                                    account!!.id).child("photo_URL").setValue(account!!.photoUrl)
//                                FirebaseDatabase.getInstance().reference.child("Play with").child(code!!).addChildEventListener(object: ChildEventListener{
//                                    override fun onChildAdded(
//                                        snapshot: DataSnapshot,
//                                        previousChildName: String?
//                                    ) {
//
//                                        val child = snapshot.children
//                                        child.forEach{
//                                            val value = it.value
//                                            if (value == account!!.id)
//                                            {
//                                                accepted()
//                                                introText.visibility = View.VISIBLE
//                                                codeText.visibility = View.VISIBLE
//                                                bCreate.visibility = View.VISIBLE
//                                                bJoin.visibility = View.VISIBLE
//                                                info.visibility = View.VISIBLE
//                                                progressBar.visibility = View.GONE
//                                            }
//                                        }
//
//                                    }
//
//                                    override fun onChildChanged(
//                                        snapshot: DataSnapshot,
//                                        previousChildName: String?
//                                    ) {
//                                        TODO("Not yet implemented")
//                                    }
//
//                                    override fun onChildRemoved(snapshot: DataSnapshot) {
//                                        TODO("Not yet implemented")
//                                    }
//
//                                    override fun onChildMoved(
//                                        snapshot: DataSnapshot,
//                                        previousChildName: String?
//                                    ) {
//                                        TODO("Not yet implemented")
//                                    }
//
//                                    override fun onCancelled(error: DatabaseError) {
//                                        TODO("Not yet implemented")
//                                    }
//
//                                })
//                            }
//                            else
//                            {
//                                introText.visibility = View.VISIBLE
//                                codeText.visibility = View.VISIBLE
//                                bCreate.visibility = View.VISIBLE
//                                bJoin.visibility = View.VISIBLE
//                                info.visibility = View.VISIBLE
//                                progressBar.visibility = View.GONE
//                                Toast.makeText(this@OnlineConnection, "Invalid Code", Toast.LENGTH_SHORT).show()
//                            }
//                        }, 2000)
//
//                    }
//
//                    override fun onCancelled(p0: DatabaseError) {
//                        TODO("Not yet implemented")
//                    }
//                })
//            }
//            else
//            {
//                Toast.makeText(this, "Please enter a valid code", Toast.LENGTH_SHORT).show()
//            }
//        }