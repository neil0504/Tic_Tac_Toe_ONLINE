package com.example.tic_tac_toe_online

import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class DataSource_RV1_InvitationFragment {
    companion object {
        var id_: String? = null
        var name: String? = null
        var email: String? = null
        var photoURL: String? = null
        var token: String? = null
        fun createDataSet(): ArrayList<InvitationFragment_Lobby> {
            val list = ArrayList<InvitationFragment_Lobby>()

            FirebaseDatabase.getInstance().reference.child("Joiners").child(code!!).addChildEventListener(object: ChildEventListener{
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    Log.d("****", "OnChildAddded Listener method clicked")
                    val l = ArrayList<String>()
                    val childs = snapshot.children
//                    Log.d("*****", "val count= ${childs.count()}")
                    childs.forEach{
                        val value = it.value.toString()
                        Log.d("*****", "Each val = $value")
                        l.add(value)
                    }

                    Log.d("*****:", "Value : $childs")
                    email = l[2]
                    id_ = l[0]
                    name = l[1]
                    photoURL = l[3]
                    token = l[4]
                    Log.d("****", "Value of email: $email, Value of ID: $id_,Value of name: $name, Value of photoURL: $photoURL ")
                    //                    else{
//                        id_ = null
//                        name = null
//                        email = null
//                        photoURL = null
//                        token = null
//                    }
                    data_Lobby.add(0, InvitationFragment_Lobby(
                            id_,
                            name,
                            email,
                            photoURL,
                            token
                        ))
                    invitationfragment_LobbyRecyclerviewAdapter.notifyItemInserted(0)
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    TODO("Not yet implemented")
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
            return list
        }
    }
}