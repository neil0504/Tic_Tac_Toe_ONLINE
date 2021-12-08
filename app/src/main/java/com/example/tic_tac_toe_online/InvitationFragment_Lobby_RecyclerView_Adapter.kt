package com.example.tic_tac_toe_online

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

private var checkPosition = -1
class InvitationFragment_Lobby_RecyclerView_Adapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<InvitationFragment_Lobby> = ArrayList()
    lateinit var mListener: OnItemClickListener

    public interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemListenerr(listerer: OnItemClickListener) {
        mListener = listerer
    }
//    fun check(checkPos: Int){
//
//    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RV1_LobbyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv1_items, parent, false),
            mListener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is RV1_LobbyViewHolder -> {
                holder.bind(items[position])
//                holder.itemView.setOnClickListener{OnRowSelect.onRowClick(position)}
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun submitList(lobbyList: List<InvitationFragment_Lobby>) {
        items = lobbyList as ArrayList<InvitationFragment_Lobby>
    }



    class RV1_LobbyViewHolder(
        itemView: View,
        private val listener: OnItemClickListener
    ): RecyclerView.ViewHolder(itemView){
        val profilePic = itemView.findViewById<ImageView>(R.id.lobby_player_profile)
        val displayName = itemView.findViewById<TextView>(R.id.lobby_displayName)
        val tickImage = itemView.findViewById<ImageView>(R.id.tickImage)
        init {
            itemView.setOnClickListener{
//                check(checkPosition)
//                if (checkPosition == -1){
//                    checkPosition = absoluteAdapterPosition
//                    tickImage.visibility = View.VISIBLE
//                }
//                else if (checkPosition != absoluteAdapterPosition){
//                    items[checkPosition].
//                    invitationfragment_LobbyRecyclerviewAdapter.notifyDataSetChanged()
//                }
                listener.onItemClick(absoluteAdapterPosition)

            }
        }

//        private fun notifyDataSetChange(checkPosition: Int) {
//
//        }

        fun bind(invitationfragmentLobby: InvitationFragment_Lobby)
        {
            Picasso.get().load(invitationfragmentLobby.photo_URL).placeholder(R.mipmap.ic_launcher).into(profilePic)
            displayName.text = invitationfragmentLobby.name
            tickImage.visibility = View.GONE
//            if (checkPosition == -1){
//                tickImage.visibility = View.GONE
//            }else{
//                if (checkPosition == absoluteAdapterPosition){
//                    tickImage.visibility = View.VISIBLE
//                }
//                else{
//                    tickImage.visibility = View.GONE
//                }
//            }
//            tickImage.visibility = View.INVISIBLE
//            itemView.setOnClickListener(this)

        }




    }



}