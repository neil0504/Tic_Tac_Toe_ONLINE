package com.neilkrishna_kabara.tic_tac_toe_online

import android.util.Log

private const val TAG = "SingletonHolder"
open class SingletonHolder<out T: Any, in A, U, V, W>(creator: (A, U, V, W) -> T) {
    private var creator: ((A, U, V, W) -> T)? = creator
    @Volatile private var instance: T? = null

    fun getInstance(arg1: A, arg2: U, arg3: V, arg4: W): T {
        Log.d(TAG, "getInstance: Starts")
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator!!(arg1, arg2, arg3, arg4)
                instance = created
                creator = null
                created
            }
        }
    }
}