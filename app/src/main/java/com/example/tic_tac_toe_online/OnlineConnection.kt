package com.example.tic_tac_toe_online

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


var isCodemaker = false
var code: String? = null
var codeFound = false
var checkTemp = true
var keyValue: String? = null
class OnlineConnection : AppCompatActivity() {
    private lateinit var introText: TextView
    private lateinit var codeText: EditText
    private lateinit var bJoin: Button
    private lateinit var bCreate: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var info: ImageView
    private lateinit var cordLayout: CoordinatorLayout
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online_connection)
        val obj = InvitationFragment()
        introText = findViewById(R.id.textView)
        codeText = findViewById(R.id.et)
        bJoin = findViewById(R.id.b_join)
        bCreate = findViewById(R.id.b_create)
        progressBar = findViewById(R.id.progressBar)
        info = findViewById(R.id.imageView)
        cordLayout = findViewById(R.id.coordinatorLayout)


//        val sbar: Snackbar = Snackbar.make(cordLayout, "One Player needs to 'CREATE' a unique code to create a game session\n(For that enter a unique code in the editor and press CREATE)\nThe other Player needs to enter the same code in the editor and press JOIN button to join the game sesion", Snackbar.LENGTH_LONG)
//        sbar.duration = 5000
//        sbar.setAction("DISMISS", View.OnClickListener {  })
//
//        val layout: Snackbar.SnackbarLayout = SnackbarLayout(this, )
//
//        sbar.getView().minimumHeight = 200


        info.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    MaterialAlertDialogBuilder(this)
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
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.invitationFragmentContainer, obj)
            fragmentTransaction.commit()
            code = null
            codeFound = false
            checkTemp = true
            keyValue = null
            code = codeText.text.toString()
            introText.visibility = View.GONE
            codeText.visibility = View.GONE
            bCreate.visibility = View.GONE
            bJoin.visibility = View.GONE
            info.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            if (code != null && code != "")
            {
                isCodemaker = true
                FirebaseDatabase.getInstance().reference.child("codes").addValueEventListener(object: ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {

                        val check = isValueAvailable(snapshot, code!!)
                        Handler().postDelayed({
                                if (check)
                                {
                                    introText.visibility = View.VISIBLE
                                    codeText.visibility = View.VISIBLE
                                    bCreate.visibility = View.VISIBLE
                                    bJoin.visibility = View.VISIBLE
                                    info.visibility = View.VISIBLE
                                    progressBar.visibility = View.GONE
                                }
                                else
                                {
                                    FirebaseDatabase.getInstance().reference.child("codes").push().setValue(code)
//                                    isValueAvailable(snapshot, code!!)
                                    checkTemp = false
                                    Handler().postDelayed({
                                        accepted()
                                        Toast.makeText(this@OnlineConnection, "Please don't go back", Toast.LENGTH_SHORT).show()
                                    }, 300)

                                }

                        }, 2000)
                    }

                    override fun onCancelled(p0: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }
            else
            {
                introText.visibility = View.VISIBLE
                codeText.visibility = View.VISIBLE
                bCreate.visibility = View.VISIBLE
                bJoin.visibility = View.VISIBLE
                info.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Please Enter a valid Code", Toast.LENGTH_SHORT).show()
            }


        }

        bJoin.setOnClickListener {
            code = null
            codeFound = false
            checkTemp = true
            keyValue = null
            code = codeText.text.toString()
            if (code != null && code != "")
            {
                introText.visibility = View.GONE
                bJoin.visibility = View.GONE
                bCreate.visibility = View.GONE
                codeText.visibility = View.GONE
                info.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                isCodemaker = false
                FirebaseDatabase.getInstance().reference.child("codes").addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(p0: DataSnapshot) {
                        val data: Boolean = isValueAvailable(p0, code!!)
                        Handler().postDelayed({
                            if (data)
                            {
                                codeFound = true
                                accepted()
                                introText.visibility = View.VISIBLE
                                codeText.visibility = View.VISIBLE
                                bCreate.visibility = View.VISIBLE
                                bJoin.visibility = View.VISIBLE
                                info.visibility = View.VISIBLE
                                progressBar.visibility = View.GONE

                            }
                            else
                            {
                                introText.visibility = View.VISIBLE
                                codeText.visibility = View.VISIBLE
                                bCreate.visibility = View.VISIBLE
                                bJoin.visibility = View.VISIBLE
                                info.visibility = View.VISIBLE
                                progressBar.visibility = View.GONE
                                Toast.makeText(this@OnlineConnection, "Invalid Code", Toast.LENGTH_SHORT).show()
                            }
                        }, 2000)

                    }

                    override fun onCancelled(p0: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }
            else
            {
                Toast.makeText(this, "Please enter a valid code", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun accepted()
    {
        startActivity(Intent(this, OnlinePlay::class.java))
        introText.visibility = View.VISIBLE
        codeText.visibility = View.VISIBLE
        bCreate.visibility = View.VISIBLE
        bJoin.visibility = View.VISIBLE
        info.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    private fun isValueAvailable(snapshot: DataSnapshot, code: String): Boolean
    {
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