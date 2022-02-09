package com.example.tic_tac_toe_online

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
private const val TAG = "NotificationFragment"
lateinit var invitesAdapter: InvitesRecyclerViewAdapter
class NotificationFragment(notificationCounterText: TextView) : Fragment(R.layout.notification_fragment), FragmentStateCheck {
    var notifCountText = notificationCounterText
    private lateinit var recycler_view: RecyclerView
    private lateinit var thiscontxt: Context
    lateinit var data: ArrayList<Invites>
    lateinit var handler: Handler
    lateinit var cardView: CardView
    lateinit var defaultText: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.notification_fragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notifCountText.text = NotificationCounter.setCounterToZero()
        cardView = view.findViewById(R.id.cardView)
        defaultText = view.findViewById(R.id.defaultText)
        if (signedInGoogle) {

            cardView.visibility = View.VISIBLE
            defaultText.visibility = View.GONE
            recycler_view = view.findViewById(R.id.recycler_view)
            handler = Handler(Looper.getMainLooper())
            thiscontxt = view.context
            Toast.makeText(thiscontxt, "Fragment Created", Toast.LENGTH_SHORT).show()
            initRecyclerView()
            addDataset()
        }else{
            cardView.visibility = View.GONE
            defaultText.visibility = View.VISIBLE
        }
//        val ob = MyDatabaseHelper_Invites(thiscontxt)
//        MyDatabaseHelper_Invites.setUpdateUIInterfaceListener(object : MyDatabaseHelper_Invites.UpdateUIInterface{
//            override fun updateUI(position: Int) {
//                    invitesAdapter.notifyItemInserted(0)
//            }
//
//        })
    }
    private fun initRecyclerView(){
        recycler_view.apply {
            layoutManager = LinearLayoutManager(thiscontxt)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            invitesAdapter = InvitesRecyclerViewAdapter()
            adapter = invitesAdapter
            invitesAdapter.setOnDeleteClickListnerer(object : InvitesRecyclerViewAdapter.OnDeleteClickListnerer{
                override fun onDeleteClick(position: Int) {
                    val value = data[position]
                    val playerID = value.joinerID
                    val db = MyDatabaseHelper_Invites(thiscontxt)
                    db.deleteRow(playerID)
                    data.removeAt(position)
                    invitesAdapter.notifyItemRemoved(position)
//                    val cur = db.readAllData()
//                    cur.use {
//                        if (it != null) {
//                            while (it.moveToNext()){
//                                with(it){
//                                    Log.d(TAG, "Value = ${getString(1)}")
//                                }
//                            }
//
//                        }
//                    }
//                    mListener.updateUI(position)


                }
            })
            invitesAdapter.setOnPlayClickListnerer(object : InvitesRecyclerViewAdapter.OnPlayClickListnerer{
                override fun onPlayClick(position: Int) {
                    Toast.makeText(thiscontxt, "Play Button Clicked", Toast.LENGTH_SHORT).show()
                    val value = data[position]
                    val playerID = value.joinerID
                    val codeForeJoining = value.playerCode
                    val db = MyDatabaseHelper_Invites(thiscontxt)
                    db.deleteRow(playerID)
                    invitesAdapter.notifyItemRemoved(position)
                    data.removeAt(position)
                    activity?.supportFragmentManager?.popBackStack()
                    val intent = Intent(thiscontxt, OnlineConnection::class.java)
                    intent.putExtra("code", codeForeJoining)
                    startActivity(intent)
                }
            })

//            MyDatabaseHelper_Invites.setUpdateUIInterfaceListener(object : MyDatabaseHelper_Invites.UpdateUIInterface{
//                override fun updateUI(position: Int) {
//                    handler.post {
//                        invitesAdapter.notifyItemInserted(position)
//                    }
//
//                }
//
//            })
        }
    }

    private fun addDataset(){
        data = DataSource_Invites_List.createDataSet()
        invitesAdapter.submitList(data)
    }

    override fun state(state: Boolean) {
        if(state){
            handler.post {
                invitesAdapter.notifyItemInserted(0)
            }
        }
    }


}