package com.example.tic_tac_toe_online

import android.content.ContentResolver
import android.content.Context
import android.net.Uri

//class PlayerDetails private constructor(val ID: String, NAME: String, EMAIL: String, PHOTO_URL: String){
//    companion object: SingletonHolder<PlayerDetails, String, String, String, String >(::PlayerDetails)
//}

//class Details(val ID: String, NAME: String, EMAIL: String, PHOTO_URL: String){
//    companion object: Details {
//
//    }
//}

class PlayerDetails(val context: Context, ID: String, NAME: String, EMAIL: String?, PHOTO_URL: Uri?){
    private var playerID = ID
    private var playerName = NAME
    private var playerEmail: String? = EMAIL
    private var playerPhotoURL = PHOTO_URL

    fun getID(): String {
        return playerID
    }

    fun getName(): String {
        return playerName
    }

    fun getEmail(): String {
        if (playerEmail == null){
            return "null"
        }
        return playerEmail as String
    }

    fun getPhotoURL(): String {
        if (playerPhotoURL == null){
            val imageUri = Uri.parse(
                ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + context.resources.getResourcePackageName(R.drawable.guest)
                        + '/' + context.resources.getResourceTypeName(R.drawable.guest) + '/' + context.resources.getResourceEntryName(R.drawable.guest) )

            return imageUri.toString()
        }
        return playerPhotoURL.toString()

    }

}