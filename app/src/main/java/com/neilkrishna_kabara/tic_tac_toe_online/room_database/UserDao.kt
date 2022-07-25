package com.neilkrishna_kabara.tic_tac_toe_online.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM guest_data")
    fun getAll(): List<GuestUser>

    @Query("SELECT * FROM guest_data WHERE _id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<GuestUser>

    @Insert
    fun insertAll(vararg users: GuestUser)

    @Delete
    fun delete(user: GuestUser)

}
