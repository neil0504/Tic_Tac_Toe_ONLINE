package com.neilkrishna_kabara.tic_tac_toe_online

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neilkrishna_kabara.tic_tac_toe_online.databinding.Rv1ItemsBinding
import com.neilkrishna_kabara.tic_tac_toe_online.model.InvitationFragmentLobby
import com.squareup.picasso.Picasso

private var checkPosition = -1
class InvitationFragment_Lobby_RecyclerView_Adapter(private val mListener: OnItemClickListenerInterface) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<InvitationFragmentLobby> = ArrayList()

    interface OnItemClickListenerInterface{
        fun onItemClick(position: Int)
    }
//    fun check(checkPos: Int){
//
//    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RV1_LobbyViewHolder(
            Rv1ItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false),
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
    fun submitList(lobbyList: List<InvitationFragmentLobby>) {
        items = lobbyList as ArrayList<InvitationFragmentLobby>
    }



    class RV1_LobbyViewHolder(
        binding: Rv1ItemsBinding,
        private val listener: OnItemClickListenerInterface
    ): RecyclerView.ViewHolder(binding.root){
        private val profilePic = binding.lobbyPlayerProfile
        private val displayName = binding.lobbyDisplayName
        private val tickImage = binding.tickImage
        init {
            binding.root.setOnClickListener{
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

        fun bind(invitationFragmentLobby: InvitationFragmentLobby)
        {
            Picasso.get().load(invitationFragmentLobby.photo_URL).placeholder(R.mipmap.ic_launcher).into(profilePic)
            displayName.text = invitationFragmentLobby.name
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