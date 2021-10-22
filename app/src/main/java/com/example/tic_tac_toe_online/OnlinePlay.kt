package com.example.tic_tac_toe_online

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import kotlin.system.exitProcess

var isMymove = isCodemaker
var isMymove_1 = isCodemaker
class OnlinePlay : AppCompatActivity() {

    val TAG: String = "OnlinePlay"

    private var winnerX = false
    private var winnerO = false
    private var winnerTie = false


    private lateinit var button1: ImageButton
    private lateinit var button2: ImageButton
    private lateinit var button3: ImageButton
    private lateinit var button4: ImageButton
    private lateinit var button5: ImageButton
    private lateinit var button6: ImageButton
    private lateinit var button7: ImageButton
    private lateinit var button8: ImageButton
    private lateinit var button9: ImageButton

    private lateinit var resetBoard: Button
    private lateinit var turnButton: Button

    private lateinit var ob1: ImageButton
    private lateinit var ob2: ImageButton
    private lateinit var om1: ImageButton
    private lateinit var om2: ImageButton
    private lateinit var os1: ImageButton
    private lateinit var os2: ImageButton


    private lateinit var xb1: ImageButton
    private lateinit var xb2: ImageButton
    private lateinit var xm1: ImageButton
    private lateinit var xm2: ImageButton
    private lateinit var xs1: ImageButton
    private lateinit var xs2: ImageButton

    private lateinit var layout: ConstraintLayout

    private lateinit var name: TextView
    private lateinit var codeText: TextView
    private lateinit var turn: TextView
    private lateinit var instruction: TextView
    private lateinit var scoreText: TextView

    private var count = 1
    private var id1: Int? = null

    private var i1 = 4
    private var i2 = 4
    private var i3 = 4
    private var i4 = 4
    private var i5 = 4
    private var i6 = 4
    private var i7 = 4
    private var i8 = 4
    private var i9 = 4

    private var idArray = arrayOfNulls<Number>(9)
    private var winner = false
    private var counter = arrayOf(0, 0, 0, 0, 0, 0)

    private var p1 = 1
    private var p2 = 2

    private lateinit var drawableOb: Drawable
    private lateinit var drawableOm: Drawable
    private lateinit var drawableOs: Drawable
    private lateinit var drawableXb: Drawable
    private lateinit var drawableXm: Drawable
    private lateinit var drawableXs: Drawable

    private lateinit var defaultDrawable: Drawable

    private var reSet = true
    private var b1Filled = false
    private var b2Filled = false
    private var b3Filled = false
    private var b4Filled = false
    private var b5Filled = false
    private var b6Filled = false
    private var b7Filled = false
    private var b8Filled = false
    private var b9Filled = false

    private var boardReset = false

    private var sp1: Int = 0
    private var sp2: Int = 0
    private var sp = arrayOf(sp1, sp2)

    private var symbolSelected: Int? = null

    private var tButton = false

    private lateinit var dbSnapshot: DataSnapshot

    private var notClosed = false
    private lateinit var winMusic: MediaPlayer
    private lateinit var looseMusic: MediaPlayer

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    var id_stringset: MutableSet<String>? = null
    var name_stringset: MutableSet<String>? = null
    var email_stringset: MutableSet<String>? = null
    var name_ = ""
    var email = ""
    var id = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online_play)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        layout = findViewById(R.id.constaintLayout2)

        ob1 = findViewById(R.id.ob1)
        ob2 = findViewById(R.id.ob2)
        om1 = findViewById(R.id.om1)
        om2 = findViewById(R.id.om2)
        os1 = findViewById(R.id.os1)
        os2 = findViewById(R.id.os2)


        xb1 = findViewById(R.id.xb1)
        xb2 = findViewById(R.id.xb2)
        xm1 = findViewById(R.id.xm1)
        xm2 = findViewById(R.id.xm2)
        xs1 = findViewById(R.id.xs1)
        xs2 = findViewById(R.id.xs2)


        resetBoard = findViewById(R.id.reset)
        turnButton = findViewById(R.id.turnButton)

        drawableOb = ob1.drawable
        drawableOm = om1.drawable
        drawableOs = os1.drawable
        drawableXb = xb1.drawable
        drawableXm = xm1.drawable
        drawableXs = xs1.drawable

        val uri = "@drawable/g_rey" // where myresource (without the extension) is the file
        val imageResource = resources.getIdentifier(uri, null, packageName)
        defaultDrawable = resources.getDrawable(imageResource)


        name = findViewById(R.id.textView2)
        codeText = findViewById(R.id.codeText)
        turn = findViewById(R.id.turnTextView)
        instruction = findViewById(R.id.textView4)
        scoreText = findViewById(R.id.scoreText)



        button1 = findViewById(R.id.p11)
        button2 = findViewById(R.id.p12)
        button3 = findViewById(R.id.p13)
        button4 = findViewById(R.id.p21)
        button5 = findViewById(R.id.p22)
        button6 = findViewById(R.id.p23)
        button7 = findViewById(R.id.p31)
        button8 = findViewById(R.id.p32)
        button9 = findViewById(R.id.p33)

        val linearLayout: LinearLayout = findViewById(R.id.linearLayout)
        linearLayout.alpha = 0f

        button1.alpha = 1f
        button2.alpha = 1f
        button3.alpha = 1f
        button4.alpha = 1f
        button5.alpha = 1f
        button6.alpha = 1f
        button7.alpha = 1f
        button8.alpha = 1f
        button9.alpha = 1f
        if(isMymove)
        {
            if (account!= null) {
                var sharedPreferences = getSharedPreferences(account!!.id, Context.MODE_PRIVATE)
                id_stringset = sharedPreferences.getStringSet("ID", null)
                name_stringset = sharedPreferences.getStringSet("NAME", null)
                email_stringset = sharedPreferences.getStringSet("EMAIL", null)
                Log.d("*****", "ID set = $id_stringset")
                Log.d("*****", "namee set = $name_stringset")
                Log.d("*****", "email set = $email_stringset")



                FirebaseDatabase.getInstance().reference.child("Details")
                    .child(code!!).child("Joining").child("name").addValueEventListener(object :ValueEventListener
                    {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            name_ = snapshot.value.toString()
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    })

                FirebaseDatabase.getInstance().reference.child("Details")
                    .child(code!!).child("Joining").child("email").addValueEventListener(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            email = snapshot.value.toString()
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    })

                FirebaseDatabase.getInstance().reference.child("Details")
                    .child(code!!).child("Joining").child("id").addValueEventListener(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            id = snapshot.value.toString()
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    })
                id_stringset!!.add(id)
                name_stringset!!.add(name_)
                email_stringset!!.add(email)
                Log.d("*****", "Updated ID set = $id_stringset")
                Log.d("*****", "Updated name set = $name_stringset")
                Log.d("*****", "Updated email set = $email_stringset")

                sharedPreferences = getSharedPreferences(account!!.id, Context.MODE_PRIVATE)
                editor = sharedPreferences.edit()
                editor.apply{
                    putStringSet("ID", id_stringset)
                    putStringSet("NAME", name_stringset)
                    putStringSet("EMAIL", email_stringset)

                }
            }

            Toast.makeText(this, "You are the Creator. U r playing with $name with email: $email", Toast.LENGTH_LONG).show()
            turn.text = "Your Turn"
            instruction.text = "Your Shape : X"
            codeText.text = "CODE : $code!!"

        }
        else
        {
            val email = FirebaseDatabase.getInstance().reference.child("Details")
                .child(code!!).child("Creator").child("email").get()
            val name = FirebaseDatabase.getInstance().reference.child("Details")
                .child(code!!).child("Creator").child("name").get()
            Toast.makeText(this, "You are the Joiner. U r playing with $name with email: $email", Toast.LENGTH_LONG).show()
            turn.text = "Opponent's Turn"
            instruction.text = "Your Shape : O"
            codeText.visibility = View.GONE

        }

        name.alpha = 0f
        codeText.alpha = 0f
        instruction.alpha = 0f
        turn.alpha = 0f
        scoreText.alpha = 0f

        linearLayout.translationY = 50f

        name.translationY = 0f
        codeText.translationY = 0f
        instruction.translationY = 0f
        turn.translationY = 0f
        scoreText.translationY = 0f

        name.animate().alpha(1f).translationYBy(10f).duration = 1500
        codeText.animate().alpha(1f).translationYBy(10f).duration = 1500
        instruction.animate().alpha(1f).translationYBy(10f).duration = 1500
        turn.animate().alpha(1f).translationYBy(10f).duration = 1500
        scoreText.animate().alpha(1f).translationYBy(10f).duration = 1500

        linearLayout.animate().alpha(1f).translationYBy(-50f).duration = 1500

        val a = "@anim/milkshake"
        val c = resources.getIdentifier(a, null, packageName)
        val myAnim: Animation = AnimationUtils.loadAnimation(this, c)
        val scaleUp: Animation = AnimationUtils.loadAnimation(
            this,
            resources.getIdentifier("@anim/scale_up", null, packageName)
        )
        val scaleDown: Animation = AnimationUtils.loadAnimation(
            this,
            resources.getIdentifier("@anim/scale_down", null, packageName)
        )

        winMusic = MediaPlayer.create(this, R.raw.win)
        looseMusic = MediaPlayer.create(this, R.raw.loose)

        FirebaseDatabase.getInstance().reference.child("data").child(code!!)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildname: String?) {
                    Log.d(TAG, "Child added and value of isMyMove_1 = $isMymove_1")
                    Log.d(TAG, "Value in snapshot = ${snapshot.children}")
                    dbSnapshot = snapshot
                    val data = snapshot.children
                    val list = arrayListOf<Int>()

                    data.forEach {
                        val value = it.value.toString().toInt()
                        Log.d(TAG, "value = $value")
                        list.add(value)
                    }

                    if (isMymove_1) {
                        isMymove_1 = false
                        moveOnline(list, isMymove_1)
                    } else {
                        turnSelector()
                        isMymove_1 = true
                        moveOnline(list, isMymove_1)
                    }
                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(p0: DataSnapshot) {
                    reStart()
                    Toast.makeText(this@OnlinePlay, "Game RESET", Toast.LENGTH_SHORT).show()
                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        FirebaseDatabase.getInstance().reference.child("isTurnInverted").child(code!!)
            .addChildEventListener(
            object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    tButton = !tButton
                    invertTurn()
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    Log.d(TAG, "Removed")
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        FirebaseDatabase.getInstance().reference.child("Board Reset").child(code!!)
            .addChildEventListener(
            object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    boardReset = true
                    reStart()
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    Log.d(TAG, "Removed")
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        val bO = View.OnClickListener { v ->
            val b = v as ImageButton
            if (isMymove_1) {
                if (!isMymove) {

                    id1 = 1
                    symbolSelected = b.id
                } else {
                    b.startAnimation(myAnim)
                }
            }
            else
            {
                b.startAnimation(myAnim)
                Toast.makeText(this, "It's Opponent's move", Toast.LENGTH_SHORT).show()
            }
        }

        val mO = View.OnClickListener { v ->
            val b = v as ImageButton
            if (isMymove_1) {
                if (!isMymove) {

                    id1 = 2
                    symbolSelected = b.id
                } else {
                    b.startAnimation(myAnim)
                }
            }
            else
            {
                b.startAnimation(myAnim)
                Toast.makeText(this, "It's Opponent's move", Toast.LENGTH_SHORT).show()
            }
        }

        val sO = View.OnClickListener { v ->
            val b = v as ImageButton
            if (isMymove_1) {
                if (!isMymove) {

                    id1 = 3
                    symbolSelected = b.id
                } else {
                    b.startAnimation(myAnim)
                }
            }
            else
            {
                b.startAnimation(myAnim)
                Toast.makeText(this, "It's Opponent's move", Toast.LENGTH_SHORT).show()
            }
        }

        val bX = View.OnClickListener { v ->
            val b = v as ImageButton
            if (isMymove_1) {
                if (isMymove) {

                    id1 = 4
                    symbolSelected = b.id
                } else {
                    b.startAnimation(myAnim)
                }
            }
            else
            {
                b.startAnimation(myAnim)
                Toast.makeText(this, "It's Opponent's move", Toast.LENGTH_SHORT).show()
            }
        }

        val mX = View.OnClickListener { v ->
            val b = v as ImageButton
            if (isMymove_1) {
            if (isMymove) {

                id1 = 5
                symbolSelected = b.id
            } else {
                b.startAnimation(myAnim)
            }
            }
            else
            {
                b.startAnimation(myAnim)
                Toast.makeText(this, "It's Opponent's move", Toast.LENGTH_SHORT).show()
            }
        }

        val sX = View.OnClickListener { v ->
            val b = v as ImageButton
            if (isMymove_1) {
                if (isMymove) {

                    id1 = 6
                    symbolSelected = b.id
                } else {
                    b.startAnimation(myAnim)
                }
            }
            else
            {
                b.startAnimation(myAnim)
                Toast.makeText(this, "It's Opponent's move", Toast.LENGTH_SHORT).show()
            }
        }

        val b1 = View.OnClickListener { v ->
            if (isMymove_1) {
                val b = v as ImageButton
                var eqId: Int? = null
                if (!winner) {
                    val img: Drawable? = imageSelector(id1)
                    if (img != null) {

                        if (id1 != 1 && id1 != 2 && id1 != 3) {
                            when (id1) {
                                4 -> {
                                    eqId = 1
                                }
                                5 -> {
                                    eqId = 2
                                }
                                6 -> {
                                    eqId = 3
                                }
                            }
                        } else {
                            eqId = id1 as Int
                        }


                        if (eqId != null) {
                            if (eqId < i1) {
                                b.setImageDrawable(img)
                                val image = imageFinder(img)
                                updateDatabase(image, b.id)
                                count++
                                i1 = eqId
                                idArray[0] = id1
                                b1Filled = true
                                hideMe()
                                checkWinner()
                                turnSelector()

                                id1 = null
                                reSet = false
                                symbolSelected = null
                            }
                        }


                    }

                }
            }
        }

        val b2 = View.OnClickListener { v ->
            if (isMymove_1) {
                val b = v as ImageButton
                var eqId: Int? = null
                if (!winner) {
                    val img: Drawable? = imageSelector(id1)
                    if (img != null) {
                        if (id1 != 1 && id1 != 2 && id1 != 3) {
                            when (id1) {
                                4 -> {
                                    eqId = 1
                                }
                                5 -> {
                                    eqId = 2
                                }
                                6 -> {
                                    eqId = 3
                                }
                            }
                        } else {
                            eqId = id1 as Int
                        }


                        if (eqId!! < i2) {
                            b.setImageDrawable(img)
                            val image = imageFinder(img)
                            updateDatabase(image, b.id)
                            count++
                            i2 = eqId!!
                            idArray[1] = id1
                            b2Filled = true
                            hideMe()
                            checkWinner()
                            turnSelector()


                            id1 = null
                            reSet = false
                            symbolSelected = null
                        }

                    }
                }
            }
        }

        val b3 = View.OnClickListener { v ->
            if (isMymove_1) {
                val b = v as ImageButton
                var eqId: Int? = null
                if (!winner) {
                    val img: Drawable? = imageSelector(id1)
                    if (img != null) {
                        if (id1 != 1 && id1 != 2 && id1 != 3) {
                            when (id1) {
                                4 -> {
                                    eqId = 1
                                }
                                5 -> {
                                    eqId = 2
                                }
                                6 -> {
                                    eqId = 3
                                }
                            }
                        } else {
                            eqId = id1 as Int
                        }


                        if (eqId != null) {
                            if (eqId!! < i3) {
                                b.setImageDrawable(img)
                                val image = imageFinder(img)
                                updateDatabase(image, b.id)
                                count++
                                i3 = eqId!!
                                idArray[2] = id1
                                b3Filled = true
                                hideMe()
                                checkWinner()
                                turnSelector()
                                id1 = null
                                reSet = false
                                symbolSelected = null

                            }
                        }
                    }
                }
            }
        }

        val b4 = View.OnClickListener { v ->
            if (isMymove_1) {
                val b = v as ImageButton
                var eqId: Int? = null
                if (!winner) {
                    val img: Drawable? = imageSelector(id1)
                    if (img != null) {
                        if (id1 != 1 && id1 != 2 && id1 != 3) {
                            when (id1) {
                                4 -> {
                                    eqId = 1
                                }
                                5 -> {
                                    eqId = 2
                                }
                                6 -> {
                                    eqId = 3
                                }
                            }
                        } else {
                            eqId = id1 as Int
                        }


                        if (eqId != null) {
                            if (eqId!! < i4) {
                                b.setImageDrawable(img)
                                val image = imageFinder(img)
                                updateDatabase(image, b.id)
                                count++
                                i4 = eqId!!
                                idArray[3] = id1
                                b4Filled = true
                                hideMe()
                                checkWinner()
                                turnSelector()
                                id1 = null
                                reSet = false
                                symbolSelected = null
                            }
                        }
                    }
                }
            }
        }

        val b5 = View.OnClickListener { v ->
            if (isMymove_1) {
                val b = v as ImageButton
                var eqId: Int? = null
                if (!winner) {
                    val img: Drawable? = imageSelector(id1)
                    if (img != null) {
                        if (id1 != 1 && id1 != 2 && id1 != 3) {
                            when (id1) {
                                4 -> {
                                    eqId = 1
                                }
                                5 -> {
                                    eqId = 2
                                }
                                6 -> {
                                    eqId = 3
                                }
                            }
                        } else {
                            eqId = id1 as Int
                        }

                        if (eqId != null) {
                            if (eqId!! < i5) {
                                b.setImageDrawable(img)
                                val image = imageFinder(img)
                                updateDatabase(image, b.id)
                                count++
                                i5 = eqId!!
                                idArray[4] = id1
                                b5Filled = true
                                hideMe()
                                checkWinner()
                                turnSelector()
                                id1 = null
                                reSet = false
                                symbolSelected = null
                            }
                        }
                    }
                }
            }
        }

        val b6 = View.OnClickListener { v ->
            if (isMymove_1) {
                val b = v as ImageButton
                var eqId: Int? = null
                if (!winner) {
                    val img: Drawable? = imageSelector(id1)
                    if (img != null) {
                        if (id1 != 1 && id1 != 2 && id1 != 3) {
                            when (id1) {
                                4 -> {
                                    eqId = 1
                                }
                                5 -> {
                                    eqId = 2
                                }
                                6 -> {
                                    eqId = 3
                                }
                            }
                        } else {
                            eqId = id1 as Int
                        }


                        if (eqId != null) {
                            if (eqId!! < i6) {
                                b.setImageDrawable(img)
                                val image = imageFinder(img)
                                updateDatabase(image, b.id)
                                count++
                                i6 = eqId!!
                                idArray[5] = id1
                                b6Filled = true
                                hideMe()
                                checkWinner()
                                turnSelector()
                                id1 = null
                                reSet = false
                                symbolSelected = null
                            }
                        }
                    }
                }
            }
        }

        val b7 = View.OnClickListener { v ->
            if (isMymove_1) {
                val b = v as ImageButton
                var eqId: Int? = null
                if (!winner) {
                    val img: Drawable? = imageSelector(id1)
                    if (img != null) {
                        if (id1 != 1 && id1 != 2 && id1 != 3) {
                            when (id1) {
                                4 -> {
                                    eqId = 1
                                }
                                5 -> {
                                    eqId = 2
                                }
                                6 -> {
                                    eqId = 3
                                }
                            }
                        } else {
                            eqId = id1 as Int
                        }


                        if (eqId != null) {
                            if (eqId!! < i7) {
                                b.setImageDrawable(img)
                                val image = imageFinder(img)
                                updateDatabase(image, b.id)
                                count++
                                i7 = eqId!!
                                idArray[6] = id1
                                b7Filled = true
                                hideMe()
                                checkWinner()
                                turnSelector()
                                id1 = null
                                reSet = false
                                symbolSelected = null
                            }
                        }
                    }
                }
            }
        }

        val b8 = View.OnClickListener { v ->
            if (isMymove_1) {
                val b = v as ImageButton
                var eqId: Int? = null
                if (!winner) {
                    val img: Drawable? = imageSelector(id1)
                    if (img != null) {
                        if (id1 != 1 && id1 != 2 && id1 != 3) {
                            when (id1) {
                                4 -> {
                                    eqId = 1
                                }
                                5 -> {
                                    eqId = 2
                                }
                                6 -> {
                                    eqId = 3
                                }
                            }
                        } else {
                            eqId = id1 as Int
                        }


                        if (eqId != null) {
                            if (eqId!! < i8) {
                                b.setImageDrawable(img)
                                val image = imageFinder(img)
                                updateDatabase(image, b.id)
                                count++
                                i8 = eqId!!
                                idArray[7] = id1
                                b8Filled = true
                                hideMe()
                                checkWinner()
                                turnSelector()
                                id1 = null
                                reSet = false
                                symbolSelected = null
                            }
                        }
                    }
                }
            }
        }

        val b9 = View.OnClickListener { v ->
            if (isMymove_1) {
                val b = v as ImageButton
                var eqId: Int? = null
                if (!winner) {
                    val img: Drawable? = imageSelector(id1)
                    if (img != null) {
                        if (id1 != 1 && id1 != 2 && id1 != 3) {
                            when (id1) {
                                4 -> {
                                    eqId = 1
                                }
                                5 -> {
                                    eqId = 2
                                }
                                6 -> {
                                    eqId = 3
                                }
                            }
                        } else {
                            eqId = id1 as Int
                        }


                        if (eqId != null) {
                            if (eqId!! < i9) {
                                b.setImageDrawable(img)
                                val image = imageFinder(img)
                                updateDatabase(image, b.id)
                                count++
                                i9 = eqId!!
                                idArray[8] = id1
                                b9Filled = true
                                hideMe()
                                checkWinner()
                                turnSelector()
                                id1 = null
                                reSet = false
                                symbolSelected = null
                            }
                        }
                    }
                }
            }
        }

        resetBoard.setOnClickListener { v ->
            val b = v as Button
            if (reSet) {
                b.startAnimation(scaleUp)
                b.startAnimation(scaleDown)
                FirebaseDatabase.getInstance().reference.child("Board Reset").child(code!!).push().child("1").setValue("1")
            }
            else
            {
                b.startAnimation(myAnim)
            }

        }

        turnButton.setOnClickListener { v ->
            val b = v as Button
            if (reSet) {
                b.startAnimation(scaleUp)
                b.startAnimation(scaleDown)
                FirebaseDatabase.getInstance().reference.child("isTurnInverted").child(code!!).push().child("1").setValue("1")
            }
            else
            {
                b.startAnimation(myAnim)
            }

        }

        ob1.setOnClickListener(bO)
        ob2.setOnClickListener(bO)
        om1.setOnClickListener(mO)
        om2.setOnClickListener(mO)
        os1.setOnClickListener(sO)
        os2.setOnClickListener(sO)
        xb1.setOnClickListener(bX)
        xb2.setOnClickListener(bX)
        xm1.setOnClickListener(mX)
        xm2.setOnClickListener(mX)
        xs1.setOnClickListener(sX)
        xs2.setOnClickListener(sX)

        button1.setOnClickListener(b1)
        button2.setOnClickListener(b2)
        button3.setOnClickListener(b3)
        button4.setOnClickListener(b4)
        button5.setOnClickListener(b5)
        button6.setOnClickListener(b6)
        button7.setOnClickListener(b7)
        button8.setOnClickListener(b8)
        button9.setOnClickListener(b9)
    }

    private fun imageFinder(img: Drawable): Int {
        return when (img) {
            drawableOb -> 1
            drawableOm -> 2
            drawableOs -> 3
            drawableXb -> 4
            drawableXm -> 5
            else -> 6
        }
    }

    private fun imageSelector(no: Int?): Drawable? {
        val img: Drawable
        when (no) {
            1 -> {
                img = drawableOb
                return img
            }
            2 -> {
                img = drawableOm
                return img
            }
            3 -> {
                img = drawableOs
                return img
            }
            4 -> {
                img = drawableXb
                return img
            }
            5 -> {
                img = drawableXm
                return img
            }
            6 -> {
                img = drawableXs
                return img
            }
            else -> {
                return null
            }
        }

    }

    private fun displayToast() {
        if (winnerX)
        {
            if (isMymove) {
                Snackbar.make(layout, "You Win !!", Snackbar.LENGTH_LONG)
                    .setAnchorView(layout).show()
                winMusic.start()
                Handler().postDelayed({
                    winMusic.release()
                    reStart() }, 2000)
            } else
            {
                Snackbar.make(layout, "You Loose. Opponent Wins", Snackbar.LENGTH_LONG)
                    .setAnchorView(layout).show()
                looseMusic.start()
                Handler().postDelayed({
                    looseMusic.release()
                    reStart() }, 2000)
            }
        }
        else if (winnerO)
        {
            if (!isMymove) {
                Snackbar.make(layout, "You Win !!", Snackbar.LENGTH_LONG)
                    .setAnchorView(layout).show()
                winMusic.start()

                Handler().postDelayed({
                    winMusic.release()
                    reStart() }, 2000)
            } else
            {
                Snackbar.make(layout, "You Loose. Opponent Wins", Snackbar.LENGTH_LONG)
                    .setAnchorView(layout).show()
                looseMusic.start()
                Handler().postDelayed({
                    looseMusic.release()
                    reStart() }, 2000)
            }
        }
        else if (winnerTie)
        {
            Snackbar.make(layout, "Its a Tie. Try Again !", Snackbar.LENGTH_LONG)
                .setAnchorView(layout).show()
            looseMusic.start()

            Handler().postDelayed({
                looseMusic.release()
                reStart() }, 2000)
        }


    }

    private fun checkWinner() {
        var text = ""

        if (((idArray[0] == 1 || idArray[0] == 2 || idArray[0] == 3)
                    && (idArray[3] == 1 || idArray[3] == 2 || idArray[3] == 3)
                    && (idArray[6] == 1 || idArray[6] == 2 || idArray[6] == 3))
            || ((idArray[1] == 1 || idArray[1] == 2 || idArray[1] == 3)
                    && (idArray[4] == 1 || idArray[4] == 2 || idArray[4] == 3)
                    && (idArray[7] == 1 || idArray[7] == 2 || idArray[7] == 3))
            || ((idArray[2] == 1 || idArray[2] == 2 || idArray[2] == 3)
                    && (idArray[5] == 1 || idArray[5] == 2 || idArray[5] == 3)
                    && (idArray[8] == 1 || idArray[8] == 2 || idArray[8] == 3))
            || ((idArray[0] == 1 || idArray[0] == 2 || idArray[0] == 3)
                    && (idArray[1] == 1 || idArray[1] == 2 || idArray[1] == 3)
                    && (idArray[2] == 1 || idArray[2] == 2 || idArray[2] == 3))
            || ((idArray[3] == 1 || idArray[3] == 2 || idArray[3] == 3)
                    && (idArray[4] == 1 || idArray[4] == 2 || idArray[4] == 3)
                    && (idArray[5] == 1 || idArray[5] == 2 || idArray[5] == 3))
            || ((idArray[6] == 1 || idArray[6] == 2 || idArray[6] == 3)
                    && (idArray[7] == 1 || idArray[7] == 2 || idArray[7] == 3)
                    && (idArray[8] == 1 || idArray[8] == 2 || idArray[8] == 3))
            || ((idArray[0] == 1 || idArray[0] == 2 || idArray[0] == 3)
                    && (idArray[4] == 1 || idArray[4] == 2 || idArray[4] == 3)
                    && (idArray[8] == 1 || idArray[8] == 2 || idArray[8] == 3))
            || ((idArray[2] == 1 || idArray[2] == 2 || idArray[2] == 3)
                    && (idArray[4] == 1 || idArray[4] == 2 || idArray[4] == 3)
                    && (idArray[6] == 1 || idArray[6] == 2 || idArray[6] == 3))
        ) {
            sp[p2 - 1] += 1
            scoreText(sp)
            winner = true
            winnerO = true
            reSet = true
            displayToast()
        }
        
        else if (((idArray[0] == 4 || idArray[0] == 5 || idArray[0] == 6)
                    && (idArray[3] == 4 || idArray[3] == 5 || idArray[3] == 6)
                    && (idArray[6] == 4 || idArray[6] == 5 || idArray[6] == 6))
            || ((idArray[1] == 4 || idArray[1] == 5 || idArray[1] == 6)
                    && (idArray[4] == 4 || idArray[4] == 5 || idArray[4] == 6)
                    && (idArray[7] == 4 || idArray[7] == 5 || idArray[7] == 6))
            || ((idArray[2] == 4 || idArray[2] == 5 || idArray[2] == 6)
                    && (idArray[5] == 4 || idArray[5] == 5 || idArray[5] == 6)
                    && (idArray[8] == 4 || idArray[8] == 5 || idArray[8] == 6))
            || ((idArray[0] == 4 || idArray[0] == 5 || idArray[0] == 6)
                    && (idArray[1] == 4 || idArray[1] == 5 || idArray[1] == 6)
                    && (idArray[2] == 4 || idArray[2] == 5 || idArray[2] == 6))
            || ((idArray[3] == 4 || idArray[3] == 5 || idArray[3] == 6)
                    && (idArray[4] == 4 || idArray[4] == 5 || idArray[4] == 6)
                    && (idArray[5] == 4 || idArray[5] == 5 || idArray[5] == 6))
            || ((idArray[6] == 4 || idArray[6] == 5 || idArray[6] == 6)
                    && (idArray[7] == 4 || idArray[7] == 5 || idArray[7] == 6)
                    && (idArray[8] == 4 || idArray[8] == 5 || idArray[8] == 6))
            || ((idArray[0] == 4 || idArray[0] == 5 || idArray[0] == 6)
                    && (idArray[4] == 4 || idArray[4] == 5 || idArray[4] == 6)
                    && (idArray[8] == 4 || idArray[8] == 5 || idArray[8] == 6))
            || ((idArray[2] == 1 || idArray[2] == 2 || idArray[2] == 3)
                    && (idArray[4] == 1 || idArray[4] == 2 || idArray[4] == 3)
                    && (idArray[6] == 1 || idArray[6] == 2 || idArray[6] == 3))
        ) {
            sp[p1 - 1] += 1
            scoreText(sp)
            winner = true
            winnerX = true
            reSet = true
            displayToast()
        }
        

        if (!winner && b1Filled && b2Filled && b3Filled && b4Filled && b5Filled && b6Filled
            && b7Filled && b8Filled && b9Filled && !ob1.isEnabled && !ob2.isEnabled
            && !xb1.isEnabled && !xb2.isEnabled && !om1.isEnabled && !om2.isEnabled
            && !xm1.isEnabled && !xm2.isEnabled) {
            winner = true
            winnerTie = true
            displayToast()
        } else if (!winner && counter[0] == 2 && counter[1] == 2 && counter[2] == 2
            && counter[3] == 2 && counter[4] == 2 && counter[5] == 2) {
            winner = true
            winnerTie = true
            reSet = true
            displayToast()
        }
    }

    private fun hideMe() {

        val symbolChosen: ImageButton = findViewById(symbolSelected!!)
        symbolChosen.animate().alpha(0f).duration = 400
        symbolChosen.isEnabled = false

    }

    private fun turnSelector() {
        if (isMymove_1) {
            turn.text = "Opponent's Move"
        } else {
            turn.text = "Your Move"
        }
    }

    private fun moveOnline(data: ArrayList<Int>, move: Boolean) {
        if(move)
        {
            val image = when(data[0])
            {
                1 -> drawableOb
                2 -> drawableOm
                3 -> drawableOs
                4 -> drawableXb
                5 -> drawableXm
                else -> drawableXs
            }
            count += 1
            val buttonID: Int = data[1]

            var buttonIndex: Int
            when(buttonID){
                button1.id -> {
                    buttonIndex = 0
                    b1Filled = true
                }
                button2.id -> {
                    buttonIndex = 1
                    b2Filled = true
                }
                button3.id -> {
                    buttonIndex = 2
                    b3Filled = true
                }
                button4.id -> {
                    buttonIndex = 3
                    b4Filled = true
                }
                button5.id -> {
                    buttonIndex = 4
                    b5Filled = true
                }
                button6.id -> {
                    buttonIndex = 5
                    b6Filled = true
                }
                button7.id -> {
                    buttonIndex = 6
                    b7Filled = true
                }
                button8.id -> {
                    buttonIndex = 7
                    b8Filled = true
                }
                else -> {
                    buttonIndex = 8
                    b9Filled = true
                }
            }
            idArray[buttonIndex] = data[0]
            val buttonSelected: ImageButton = findViewById(buttonID)
            buttonSelected.setImageDrawable(image)
            val symbolButton: ImageButton = findViewById(data[2])
            symbolButton.animate().alpha(0f).duration=400
            symbolButton.isEnabled = false
            checkWinner()
        }
    }

    private fun reappear() {
        ob1.alpha = 0f
        ob2.alpha = 0f
        om1.alpha = 0f
        om2.alpha = 0f
        os1.alpha = 0f
        os2.alpha = 0f
        xb1.alpha = 0f
        xb2.alpha = 0f
        xm1.alpha = 0f
        xm2.alpha = 0f
        xs1.alpha = 0f
        xs2.alpha = 0f


        ob1.animate().alpha(1f).duration = 1500
        ob2.animate().alpha(1f).duration = 1500
        om1.animate().alpha(1f).duration = 1500
        om2.animate().alpha(1f).duration = 1500
        os1.animate().alpha(1f).duration = 1500
        os2.animate().alpha(1f).duration = 1500
        xb1.animate().alpha(1f).duration = 1500
        xb2.animate().alpha(1f).duration = 1500
        xm1.animate().alpha(1f).duration = 1500
        xm2.animate().alpha(1f).duration = 1500
        xs1.animate().alpha(1f).duration = 1500
        xs2.animate().alpha(1f).duration = 1500

        ob1.isEnabled = true
        ob2.isEnabled = true
        om1.isEnabled = true
        om2.isEnabled = true
        os1.isEnabled = true
        os2.isEnabled = true
        xb1.isEnabled = true
        xb2.isEnabled = true
        xm1.isEnabled = true
        xm2.isEnabled = true
        xs1.isEnabled = true
        xs2.isEnabled = true

    }

    private fun reStart() {
        if (boardReset)
        {
            Snackbar.make(turnButton, "Board has been RESET", Snackbar.LENGTH_SHORT)
                .setAnchorView(turnButton).show()
            sp = arrayOf(0, 0)
            scoreText.text = "Player 1 : ${sp[0]}  |  PLayer 2 : ${sp[1]} "
            boardReset = false

        }

        button1.setImageDrawable(null)
        button2.setImageDrawable(null)
        button3.setImageDrawable(null)
        button4.setImageDrawable(null)
        button5.setImageDrawable(null)
        button6.setImageDrawable(null)
        button7.setImageDrawable(null)
        button8.setImageDrawable(null)
        button9.setImageDrawable(null)
        count = 1
        winner = false
        idArray = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
        reSet = true
        reappear()
        i1 = 4
        i2 = 4
        i3 = 4
        i4 = 4
        i5 = 4
        i6 = 4
        i7 = 4
        i8 = 4
        i9 = 4
        id1 = null
        counter = arrayOf(0, 0, 0, 0, 0, 0)
        turn.text = "Player 1's turn"
        b1Filled = false
        b2Filled = false
        b3Filled = false
        b4Filled = false
        b5Filled = false
        b6Filled = false
        b7Filled = false
        b8Filled = false
        b9Filled = false

        winnerO = false
        winnerX = false
        isMymove = isCodemaker
        isMymove_1 = isCodemaker

        if (tButton)
        {
            invertTurn()
        }
        else
        {
            if(isCodemaker)
            {
                turn.text = "Your Turn"
            }
            else
            {
                turn.text = "Opponent's Turn"
            }
        }
        FirebaseDatabase.getInstance().reference.child("data").child(code!!).removeValue()
        FirebaseDatabase.getInstance().reference.child("isTurnInverted").child(code!!).removeValue()
    }

    private fun removeCode() {
        if(isCodemaker)
        {
            FirebaseDatabase.getInstance().reference.child("codes").child(keyValue!!).removeValue()
        }
    }

    override fun onBackPressed() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Info")
            .setMessage("Exit Game ?")
            .setPositiveButton("YES") { dialog, which ->
                notClosed = true
                removeCode()
                if (isCodemaker)
                {
                    FirebaseDatabase.getInstance().reference.child("data").child(code!!).removeValue()
                    FirebaseDatabase.getInstance().reference.child("isTurnInverted").child(code!!).removeValue()
                    FirebaseDatabase.getInstance().reference.child("Board Reset").child(code!!).removeValue()
                }
                exitProcess(0) }
            .setNegativeButton("NO") { dialog, which ->

            }.show()

    }

    private fun updateDatabase(img: Int, cellID: Int) {
        val a = listOf(img, cellID, symbolSelected!!)
        FirebaseDatabase.getInstance().reference.child("data").child(code!!).child("count $count").setValue(a)

    }

    private fun invertTurn(){
        Snackbar.make(turnButton, "The turns have been INVERTED", Snackbar.LENGTH_SHORT)
            .setAnchorView(turnButton).show()
        if(isMymove_1)
        {
            turn.text = "Opponent's Turn"
            instruction.text = "Your Shape : X"

        }
        else
        {
            turn.text = "Your Turn"
            instruction.text = "Your Shape : O"

        }
        isMymove_1 = !isMymove_1
    }

    override fun onStop() {
        if (!notClosed)
        {
            removeCode()
            if (isCodemaker)
            {
                FirebaseDatabase.getInstance().reference.child("data").child(code!!).removeValue()
                FirebaseDatabase.getInstance().reference.child("isTurnInverted").child(code!!).removeValue()
                FirebaseDatabase.getInstance().reference.child("Board Reset").child(code!!).removeValue()
            }
        }
        super.onStop()
    }

    private fun scoreText(sp: Array<Int>)
    {
        if(isCodemaker)
        {
            scoreText.text = "Mine : ${sp[0]}  |  Opponent's : ${sp[1]} "
        }
        else
        {
            scoreText.text = "Mine : ${sp[1]}  |  Opponent's : ${sp[0]} "
        }
    }
}