package com.example.tic_tac_toe_online

import android.database.Cursor
import android.util.Log

class DataSource_for_rv2_Invitation_Fragment {

    companion object{
        fun createDataSet(query: Cursor?): ArrayList<InvitationFragment_Invites>{

            var ID: String? = null
            var NAME: String? = null
            var EMAIL: String? = null
            var PHOTOURL: String? = null
            var USER_TOKEN: String? = null
            val list = ArrayList<InvitationFragment_Invites>()

            if (query!!.count > 0) {
                query.use {
                    while (it.moveToNext()) {
                        with(it) {
                            ID = getString(1)
                            NAME = getString(2)
                            EMAIL = getString(3)
                            PHOTOURL = getString(4)
                            USER_TOKEN = getString(5)
                            Log.d(
                                "DATA_RETRIEVED",
                                "ID = $ID, NAME = $NAME, EMAIL = $EMAIL, PHOTOURL = $PHOTOURL, USER_TOKEN = $USER_TOKEN"
                            )
                            list.add(
                                InvitationFragment_Invites(
                                    ID!!,
                                    NAME!!,
                                    EMAIL!!,
                                    PHOTOURL!!,
                                    USER_TOKEN!!

                                )
                            )
                        }
                    }
                }
            }else{
                Log.d("DATA_RETRIEVED", "No Value in Database")
            }

            return list
        }
    }
}

