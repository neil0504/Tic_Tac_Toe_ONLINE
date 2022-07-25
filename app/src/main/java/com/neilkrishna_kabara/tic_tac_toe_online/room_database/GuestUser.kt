package com.neilkrishna_kabara.tic_tac_toe_online.room_database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Guest_data")
class GuestUser(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "_id")val uid: Long = 0,
    @ColumnInfo(name = "guestId") val guestID: String,
    @ColumnInfo(name = "guestName") val guestName: String
)
