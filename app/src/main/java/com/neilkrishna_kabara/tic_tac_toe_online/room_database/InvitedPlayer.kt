package com.neilkrishna_kabara.tic_tac_toe_online.room_database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Invited_Players")
class InvitedPlayer(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "_id")val uid: Long = 0,
    @ColumnInfo(name = "playerID") val playerId: String,
    @ColumnInfo(name = "playerName") val playerName: String,
    @ColumnInfo(name = "playerEmail") val playerEmail: String,
    @ColumnInfo(name = "playerPhotoURL") val playerPhotoURL: String,
    @ColumnInfo(name = "playerToken") val playerToken: String,
    @ColumnInfo(name = "playerCode") val playerCode: String,
)
