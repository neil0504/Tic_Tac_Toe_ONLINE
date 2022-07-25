package com.neilkrishna_kabara.tic_tac_toe_online.model

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import com.neilkrishna_kabara.tic_tac_toe_online.R

//class PlayerDetails private constructor(val ID: String, NAME: String, EMAIL: String, PHOTO_URL: String){
//    companion object: SingletonHolder<PlayerDetails, String, String, String, String >(::PlayerDetails)
//}

//class Details(val ID: String, NAME: String, EMAIL: String, PHOTO_URL: String){
//    companion object: Details {
//
//    }
//}

class PlayerDetails(private val context: Context, private val playerID: String?, private val playerName: String?, private val playerEmail: String?, private val playerPhotoURL: Uri?){

    fun getID(): String {
        return playerID ?: "0000"
    }

    fun getName(): String {
        return playerName ?: "New Member"
    }

    fun getEmail(): String {
        return playerEmail ?: "null"
    }

    fun getPhotoURL(): String {
        if (playerPhotoURL == null){
            val imageUri = Uri.parse(
                ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + context.resources.getResourcePackageName(R.drawable.guest)
                        + '/' + context.resources.getResourceTypeName(R.drawable.guest) + '/' + context.resources.getResourceEntryName(
                    R.drawable.guest
                ) )

            return imageUri.toString()
        }
        return playerPhotoURL.toString()

    }

}