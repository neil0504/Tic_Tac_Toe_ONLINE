 package com.neilkrishna_kabara.tic_tac_toe_online

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neilkrishna_kabara.tic_tac_toe_online.databinding.FragmentInvitationBinding
//import com.neilkrishna_kabara.tic_tac_toe_online
import com.neilkrishna_kabara.tic_tac_toe_online.model.InvitationFragmentInvites
import com.neilkrishna_kabara.tic_tac_toe_online.model.InvitationFragmentLobby
import com.neilkrishna_kabara.tic_tac_toe_online.object_class.Data
import com.google.firebase.database.FirebaseDatabase



lateinit var joinerID: String
lateinit var joinerName: String
var joinerEmail: String? = null
var joinerPhotoURL: String? = null
lateinit var joinerToken: String

private const val TAG = "InvitationFragment"
//class InvitationFragment(sp: SharedPreferences?): Fragment(R.layout.fragment_invitation){
class InvitationFragment(private val cur: Cursor?): Fragment(R.layout.fragment_invitation),
    InvitationFragmentInvitesRecyclerViewAdapter.InvitationFragmentInvitesRecyclerViewAdapterInterface,
    InvitationFragment_Lobby_RecyclerView_Adapter.OnItemClickListenerInterface,
DataSource_RV1_InvitationFragment.NotifyItemInsertedInterface{

    // Binding
    private lateinit var binding: FragmentInvitationBinding

    // Views
    private lateinit var recyclerView2: RecyclerView
    private lateinit var recyclerView1: RecyclerView
    private lateinit var playBtn: Button

    // Data
    private lateinit var thiscontext: Context
    private var idd: String? = null
    private var name: String? = null
    private var email: String? = null
    private var photoURL: String? = null
    private var token: String? = null
    private lateinit var dataInvites: ArrayList<InvitationFragmentInvites>
    lateinit var data_Lobby: ArrayList<InvitationFragmentLobby>
    private var itemSelected = false



    // Adapters
    private lateinit var invitationFragmentInvitesAdapter: InvitationFragmentInvitesRecyclerViewAdapter
    private lateinit var invitationFragmentLobbyRecyclerviewAdapter: InvitationFragment_Lobby_RecyclerView_Adapter

//    private var cur: Cursor? = null
//    lateinit var acceptedMethod: AcceptedMethod
//    lateinit var obj: OnlineConnectionFragment



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentInvitationBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playBtn = binding.playButton
        recyclerView2 = binding.rv2Invites
        recyclerView1 = binding.rv1Lobby
        thiscontext = view.context
        Toast.makeText(thiscontext, "Fragment Created", Toast.LENGTH_SHORT).show()
        initRecyclerView1()
        addDataset1()
        initRecyclerView2()
        addDataset2()

        playBtn.setOnClickListener {
            if(itemSelected){
                val set = ArrayList<String?>()
                if (Data.isSignedInGoogle == true)
                {
                    if (Data.account != null){
                        set.add(Data.account!!.id)
                        set.add(Data.account!!.displayName!!)
                        set.add(Data.account!!.email!!)
                        set.add(Data.account!!.photoUrl!!.toString())
                        set.add(Data.myToken!!)
                    }
                }else if (Data.isSignedInGuest == true){
                    set.add(Data.guestId)
                    set.add(Data.guestName)
                    set.add("null")
                    set.add("null")
                    set.add(Data.myToken)
                }

                FirebaseDatabase.getInstance().reference.child("Creator_cred").child(code!!).child(idd!!).setValue(set)
                FirebaseDatabase.getInstance().reference.child("Play with").child(code!!).child(idd!!).setValue(idd!!)
                joinerID = idd!!
                joinerName = name!!
                joinerEmail = email
                joinerPhotoURL = photoURL.toString()
                joinerToken = token!!
                Log.d(TAG, "Value of joinerID = $joinerID, Value of joinerName = $joinerName")
//                obj.accepted()
                acc()
            }else{
                Toast.makeText(thiscontext, "Select a player to play with", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun initRecyclerView1() {
        recyclerView1.apply {
            layoutManager = LinearLayoutManager(thiscontext)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            invitationFragmentLobbyRecyclerviewAdapter = InvitationFragment_Lobby_RecyclerView_Adapter(this@InvitationFragment)
            adapter = invitationFragmentLobbyRecyclerviewAdapter
        }
    }
    private fun initRecyclerView2(){
        recyclerView2.apply {
            layoutManager = LinearLayoutManager(thiscontext)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            invitationFragmentInvitesAdapter = InvitationFragmentInvitesRecyclerViewAdapter(this@InvitationFragment)
            adapter = invitationFragmentInvitesAdapter
        }
    }
    private fun addDataset2(){
        dataInvites = DataSource_RV2_InvitationFragment.createDataSet(cur)
        invitationFragmentInvitesAdapter.submitList(dataInvites)
    }
    private fun addDataset1(){
        DataSource_RV1_InvitationFragment.setNotifyItemInsertedInterfaceListener(this)
        data_Lobby = DataSource_RV1_InvitationFragment.createDataSet()
        invitationFragmentLobbyRecyclerviewAdapter.submitList(data_Lobby)
    }
    private fun acc() {
        startActivity(Intent(thiscontext, OnlinePlay::class.java))
    }

    override fun onDeleteClick(position: Int) {
        val obj = dataInvites[position]
        val playerID = obj.id
        Log.d(TAG, "Delete Button Clicked")
        val db = MyDatabaseHelper(thiscontext)
        db.deleteRow(playerID)
        dataInvites.removeAt(position)
        invitationFragmentInvitesAdapter.notifyItemRemoved(position)
    }

    override fun onInviteClick(position: Int) {
        Toast.makeText(thiscontext, "Invite Button Clicked", Toast.LENGTH_SHORT).show()

        val obj = dataInvites[position]
        val token = obj.userToken

        Log.d("TATA", "Invitee Token = $token")
        // TODO: Do account not null check
        val sendNotification = FcmNotificationsSender(token, "Invitation", "${Data.account?.displayName} invites you to 2 Player Tic-Tac-Toe", thiscontext, this@InvitationFragment.requireActivity())
        sendNotification.SendNotifications()
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(thiscontext, "Row clicked", Toast.LENGTH_SHORT).show()
        itemSelected = true
        val obj = data_Lobby[position]
        idd = obj.id
        name = obj.name
        email = obj.email
        photoURL = obj.photo_URL
        token = obj.token
    }

    override fun notifyItemInserted(position: Int) {
        invitationFragmentLobbyRecyclerviewAdapter.notifyItemInserted(position)
    }

    override fun addItem(position: Int, obj: InvitationFragmentLobby) {
        data_Lobby.add(position, obj)
    }


}