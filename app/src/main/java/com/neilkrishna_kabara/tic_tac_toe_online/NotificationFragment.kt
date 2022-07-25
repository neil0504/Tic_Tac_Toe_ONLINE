package com.neilkrishna_kabara.tic_tac_toe_online

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neilkrishna_kabara.tic_tac_toe_online.databinding.NotificationFragmentBinding
import com.neilkrishna_kabara.tic_tac_toe_online.model.Invites
import com.neilkrishna_kabara.tic_tac_toe_online.object_class.Data

private const val TAG = "NotificationFragment"
class NotificationFragment(private val ctx: Context, private val listener: NotificationFragmentInterface) : Fragment(), InvitesRecyclerViewAdapter.OnDeleteClickListener, InvitesRecyclerViewAdapter.OnPlayClickListener, FragmentStateCheck{

    interface NotificationFragmentInterface{
        fun setCounterToZero()
    }
    private lateinit var binding: NotificationFragmentBinding

    //Views
    private lateinit var cardView: CardView
    private lateinit var defaultText: TextView
    private lateinit var recyclerView: RecyclerView

    // RecyclerViewAdapter
    private lateinit var invitesAdapter: InvitesRecyclerViewAdapter

    // Data
    private lateinit var data: ArrayList<Invites>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NotificationFragmentBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listener.setCounterToZero()
        cardView = binding.cardView
        defaultText = binding.defaultText

        if (Data.isSignedInGoogle == true){
            cardView.visibility = View.VISIBLE
            defaultText.visibility = View.GONE
            recyclerView = binding.recyclerView
            initRecyclerView()
            addDataset()
        }else{
            cardView.visibility = View.GONE
            defaultText.visibility = View.VISIBLE
        }

    }

    private fun addDataset() {
        data = com.neilkrishna_kabara.tic_tac_toe_online.DataSource_Invites_List.createDataSet(ctx)
        invitesAdapter.submitList(data)
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            invitesAdapter = InvitesRecyclerViewAdapter(this@NotificationFragment, this@NotificationFragment)
            adapter = invitesAdapter
        }
    }

    override fun onDeleteClick(position: Int) {
        val value = data[position]
        val playerID = value.joinerID
        val db = MyDatabaseHelper_Invites(requireContext())
        db.deleteRow(playerID)
        data.removeAt(position)
        invitesAdapter.notifyItemRemoved(position)
    }

    override fun onPlayClick(position: Int) {
        val value = data[position]
        val playerID = value.joinerID
        val codeForeJoining = value.playerCode
        val db = MyDatabaseHelper_Invites(requireContext())
        db.deleteRow(playerID)
        invitesAdapter.notifyItemRemoved(position)
        data.removeAt(position)
        activity?.supportFragmentManager?.popBackStack()
        val intent = Intent(requireContext(), OnlineConnection::class.java)
        intent.putExtra("code", codeForeJoining)
        startActivity(intent)
    }
    override fun state(state: Boolean) {
        if(state){
            Handler(Looper.getMainLooper()).post {
                invitesAdapter.notifyItemInserted(0)
            }
        }
    }

}