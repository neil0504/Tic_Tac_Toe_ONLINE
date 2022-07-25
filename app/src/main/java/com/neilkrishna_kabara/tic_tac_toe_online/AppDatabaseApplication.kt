package com.neilkrishna_kabara.tic_tac_toe_online

import android.app.Application
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.neilkrishna_kabara.tic_tac_toe_online.repository.AppDatabaseRepository
import com.neilkrishna_kabara.tic_tac_toe_online.room_database.AppDatabases

var googleSignedInAccount: GoogleSignInAccount? = null
class AppDatabaseApplication: Application() {
    val database by lazy { AppDatabases.getDatabase(this) }

    val repository by lazy {AppDatabaseRepository(invitedPlayerDao = database.invitedPlayersDao(), previousPlayerDao = database.previousPlayerDoa())}

    override fun onCreate() {
        super.onCreate()
        googleSignedInAccount = GoogleSignIn.getLastSignedInAccount(this.applicationContext)


    }

}