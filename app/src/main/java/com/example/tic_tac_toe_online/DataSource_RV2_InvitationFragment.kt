package com.example.tic_tac_toe_online

import android.database.Cursor
import android.util.Log

class DataSource_RV2_InvitationFragment {

    companion object{
        private var idd: String? = null
        private var name: String? = null
        private var email: String? = null
        private var photoURL: String? = null
        private var userToken: String? = null
        fun createDataSet(query: Cursor?): ArrayList<InvitationFragment_Invites>{
            val list = ArrayList<InvitationFragment_Invites>()
            if (query != null) {
                if (query.count > 0) {
                    query.use {
                        while (it.moveToNext()) {
                            with(it) {
                                idd = getString(1)
                                name = getString(2)
                                email = getString(3)
                                photoURL = getString(4)
                                userToken = getString(5)
                                Log.d(
                                    "DATA_RETRIEVED",
                                    "ID = $idd, NAME = $name, EMAIL = $email, PHOTOURL = $photoURL, USER_TOKEN = $userToken"
                                )
                                list.add(
                                    InvitationFragment_Invites(
                                        idd!!,
                                        name!!,
                                        email!!,
                                        photoURL!!,
                                        userToken!!

                                    )
                                )
                            }
                        }
                    }
                } else {
                    Log.d("DATA_RETRIEVED", "No Value in Database")
                }
            }

            return list
        }
    }
}

