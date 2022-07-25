package com.neilkrishna_kabara.tic_tac_toe_online

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neilkrishna_kabara.tic_tac_toe_online.databinding.Rv2ItemsBinding
import com.neilkrishna_kabara.tic_tac_toe_online.model.InvitationFragmentInvites
import com.squareup.picasso.Picasso

class InvitationFragmentInvitesRecyclerViewAdapter(private val listener: InvitationFragmentInvitesRecyclerViewAdapterInterface): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<InvitationFragmentInvites> = ArrayList()

    interface InvitationFragmentInvitesRecyclerViewAdapterInterface{
        fun onDeleteClick(position: Int)
        fun onInviteClick(position: Int)

    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return RV2_InvitesViewHolder(
//            LayoutInflater.from(parent.context).inflate(R.layout.rv2_items, parent, false),
//            listener,
//        )
        return RV2_InvitesViewHolder(Rv2ItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener)
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
    fun submitList(inviteList: List<InvitationFragmentInvites>) {
        items = inviteList as ArrayList<InvitationFragmentInvites>
    }
    class RV2_InvitesViewHolder(binding: Rv2ItemsBinding, private val listener: InvitationFragmentInvitesRecyclerViewAdapterInterface): RecyclerView.ViewHolder(binding.root){
        private val profilePic = binding.invitePlayerProfile
        private val displayName = binding.inviteDisplayName
        private val inviteButton = binding.inviteButton
        private val delete = binding.deleteImageButton

        fun bind(invitationFragmentInvites: InvitationFragmentInvites)
        {
            Picasso.get().load(invitationFragmentInvites.photo_URL).placeholder(R.mipmap.ic_launcher).into(profilePic)
            displayName.text = invitationFragmentInvites.name
            inviteButton.setOnClickListener {
                listener.onInviteClick(absoluteAdapterPosition)
            }
            delete.setOnClickListener {
                listener.onDeleteClick(absoluteAdapterPosition)
            }

        }
    }
}