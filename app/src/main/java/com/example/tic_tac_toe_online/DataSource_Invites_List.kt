package com.example.tic_tac_toe_online

class DataSource_Invites_List {

    companion object{
        val list = ArrayList<Invites>()
        fun createDataSet(): ArrayList<Invites>{
            lateinit var inviteeID: String
            lateinit var inviteeName: String
            lateinit var inviteeEmail: String
            lateinit var inviteePhotoURL: String
            lateinit var inviteeToken: String
            lateinit var inviteeCode: String
            // TODO: Dont know what i did down...check later
            val db = MyDatabaseHelper_Invites(MainActivityContext)
            val cur = db.readAllData()
            cur.use {
                if (it != null) {
                    while (it.moveToNext()){
                        with(it){
                            inviteeID = getString(1)
                            inviteeName = getString(2)
                            inviteeEmail = getString(3)
                            inviteePhotoURL = getString(4)
                            inviteeToken = getString(5)
                             inviteeCode = getString(6)
                        }
                        list.add(
                            Invites(
                                inviteeID,
                                inviteeName,
                                inviteeEmail,
                                inviteePhotoURL,
                                inviteeToken,
                                inviteeCode
                            )
                        )
                    }
                }
            }
            return list
        }
    }
}