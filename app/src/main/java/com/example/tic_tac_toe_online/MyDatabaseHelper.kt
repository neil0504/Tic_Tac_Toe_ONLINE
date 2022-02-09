package com.example.tic_tac_toe_online

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
//    var DATABASE_NAME: String? = "PreviousPLAYERS.db"
//    val DATABASE_VERSION = 1
private val con = context
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE IF NOT EXISTS $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY NOT NULL," +
                " $COLUMN_PLAYER_ID TEXT NOT NULL, $COLUMN_PLAYER_NAME TEXT NOT NULL, $COLUMN_PLAYER_EMAIL TEXT NOT NULL," +
                " $COLUMN_PLAYER_PHOTOURL TEXT NOT NULL, $COLUMN_PLAYER_TOKEN TEXT NOT NULL)"
//        val query = "ALTER TABLE $TABLE_NAME ADD COLUMN $COLUMN_PLAYER_TOKEN TEXT NOT NULL"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addRecord(playerID: String, playerName: String, playerEmail: String?, playerPhotoURL: String?, playerToken: String){
        val db: SQLiteDatabase = this.writableDatabase
        val cv = ContentValues().apply{
            put(COLUMN_PLAYER_ID, playerID)
            put(COLUMN_PLAYER_NAME, playerName)
            put(COLUMN_PLAYER_EMAIL, playerEmail)
            put(COLUMN_PLAYER_PHOTOURL, playerPhotoURL)
            put(COLUMN_PLAYER_TOKEN, playerToken)
        }
        val result: Long = db.insert(TABLE_NAME, null, cv)
        if(result == "-1".toLong()){
            Toast.makeText(con, "Record Addition Failed", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(con, "Record Addition Success", Toast.LENGTH_SHORT).show()
        }
    }

    fun readAllData(query: String = "SELECT * FROM $TABLE_NAME", arg: String? = null): Cursor? {
//        val query = "SELECT * FROM $TABLE_NAME"
        val db: SQLiteDatabase = this.readableDatabase
        var cursor: Cursor? = null
        cursor = if(arg == null){
            db.rawQuery(query, null)
        }else {
            db.rawQuery(query, arrayOf(arg))
        }
        return cursor
    }

    fun deleteRow(playerID: String){
        val db = this.readableDatabase
        val result = db.delete(TABLE_NAME, "$COLUMN_PLAYER_ID=?", arrayOf(playerID))
        if(result == -1){
            Toast.makeText(con, "Record DELETION Failed", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(con, "Record DELETION Success", Toast.LENGTH_SHORT).show()
        }
    }
    companion object{
        private const val DATABASE_NAME: String = "PlayerHistory.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "PlayerHistory"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_PLAYER_ID = "playerID"
        private const val COLUMN_PLAYER_NAME = "playerName"
        private const val COLUMN_PLAYER_EMAIL = "playerEmail"
        private const val COLUMN_PLAYER_PHOTOURL = "playerPhotoURL"
        private const val COLUMN_PLAYER_TOKEN = "playerToken"
//        private val context = this
    }
}