package com.neilkrishna_kabara.tic_tac_toe_online.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface InvitedPlayerDao {
    @Query("SELECT * FROM Invited_Players")
    fun getAll(): Flow<List<InvitedPlayer>>

    @Query("SELECT * FROM Invited_Players WHERE _id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<InvitedPlayer>

    @Insert
    fun insertAll(vararg users: InvitedPlayer)

    @Delete
    fun delete(user: InvitedPlayer)
}