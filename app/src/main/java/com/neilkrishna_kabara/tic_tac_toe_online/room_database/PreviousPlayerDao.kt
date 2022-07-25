package com.neilkrishna_kabara.tic_tac_toe_online.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PreviousPlayerDao{
    @Query("SELECT * FROM Player_History")
    fun getAll(): Flow<List<PreviousPlayer>>

    @Query("SELECT * FROM Player_History WHERE _id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<PreviousPlayer>

    @Insert
    fun insertAll(vararg users: PreviousPlayer)

    @Delete
    fun delete(user: PreviousPlayer)
}