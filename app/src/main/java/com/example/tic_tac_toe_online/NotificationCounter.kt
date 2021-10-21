package com.example.tic_tac_toe_online

import android.util.Log
import android.view.View
import android.widget.TextView

class NotificationCounter(view: View) {
    var notificationCounter: TextView= view.findViewById(R.id.counter)
//    var bell: ImageView = view.findViewById(R.id.notif_icon)
//    class NotificationCounter constructor(v: View){
//        var notificationCounter: TextView= v.findViewById(R.id.counter)
//        var bell: ImageView = v.findViewById(R.id.notif_icon)
//    }


    private final val MAX_NUMBER = 99
    private var notification_number_counter = 1
    fun increaseNumber(){
        notification_number_counter += 1
        if (notification_number_counter > MAX_NUMBER){
            Log.d("Counter", "Maximum number reached")
        }else{
            notificationCounter.text = notification_number_counter.toString()
            Log.d("Neillll", "Value of counter in string = ${notification_number_counter.toString()}")
        }
    }

}