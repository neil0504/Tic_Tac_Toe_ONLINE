package com.neilkrishna_kabara.tic_tac_toe_online

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class MyDatabaseHelperGuest(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private val TAG = "MyDatabaseHelperGuest"

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE IF NOT EXISTS ${TABLE_NAME}(" +
                "$COLUMN_ID INTEGER PRIMARY KEY NOT NULL," +
                " $COLUMN_PLAYER_ID TEXT NOT NULL," +
                " $COLUMN_PLAYER_NAME TEXT NOT NULL)"
//        val query = "ALTER TABLE $TABLE_NAME ADD COLUMN $COLUMN_PLAYER_TOKEN TEXT NOT NULL"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addRecord(playerID: String, playerName: String){
        val db: SQLiteDatabase = this.writableDatabase
        val cv = ContentValues().apply{
            put(COLUMN_PLAYER_ID, playerID)
            put(COLUMN_PLAYER_NAME, playerName)
        }
        val result: Long = db.insert(TABLE_NAME, null, cv)
        if(result == "-1".toLong()){
            Log.d(TAG, "MyDatabaseHelperGuest: Addition Unsuccessful")
        }else{
            Log.d(TAG, "MyDatabaseHelperGuest: Addition Successful")
        }
    }

    fun readAllData(query: String = "SELECT * FROM $TABLE_NAME", arg: String? = null): Cursor? {
//        val query = "SELECT * FROM $TABLE_NAME"

        val db: SQLiteDatabase = this.readableDatabase

        return db.rawQuery(query, null)
    }

    fun deleteRow(playerID: String){
        val db = this.readableDatabase
        val result = db.delete(TABLE_NAME, "$COLUMN_PLAYER_ID=?", arrayOf(playerID))
        if(result == -1){
//            Toast.makeText(cc, "Record DELETION Failed", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "MyDatabaseHelperGuest: Deletion Unsuccessful")
        }else{
//            Toast.makeText(cc, "Record DELETION Success", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "MyDatabaseHelperGuest: Deletion Successful")
        }
    }

    companion object{
        private const val DATABASE_NAME: String = "Guest_data.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "Guest_data"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_PLAYER_ID = "playerID"
        private const val COLUMN_PLAYER_NAME = "playerName"
//        private const val COLUMN_PLAYER_TOKEN = "playerToken"
        private val context = this
    }
}