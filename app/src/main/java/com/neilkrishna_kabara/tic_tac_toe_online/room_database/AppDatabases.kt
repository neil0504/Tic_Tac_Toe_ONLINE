package com.neilkrishna_kabara.tic_tac_toe_online.room_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GuestUser::class, PreviousPlayer::class, InvitedPlayer::class], version = 1)
abstract class AppDatabases: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun previousPlayerDoa(): PreviousPlayerDao
    abstract fun invitedPlayersDao(): InvitedPlayerDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabases? = null

        fun getDatabase(context: Context): AppDatabases {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabases::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}