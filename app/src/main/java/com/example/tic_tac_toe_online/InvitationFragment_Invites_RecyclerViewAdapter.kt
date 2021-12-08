package com.example.tic_tac_toe_online

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class InvitationFragment_Invites_RecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<InvitationFragment_Invites> = ArrayList()
    lateinit var deleteListener: OnDeleteClickListnerer
    lateinit var inviteListener: OnInviteClickListnerer
    interface OnDeleteClickListnerer{
        fun onDeleteClick(position: Int)
    }

    fun setOnDeleteClickListnerer(listerer: OnDeleteClickListnerer) {
        deleteListener = listerer
    }

    interface OnInviteClickListnerer{
        fun onInviteClick(position: Int)
    }

    fun setOnInviteClickListnerer(listerer: OnInviteClickListnerer) {
        inviteListener = listerer
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RV2_InvitesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv2_items, parent, false),
            deleteListener,
            inviteListener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is RV2_InvitesViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun submitList(inviteList: List<InvitationFragment_Invites>) {
        items = inviteList as ArrayList<InvitationFragment_Invites>
    }
    class RV2_InvitesViewHolder(itemView: View, private val listener: OnDeleteClickListnerer, private val inviteListener: OnInviteClickListnerer): RecyclerView.ViewHolder(itemView){
        val profilePic = itemView.findViewById<ImageView>(R.id.invite_player_profile)
        val displayName = itemView.findViewById<TextView>(R.id.invite_displayName)
        val inviteButton = itemView.findViewById<Button>(R.id.inviteButton)
        val delete = itemView.findViewById<ImageButton>(R.id.deleteImageButton)

        fun bind(invitationfragmentInvites: InvitationFragment_Invites)
        {
            Picasso.get().load(invitationfragmentInvites.photo_URL).placeholder(R.mipmap.ic_launcher).into(profilePic)
            displayName.text = invitationfragmentInvites.name
            inviteButton.setOnClickListener {
                inviteListener.onInviteClick(absoluteAdapterPosition)
            }
            delete.setOnClickListener {
                listener.onDeleteClick(absoluteAdapterPosition)
            }

        }
    }
}