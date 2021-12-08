package com.example.tic_tac_toe_online

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject


class FcmNotificationsSender(
    var userFcmToken: String,
    var title: String,
    var body: String,
    var mContext: Context,
    mActivity: Activity
) {
    var mActivity: Activity = mActivity
    private var requestQueue: RequestQueue? = null
    private val postUrl = "https://fcm.googleapis.com/fcm/send"
    private val fcmServerKey =
        "AAAADTSwps4:APA91bH7vdjopmF1rLQwxIBVJN_K7Q-F3hmbNxJXmggUQS0NI5tRFOkFzgBYAyWFzR7SBneS4WWpd2ZrvaoF05cdj3qPT2H54Z0P07yZljRM66a5KJnZUxRFPqYC83GX9-kFJ03zc5Xj"

    fun SendNotifications(){
        requestQueue = Volley.newRequestQueue(mActivity)
        val mainObj = JSONObject()
        try{
            mainObj.put("to", userFcmToken)
//            val notiObject = JSONObject()
            val dataObject = JSONObject()
//            notiObject.put("title", title)
//            notiObject.put("body", body)
//            notiObject.put("icon", "temp_icon") // enter icon that exists in drawable only
            dataObject.put("title", title)
            dataObject.put("body", body)
            dataObject.put("icon", "temp_icon") // enter icon that exists in drawable only
            dataObject.put("code", code)
            dataObject.put("id", account?.id)
            dataObject.put("name", account?.displayName)
            dataObject.put("email", account?.email)
            dataObject.put("photoURL", account?.photoUrl.toString())
            dataObject.put("token", myToken)

//            mainObj.put("notification", notiObject)
            mainObj.put("data", dataObject)

            val request: JsonObjectRequest = object : JsonObjectRequest(Method.POST, postUrl, mainObj,
//                object: Response.Listener<JSONObject?>{
//                override fun onResponse(response: JSONObject?) {
//                    Toast.makeText(mContext, "Notification Delivered", Toast.LENGTH_SHORT).show()
//                }
//            }
                Response.Listener { response ->
                    Toast.makeText(mContext, "Notification Delivered", Toast.LENGTH_SHORT).show()
                    Log.d("RESPONSE", " Response = $response")
                }
                ,Response.ErrorListener { error ->
                    // TODO: Handle error
                }){
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val header: MutableMap<String, String> = HashMap()
                    header["content-type"] = "application/json"
                    header["authorization"] = "key=$fcmServerKey"
                    return header
                }
            }
            requestQueue!!.add(request)

        }catch (e: JSONException){
            e.printStackTrace()
        }
    }

}