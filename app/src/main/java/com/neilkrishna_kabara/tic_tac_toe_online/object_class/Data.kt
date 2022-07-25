package com.neilkrishna_kabara.tic_tac_toe_online.object_class

import com.neilkrishna_kabara.tic_tac_toe_online.model.PlayerDetails
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

object Data {
    var isSignedInGoogle: Boolean? = false
    var isSignedInGuest: Boolean? = false
    var guestId = "Guest_1"
    var guestName = "Guest_1"
    lateinit var player: PlayerDetails
    var account: GoogleSignInAccount? = null
    var myToken: String? = null
//    var intentArray: BooleanArray? = null

}