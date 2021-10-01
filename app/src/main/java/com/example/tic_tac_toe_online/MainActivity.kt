package com.example.tic_tac_toe_online

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val online_btn: Button = findViewById(R.id.b_online)
        val offline_btn: Button = findViewById(R.id.b_offline)

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
}