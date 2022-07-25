package com.neilkrishna_kabara.tic_tac_toe_online.repository

import com.neilkrishna_kabara.tic_tac_toe_online.room_database.InvitedPlayer
import com.neilkrishna_kabara.tic_tac_toe_online.room_database.InvitedPlayerDao
import com.neilkrishna_kabara.tic_tac_toe_online.room_database.PreviousPlayer
import com.neilkrishna_kabara.tic_tac_toe_online.room_database.PreviousPlayerDao
import kotlinx.coroutines.flow.Flow

class AppDatabaseRepository(private val invitedPlayerDao: InvitedPlayerDao, private val previousPlayerDao: PreviousPlayerDao) {

    val allInvitedPlayers: Flow<List<InvitedPlayer>> = invitedPlayerDao.getAll()
    val allPreviousPlayers: Flow<List<PreviousPlayer>> = previousPlayerDao.getAll()

    suspend fun insertInvitedPlayer(player: InvitedPlayer){
        invitedPlayerDao.insertAll(player)
    }

    suspend fun insertPreviousPlayer(player: PreviousPlayer){
        previousPlayerDao.insertAll(player)
    }
}