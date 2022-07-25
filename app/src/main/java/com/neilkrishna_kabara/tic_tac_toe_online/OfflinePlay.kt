package com.neilkrishna_kabara.tic_tac_toe_online

import android.content.pm.ActivityInfo
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.snackbar.Snackbar
import com.neilkrishna_kabara.tic_tac_toe_online.databinding.ActivityOfflinePlayBinding

private const val TAG = "OfflinePlay"
//private val TAG: String = "offline_play"
class OfflinePlay : AppCompatActivity() {

    private val binding by lazy { ActivityOfflinePlayBinding.inflate(layoutInflater) }


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
    private var isInverted = false
    private var counter = arrayOf(0, 0, 0, 0, 0, 0)

    private var p1 = 1
    private var p2 = 2

    private var reSet = true
    private var turnIsInverted = false
    private var b1Filled = false
    private var b2Filled = false
    private var b3Filled = false
    private var b4Filled = false
    private var b5Filled = false
    private var b6Filled = false
    private var b7Filled = false
    private var b8Filled = false
    private var b9Filled = false

    private var sp1: Int = 0
    private var sp2: Int = 0
    private var sp = arrayOf(sp1, sp2)

//    private lateinit var layout: ConstraintLayout
//    private lateinit var linearLayout: LinearLayout

    private val layout by lazy { binding.constaintLayout2 }
    private val linearLayout by lazy { binding.linearLayout }

    private val ob1 by lazy { binding.ob1 }
//    private val ob1: ImageButton get() {
//        return binding.ob1
//    }
    private val ob2 by lazy { binding.ob2 }
    private val om1 by lazy { binding.om1 }
    private val om2 by lazy { binding.om2 }
    private val os1 by lazy { binding.os1 }
    private val os2 by lazy { binding.os2 }

    private val xb1 by lazy { binding.xb1 }
    private val xb2 by lazy { binding.xb2 }
    private val xm1 by lazy { binding.xm1 }
    private val xm2 by lazy { binding.xm2 }
    private val xs1 by lazy { binding.xs1 }
    private val xs2 by lazy { binding.xs2 }

//    private val restart by lazy { binding.reset }
    private val restart by lazy { binding.reset }
    private val invertControl by lazy { binding.invertControl }
    private val turnButton by lazy { binding.turnButton }
    private val breset by lazy { binding.breset }

//    private val drawableOb: Drawable by lazy {
////        ContextCompat.getDrawable(this, R.drawable.bo)
//        ResourcesCompat.getDrawable(this.resources, R.drawable.bo, null)
//    }
    private val drawableOb: Drawable by lazy { ResourcesCompat.getDrawable(this.resources, R.drawable.bo, null)!! }
    private val drawableOm: Drawable by lazy { ResourcesCompat.getDrawable(this.resources, R.drawable.mo, null)!! }
    private val drawableOs: Drawable by lazy { ResourcesCompat.getDrawable(this.resources, R.drawable.so, null)!! }
    private val drawableXb: Drawable by lazy { ResourcesCompat.getDrawable(this.resources, R.drawable.bx, null)!! }
    private val drawableXm: Drawable by lazy { ResourcesCompat.getDrawable(this.resources, R.drawable.mx, null)!! }
    private val drawableXs: Drawable by lazy { ResourcesCompat.getDrawable(this.resources, R.drawable.sx, null)!! }
    private val defaultDrawable by lazy { ResourcesCompat.getDrawable(this.resources, R.drawable.g_rey, null)!! }

    private val name by lazy { binding.textView2 }
    private val turn by lazy { binding.turnTextView }
    private val instruction by lazy { binding.textView4}
    private val scoreText by lazy { binding.scoreText }

    private val button1 by lazy { binding.p11 }
    private val button2 by lazy { binding.p12 }
    private val button3 by lazy { binding.p13 }
    private val button4 by lazy { binding.p21 }
    private val button5 by lazy { binding.p22 }
    private val button6 by lazy { binding.p23 }
    private val button7 by lazy { binding.p31 }
    private val button8 by lazy { binding.p32 }
    private val button9 by lazy { binding.p33 }
//    private lateinit var drawableOb: Drawable
//    private lateinit var drawableOm: Drawable
//    private lateinit var drawableOs: Drawable
//    private lateinit var drawableXb: Drawable
//    private lateinit var drawableXm: Drawable
//    private lateinit var drawableXs: Drawable
//    private lateinit var defaultDrawable: Drawable
//
//    private lateinit var name: TextView
//    private lateinit var turn: TextView
//    private lateinit var instruction: TextView
//    private lateinit var scoreText: TextView
//
//
//    private lateinit var button1: ImageButton
//    private lateinit var button2: ImageButton
//    private lateinit var button3: ImageButton
//    private lateinit var button4: ImageButton
//    private lateinit var button5: ImageButton
//    private lateinit var button6: ImageButton
//    private lateinit var button7: ImageButton
//    private lateinit var button8: ImageButton
//    private lateinit var button9: ImageButton
//
//    private lateinit var ob1: ImageButton
//    private lateinit var ob2: ImageButton
//    private lateinit var om1: ImageButton
//    private lateinit var om2: ImageButton
//    private lateinit var os1: ImageButton
//    private lateinit var os2: ImageButton
//
//    private lateinit var xb1: ImageButton
//    private lateinit var xb2: ImageButton
//    private lateinit var xm1: ImageButton
//    private lateinit var xm2: ImageButton
//    private lateinit var xs1: ImageButton
//    private lateinit var xs2: ImageButton
//
//
//
//    private lateinit var restart: Button
//    private lateinit var invertControl: Button
//    private lateinit var turnButton: Button
//    private lateinit var breset: Button

//    private lateinit var drawableOb = Context

    private var symbolSelected: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

//        layout = binding.constaintLayout2
//        linearLayout = binding.linearLayout
//
//        ob1 = binding.ob1
//        ob2 = binding.ob2
//        om1 = binding.om1
//        om2 = binding.om2
//        os1 = binding.os1
//        os2 = binding.os2
//        ob2 = findViewById(R.id.ob2)
//        om1 = findViewById(R.id.om1)
//        om2 = findViewById(R.id.om2)
//        os1 = findViewById(R.id.os1)
//        os2 = findViewById(R.id.os2)

//        xb1 = binding.xb1
//        xb2 = binding.xb2
//        xm1 = binding.xm1
//        xm2 = binding.xm2
//        xs1 = binding.xs1
//        xs2 = binding.xs2
//        xb1 = findViewById(R.id.xb1)
//        xb2 = findViewById(R.id.xb2)
//        xm1 = findViewById(R.id.xm1)
//        xm2 = findViewById(R.id.xm2)
//        xs1 = findViewById(R.id.xs1)
//        xs2 = findViewById(R.id.xs2)


//        restart = findViewById(R.id.reset)
//        invertControl = findViewById(R.id.invertControl)
//        turnButton = findViewById(R.id.turnButton)
//        breset = findViewById(R.id.breset)
//
//        drawableOb = ob1.drawable
//        drawableOm = om1.drawable
//        drawableOs = os1.drawable
//        drawableXb = xb1.drawable
//        drawableXm = xm1.drawable
//        drawableXs = xs1.drawable

//        val uri = "@drawable/g_rey" // where myresource (without the extension) is the file
//        val imageResource = resources.getIdentifier(uri, null, packageName)
//        defaultDrawable = resources.getDrawable(imageResource)




//        name = binding.textView2
//        turn = binding.turnTextView
//        instruction = binding.textView4
//        scoreText = binding.scoreText
//
//
//        button1 = findViewById(R.id.p11)
//        button2 = findViewById(R.id.p12)
//        button3 = findViewById(R.id.p13)
//        button4 = findViewById(R.id.p21)
//        button5 = findViewById(R.id.p22)
//        button6 = findViewById(R.id.p23)
//        button7 = findViewById(R.id.p31)
//        button8 = findViewById(R.id.p32)
//        button9 = findViewById(R.id.p33)
        
        linearLayout.alpha = 0f

        name.alpha = 0f
        instruction.alpha = 0f
        turn.alpha = 0f
        scoreText.alpha = 0f

        linearLayout.translationY = 50f

        name.translationY = 0f
        instruction.translationY = 0f
        turn.translationY = 0f
        scoreText.translationY = 0f

        name.animate().alpha(1f).translationYBy(10f).duration = 1500
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
        
        val bO = View.OnClickListener { v ->
            val b = v as ImageButton

            val temp = idSelect()
            if (temp == 'A') {
                id1 = 1
                symbolSelected = b.id
            } else {
                id1 = null
                b.startAnimation(myAnim)
            }
            Log.d(TAG, "ID = 1 set")

        }
        
        val mO = View.OnClickListener { v ->
            Log.d(TAG, "MO Clicked")

            val b = v as ImageButton

            val temp = idSelect()
            if (temp == 'A') {
                id1 = 2
                symbolSelected = b.id
            } else {
                id1 = null
                b.startAnimation(myAnim)
            }
            Log.d(TAG, "ID = 2 set")
        }
        
        val sO = View.OnClickListener { v ->
            Log.d(TAG, "SO Clicked")

            val b = v as ImageButton

            val temp = idSelect()
            if (temp == 'A') {
                id1 = 3
                symbolSelected = b.id
            } else {
                id1 = null
                b.startAnimation(myAnim)
            }
            Log.d(TAG, "ID = 3 set")
        }
        
        val bX = View.OnClickListener { v ->
            Log.d(TAG, "BX Clicked")

            val b = v as ImageButton


            val temp = idSelect()
            if (temp == 'B') {
                id1 = 4
                symbolSelected = b.id
            } else {
                id1 = null
                b.startAnimation(myAnim)
            }
            Log.d(TAG, "ID = 4 set")
        }
        
        val mX = View.OnClickListener { v ->
            Log.d(TAG, "MX Clicked")

            val b = v as ImageButton


            val temp = idSelect()
            if (temp == 'B') {
                id1 = 5
                symbolSelected = b.id
            } else {
                id1 = null
                b.startAnimation(myAnim)
            }
            Log.d(TAG, "ID = 5 set")
        }
        
        val sX = View.OnClickListener { v ->
            Log.d(TAG, "SX Clicked")

            val b = v as ImageButton


            val temp = idSelect()
            if (temp == 'B') {
                id1 = 6
                symbolSelected = b.id
            } else {
                id1 = null
                b.startAnimation(myAnim)
            }
            Log.d(TAG, "ID = 6 set")
        }

        button1.setOnClickListener { v ->
            Log.d(TAG, "Button 1 Clicked")
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

        button2.setOnClickListener { v ->
            Log.d(TAG, "Button 2 Clicked")
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

        button3.setOnClickListener { v ->
            Log.d(TAG, "Button 3 Clicked")
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

        button4.setOnClickListener { v ->
            Log.d(TAG, "Button 4 Clicked")
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

        button5.setOnClickListener { v ->
            Log.d(TAG, "Button 5 Clicked")
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

        button6.setOnClickListener { v ->
            Log.d(TAG, "Button 6 Clicked")
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

        button7.setOnClickListener { v ->
            Log.d(TAG, "Button 7 Clicked")
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

        button8.setOnClickListener { v ->
            Log.d(TAG, "Button 8 Clicked")
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

        button9.setOnClickListener { v ->
            Log.d(TAG, "Button 9 Clicked")
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

        restart.setOnClickListener { v ->
            val b = v as Button
            b.startAnimation(scaleUp)
            b.startAnimation(scaleDown)
            reStart()
        }

        invertControl.setOnClickListener { v ->
            val b = v as Button

            if (reSet) {
                b.startAnimation(scaleUp)
                b.startAnimation(scaleDown)
                isInverted = !isInverted
                if (isInverted) {
                    p1 = 2
                    p2 = 1
                    instruction.text = "Player 1 : O\nPlayer 2 : X"
                } else {
                    p1 = 1
                    p2 = 2

                    instruction.text = "Player 1: X\nPlayer 2 : O"
                }
            } else {
                b.startAnimation(myAnim)
            }


        }

        turnButton.setOnClickListener { v ->
            val b = v as Button
            if (reSet) {
                b.startAnimation(scaleUp)
                b.startAnimation(scaleDown)
                if (!turnIsInverted) {
                    if (count == 1) {
                        count = 0
                    } else {
                        count += 1
                    }
                    turn.text = "Player 2's Turn"
                    turnIsInverted = !turnIsInverted
                    id1 = null
                } else {
                    turn.text = "Player 1's Turn"
                    turnIsInverted = !turnIsInverted
                    id1 = null
                }
            } else {
                b.startAnimation(myAnim)
            }
        }

        breset.setOnClickListener { v ->
            val b = v as Button
            b.startAnimation(scaleUp)
            b.startAnimation(scaleDown)
            reStart()
            sp1 = 0
            sp2 = 0
            sp = arrayOf(sp1, sp2)
            scoreText.text = "Player 1 : ${sp[0]}  |  PLayer 2 : ${sp[1]} "
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
        
    }
    private fun idSelect(): Char {
        return if (count % 2 == 0) {
            if (!isInverted) {
                'A'
            } else {
                'B'
            }
        } else {
            if (!isInverted) {
                'B'
            } else {
                'A'
            }
        }
    }

    private fun turnSelector() {
        if (count % 2 == 0) {
            turn.text = "Player 2's Turn"
        } else {
            turn.text = "Player 1's Turn"
        }
    }

    private fun hideMe() {
        val symbolChosen: ImageButton = findViewById(symbolSelected!!)
        symbolChosen.animate().alpha(0f).duration = 400
        symbolChosen.isEnabled = false
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
                    && (idArray[6] == 1 || idArray[6] == 2 || idArray[6] == 3)))
        {
            text = "Player $p2 is the Winner"
            sp[p2 - 1] += 1
            scoreText.text = "Player 1 : ${sp[0]}  |  PLayer 2 : ${sp[1]} "
            winner = true
            displayToast(text)
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
                    && (idArray[6] == 1 || idArray[6] == 2 || idArray[6] == 3)))
        {
            text = "Result: Player $p1 is the Winner"
            sp[p1 - 1] += 1
            scoreText.text = "Player 1 : ${sp[0]}  |  PLayer 2 : ${sp[1]} "
            winner = true
            displayToast(text)
        }

        if (!winner && b1Filled && b2Filled && b3Filled && b4Filled && b5Filled && b6Filled
            && b7Filled && b8Filled && b9Filled && !ob1.isEnabled && !ob2.isEnabled
            && !xb1.isEnabled && !xb2.isEnabled && !om1.isEnabled && !om2.isEnabled
            && !xm1.isEnabled && !xm2.isEnabled)
            {
            text = "Result : Its a TIE"
            winner = true
            displayToast(text)
        } else if (!winner && counter[0] == 2 && counter[1] == 2 && counter[2] == 2
            && counter[3] == 2 && counter[4] == 2 && counter[5] == 2) {
            text = "Result : Its a TIE"
            winner = true
            displayToast(text)
        }


    }

    private fun displayToast(text: String) {
        Snackbar.make(layout, text, Snackbar.LENGTH_SHORT).setAnchorView(layout)
            .show()
        Handler().postDelayed({ reStart() }, 2000)

    }

    private fun reStart() {
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

//        ob1.visibility = View.VISIBLE
//        ob2.visibility = View.VISIBLE
//        om1.visibility = View.VISIBLE
//        om2.visibility = View.VISIBLE
//        os1.visibility = View.VISIBLE
//        os2.visibility = View.VISIBLE
//        xb1.visibility = View.VISIBLE
//        xb2.visibility = View.VISIBLE
//        xm1.visibility = View.VISIBLE
//        xm2.visibility = View.VISIBLE
//        xs1.visibility = View.VISIBLE
//        xs2.visibility = View.VISIBLE

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
}