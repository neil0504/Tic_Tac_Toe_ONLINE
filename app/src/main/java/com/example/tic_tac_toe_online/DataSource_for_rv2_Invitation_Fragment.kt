package com.example.tic_tac_toe_online

import android.content.SharedPreferences
import android.util.Log

class DataSource_for_rv2_Invitation_Fragment {

    companion object{
        fun createDataSet(sharedPreferences: SharedPreferences? ): ArrayList<InvitationFragment_Invites>{
            var id_stringset: MutableSet<String>? = null
            var name_stringset: MutableSet<String>? = null
            var email_stringset: MutableSet<String>? = null
            var photoURL_stringset: MutableSet<String>? = null
            val list = ArrayList<InvitationFragment_Invites>()
            if(sharedPreferences != null) {
                id_stringset = sharedPreferences.getStringSet("ID", null)
                Log.d("DATASOURCE_INVITES", "VALUE OF id_stringset = $id_stringset")
                name_stringset = sharedPreferences.getStringSet("NAME", null)
                Log.d("DATASOURCE_INVITES", "VALUE OF name_stringset = $name_stringset")
                email_stringset = sharedPreferences.getStringSet("EMAIL", null)
                Log.d("DATASOURCE_INVITES", "VALUE OF email_stringset = $email_stringset")
                photoURL_stringset = sharedPreferences.getStringSet("PHOTO_URL", null)
                Log.d("DATASOURCE_INVITES", "VALUE OF photoURL_stringset = $photoURL_stringset")
            }
            if (id_stringset != null){
                for (item in id_stringset) {
                    list.add(
                        InvitationFragment_Invites(
                            item,
                            name_stringset!!.elementAt(id_stringset.indexOf(item)),
                            email_stringset!!.elementAt(id_stringset.indexOf(item)),
                            photoURL_stringset!!.elementAt(id_stringset.indexOf(item))
                        )
                    )
                }
            }
            return list
        }
    }
}

