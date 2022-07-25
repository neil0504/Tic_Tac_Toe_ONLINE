package com.neilkrishna_kabara.tic_tac_toe_online

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelID = "notification_channel"
const val channelName = "com.example.tic_tac_toe_online"
class MyFirebaseMessagingService : FirebaseMessagingService() {
    var mNotificationManager: NotificationManager? = null

    @SuppressLint("MissingPermission")

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        //            Toast.makeText(this, "Data = ${remoteMessage.data.get("data")}", Toast.LENGTH_SHORT).show()
        Log.d("AAAAAA", "Data = ${remoteMessage.data.get("code")}, title = ${remoteMessage.data.get("title")}")
        Log.d("AAAAAA", "Title = ${remoteMessage.data.get("title")}, Body = ${remoteMessage.data.get("body")}")
        val joiningCode = remoteMessage.data["code"]!!
        val inviteeID = remoteMessage.data["id"]
        val inviteeName = remoteMessage.data["name"]
        val inviteeEmail = remoteMessage.data["email"]
        val inviteePhotoURL = remoteMessage.data["photoURL"]
        val inviteeToken = remoteMessage.data["token"]

        if (inviteeID != null && inviteeName != null && inviteeEmail != null && inviteePhotoURL != null && inviteeToken != null) {
            generateNotification(remoteMessage.data.get("title")!!, remoteMessage.data.get("body")!!, joiningCode, inviteeID, inviteeName, inviteeEmail, inviteePhotoURL, inviteeToken)
        }

    }

    fun generateNotification(
        title: String,
        body: String,
        joiningCode: String,
        inviteeID: String,
        inviteeName: String,
        inviteeEmail: String,
        inviteePhotoURL: String,
        inviteeToken: String
    )
    {
        val intent = Intent(this, OnlineConnection::class.java)
        intent.putExtra("code", joiningCode)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

//        Channel ID, Channel Name
        var builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title, body))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0, builder.build())
        val db = MyDatabaseHelper_Invites(this)
        db.addRecord(inviteeID, inviteeName, inviteeEmail, inviteePhotoURL, inviteeToken, joiningCode)
    }

    private fun getRemoteView(title: String, body: String): RemoteViews? {
        val remoteView = RemoteViews("com.example.tic_tac_toe_online", R.layout.notification)
        remoteView.setTextViewText(R.id.title, title)
        remoteView.setTextViewText(R.id.description, body)
        remoteView.setImageViewResource(R.id.appLogo, R.drawable.guest)

        return remoteView
    }

    private fun addUser(){

    }
}