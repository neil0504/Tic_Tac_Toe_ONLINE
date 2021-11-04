package com.example.tic_tac_toe_online

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.*
import android.content.Intent as Intent1

//lateinit var createrID: String
//lateinit var joinerId: String
var isCodemaker = false
var code: String? = null
var codeFound = false
var checkTemp = true
var keyValue: String? = null

lateinit var creatorID: String
lateinit var creatorName: String
lateinit var creatorEmail: String
lateinit var creatorPhotoURL: String
class OnlineConnectionFragment : Fragment(){
//    private lateinit var introText: TextView
    private lateinit var codeText: EditText
    private lateinit var bJoin: Button
    private lateinit var bCreate: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var info: ImageView
    private lateinit var cordLayout: CoordinatorLayout
    private lateinit var thiscontxt: Context
    lateinit var mListener: TurnOnFragment
    public interface TurnOnFragment{
        fun turnOn(sharedPreferences: SharedPreferences?)
    }
    fun setTurnOnFragment(listerer: TurnOnFragment) {
        mListener = listerer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_online_connection, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        thiscontxt = view.context
//        FirebaseApp.initializeApp(thiscontxt)
//        introText = findViewById(R.id.textView)
        codeText = view.findViewById(R.id.et)
        bJoin = view.findViewById(R.id.b_join)
        bCreate = view.findViewById(R.id.b_create)
        progressBar = view.findViewById(R.id.progressBar)
        info = view.findViewById(R.id.imageView)
        cordLayout = view.findViewById(R.id.coordinatorLayout)

        info.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    MaterialAlertDialogBuilder(thiscontxt)
                        .setTitle("Info")
                        .setMessage("One Player needs to 'CREATE' a unique code to create a game session\n(For that enter a unique code in the editor and press CREATE).\nThe other Player needs to enter the same code in the editor and press JOIN button to join the game sesion")
                        .setPositiveButton("Dismiss") { dialog, which ->
                            // Respond to neutral button press
                        }.show()
                }
            }

            v?.onTouchEvent(event) ?: true
        }
        bCreate.setOnClickListener {
            code = null
            codeFound = false
            checkTemp = true
            keyValue = null
            code = codeText.text.toString()
//            introText.visibility = View.GONE
            codeText.visibility = View.GONE
            bCreate.visibility = View.GONE
            bJoin.visibility = View.GONE
            info.visibility = View.GONE
            progressBar.visibility = View.VISIBLE

            if (code != null && code != "")
            {
                isCodemaker = true
                FirebaseDatabase.getInstance().reference.child("codes").addValueEventListener(object:
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val check = isValueAvailable(snapshot, code!!)
                        Handler().postDelayed({
                            if (check) {
                                Toast.makeText(thiscontxt, "Code already in use. Enter a new Unique Code", Toast.LENGTH_SHORT).show()
//                                introText.visibility = View.VISIBLE
                                codeText.visibility = View.VISIBLE
                                bCreate.visibility = View.VISIBLE
                                bJoin.visibility = View.VISIBLE
                                info.visibility = View.VISIBLE
                                progressBar.visibility = View.GONE

                            }
                            else{
                                FirebaseDatabase.getInstance().reference.child("codes").push().setValue(code)
                                FirebaseDatabase.getInstance().reference.child("Creater_Joiner").child(
                                    code!!).child("CreaterID").setValue(account!!.id)
                                var sharedPreferences: SharedPreferences? = null
                                if (account != null)
                                {
                                    sharedPreferences = thiscontxt.getSharedPreferences(account!!.id, Context.MODE_PRIVATE)
                                    val id_stringset = sharedPreferences.getStringSet("ID", null)
                                    Log.d("DATASOURCE_INVITES", "EXISTING VALUE OF id_stringset = $id_stringset")
                                    val name_stringset = sharedPreferences.getStringSet("NAME", null)
                                    Log.d("DATASOURCE_INVITES", "EXISTING VALUE OF name_stringset = $name_stringset")
                                    val email_stringset = sharedPreferences.getStringSet("EMAIL", null)
                                    Log.d("DATASOURCE_INVITES", "EXISTING VALUE OF email_stringset = $email_stringset")
                                    val photoURL_stringset = sharedPreferences.getStringSet("PHOTO_URL", null)
                                    Log.d("DATASOURCE_INVITES", "EXISTING VALUE OF photoURL_stringset = $photoURL_stringset")
//                                    sharedPreferences = getSharedPreferences(account!!.id, Context.MODE_PRIVATE)
//                                    name = account!!.displayName
//                                    email = account!!.email
                                    FirebaseDatabase.getInstance().reference.child("Details")
                                        .child(code!!).child("Creator").child("name").setValue(account!!.displayName)
                                    FirebaseDatabase.getInstance().reference.child("Details")
                                        .child(code!!).child("Creator").child("email")
                                        .setValue(account!!.email)
                                    FirebaseDatabase.getInstance().reference.child("Details")
                                        .child(code!!).child("Creator").child("id")
                                        .setValue(account!!.id)
                                    FirebaseDatabase.getInstance().reference.child("Details")
                                        .child(code!!).child("Creator").child("photo")
                                        .setValue(account!!.photoUrl!!.toString())
                                }
                                checkTemp = false
                                if(account!= null) {
                                    mListener.turnOn(sharedPreferences)
                                }

                            }
                        }, 2000)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }
            else{
//                introText.visibility = View.VISIBLE
                codeText.visibility = View.VISIBLE
                bCreate.visibility = View.VISIBLE
                bJoin.visibility = View.VISIBLE
                info.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                Toast.makeText(thiscontxt, "Please Enter a valid Code", Toast.LENGTH_SHORT).show()
//                Toast.makeText(thiscontxt, "Plz Enter a code to Create a Game", Toast.LENGTH_SHORT).show()
            }


        }

        bJoin.setOnClickListener {
            code = null
            codeFound = false
            checkTemp = true
            keyValue = null
            code = codeText.text.toString()
            if (code != null && code != "") {
//                introText.visibility = View.GONE
                bJoin.visibility = View.GONE
                bCreate.visibility = View.GONE
                codeText.visibility = View.GONE
                info.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                isCodemaker = false
                FirebaseDatabase.getInstance().reference.child("codes").addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(p0: DataSnapshot) {
                        val data: Boolean = isValueAvailable(p0, code!!)
                        val name: String?
                        val email: String?
                        if (data) {
                            if (account != null) {
                                FirebaseDatabase.getInstance().reference.child("Details")
                                    .child(code!!).child("Joining").child("name")
                                    .setValue(account!!.displayName)
                                FirebaseDatabase.getInstance().reference.child("Details")
                                    .child(code!!).child("Joining").child("email")
                                    .setValue(account!!.email)
                                FirebaseDatabase.getInstance().reference.child("Details")
                                    .child(code!!).child("Joining").child("id")
                                    .setValue(account!!.id)
                                FirebaseDatabase.getInstance().reference.child("Details")
                                    .child(code!!).child("Joining").child("photoURL")
                                    .setValue(account!!.photoUrl!!.toString())
                            }
                            Handler().postDelayed({
                                if (data) {
                                    codeFound = true
                                    val arrayList = ArrayList<String> ()
                                    arrayList.add(account!!.id)
                                    arrayList.add(account!!.displayName)
                                    arrayList.add(account!!.email)
                                    arrayList.add(account!!.photoUrl.toString())
                                    FirebaseDatabase.getInstance().reference.child("Joiners").child(
                                        code!!).child(account!!.id).setValue(arrayList)
//                                    FirebaseDatabase.getInstance().reference.child("Joiners")
//                                        .child(code!!).child(
//                                            account!!.id
//                                        ).child("id").setValue(account!!.id)
//                                    FirebaseDatabase.getInstance().reference.child("Joiners")
//                                        .child(code!!).child(
//                                            account!!.id
//                                        ).child("name").setValue(account!!.displayName)
//                                    FirebaseDatabase.getInstance().reference.child("Joiners")
//                                        .child(code!!).child(
//                                            account!!.id
//                                        ).child("email").setValue(account!!.email)
//                                    FirebaseDatabase.getInstance().reference.child("Joiners")
//                                        .child(code!!).child(
//                                            account!!.id
//                                        ).child("photo_URL").setValue(account!!.photoUrl!!.toString())
                                    FirebaseDatabase.getInstance().reference.child("Creator_cred").child(code!!).addChildEventListener(object : ChildEventListener{
                                        override fun onChildAdded(
                                            snapshot: DataSnapshot,
                                            previousChildName: String?
                                        ) {
                                            Log.d("abcdefghijk", "addChildEventListener called for CreatorCred")
                                            val dataa = snapshot.children
                                            val l = ArrayList<String>()
                                            dataa.forEach{
                                                val value = it.value.toString()
                                                Log.d("abcdefghijk", "Inside For Loop for Creator val = $value")
                                                l.add(value)
                                            }
                                            creatorID = l[0]
                                            creatorName = l[1]
                                            creatorEmail = l[2]
                                            creatorPhotoURL = l[3]
                                        }

                                        override fun onChildChanged(
                                            snapshot: DataSnapshot,
                                            previousChildName: String?
                                        ) {
                                            TODO("Not yet implemented")
                                        }

                                        override fun onChildRemoved(snapshot: DataSnapshot) {
                                            TODO("Not yet implemented")
                                        }

                                        override fun onChildMoved(
                                            snapshot: DataSnapshot,
                                            previousChildName: String?
                                        ) {
                                            TODO("Not yet implemented")
                                        }

                                        override fun onCancelled(error: DatabaseError) {
                                            TODO("Not yet implemented")
                                        }

                                    })
                                    FirebaseDatabase.getInstance().reference.child("Play with")
                                        .child(code!!).addChildEventListener(object :
                                        ChildEventListener {
                                        override fun onChildAdded(
                                            snapshot: DataSnapshot,
                                            previousChildName: String?
                                        ) {
//                                                Log.d("****", "OnChildAddded Listener method clicked for Joining")
//                                                Log.d("****", "Previous child = $previousChildName")
                                            Log.d("abcdefgh", "OnChildAddded Listener method clicked for Joining")

                                            val child = snapshot.value.toString()
//                                                val children = snapshot.children
//                                                Log.d("abcdefgh", "Count = ${children.count()}")
//                                                val l = ArrayList<String>()
//                                                children.forEach{
//                                                    val value = it.value.toString()
//                                                    Log.d("abcdefgh", "Each Val = $value")
//                                                    l.add(value)
//                                                }
//                                                Log.d("****", "clicked for Joining child value = ${child.count()}")
//                                                child.forEach {
//                                                    Toast.makeText(thiscontxt, "Inside for Loop", Toast.LENGTH_SHORT).show()
//                                                    val value = it.value.toString()
                                            Log.d("****", "Reached Comparision point")
//                                                Log.d("abcdefgh", "Value of l[0] = ${l[0]}")
                                            if (child == account!!.id!!.toString()) {
                                                Log.d("****", "Code accepted and user Joining")
                                                FirebaseDatabase.getInstance().reference.child("Creater_Joiner").child(
                                                    code!!).child("JoinerID").setValue(account!!.id)
                                                accepted()
//                                                    introText.visibility = View.VISIBLE
                                                codeText.visibility = View.VISIBLE
                                                bCreate.visibility = View.VISIBLE
                                                bJoin.visibility = View.VISIBLE
                                                info.visibility = View.VISIBLE
                                                progressBar.visibility = View.GONE
                                            }
//
                                        }

                                            override fun onChildChanged(
                                                snapshot: DataSnapshot,
                                                previousChildName: String?
                                            ) {
                                                TODO("Not yet implemented")
                                            }

                                            override fun onChildRemoved(snapshot: DataSnapshot) {
                                                TODO("Not yet implemented")
                                            }

                                            override fun onChildMoved(
                                                snapshot: DataSnapshot,
                                                previousChildName: String?
                                            ) {
                                                TODO("Not yet implemented")
                                            }

                                            override fun onCancelled(error: DatabaseError) {
                                                TODO("Not yet implemented")
                                            }
                                        })

                                }
                                else
                                {
//                                    introText.visibility = View.VISIBLE
                                    codeText.visibility = View.VISIBLE
                                    bCreate.visibility = View.VISIBLE
                                    bJoin.visibility = View.VISIBLE
                                    info.visibility = View.VISIBLE
                                    progressBar.visibility = View.GONE
                                    Toast.makeText(thiscontxt, "Invalid Code", Toast.LENGTH_SHORT).show()
                                }
                            }, 2000)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })

            }
        }





    }

    fun accepted() {
//        thiscontxt = view?.context!!
        startActivity(Intent1(cc, OnlinePlay::class.java))
//        introText.visibility = View.VISIBLE
        codeText.visibility = View.VISIBLE
        bCreate.visibility = View.VISIBLE
        bJoin.visibility = View.VISIBLE
        info.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    private fun isValueAvailable(snapshot: DataSnapshot, code: String): Boolean {
        val data = snapshot.children
        data.forEach {
            val value = it.value.toString()
            if (value == code)
            {
                keyValue = it.key.toString()
                return true
            }
        }
        return false
    }

}