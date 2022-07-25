package com.neilkrishna_kabara.tic_tac_toe_online
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.neilkrishna_kabara.tic_tac_toe_online.model.Invites

private const val TAG = "DatabaseHelper_Invites"
class MyDatabaseHelper_Invites(context: Context): SQLiteOpenHelper(context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
)  {


    interface UpdateUIInterface{
        fun updateUI(position: Int)
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // _id, playerID, playerName, playerEmail, playerPhotoURL, playerToken
        val query = "CREATE TABLE IF NOT EXISTS $TABLE_NAME(${COLUMN_ID} INTEGER PRIMARY KEY NOT NULL," +
                " $COLUMN_PLAYER_ID TEXT NOT NULL, $COLUMN_PLAYER_NAME TEXT NOT NULL, $COLUMN_PLAYER_EMAIL TEXT NOT NULL," +
                " $COLUMN_PLAYER_PHOTOURL TEXT NOT NULL, $COLUMN_PLAYER_TOKEN TEXT NOT NULL, $COLUMN_PLAYER_CODE TEXT NOT NULL)"
//        val query = "ALTER TABLE $TABLE_NAME ADD COLUMN $COLUMN_PLAYER_TOKEN TEXT NOT NULL"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addRecord(
        playerID: String,
        playerName: String,
        playerEmail: String,
        playerPhotoURL: String,
        playerToken: String,
        playerCode: String
    ){
        val db: SQLiteDatabase = this.writableDatabase
        val cv = ContentValues().apply{
            put(COLUMN_PLAYER_ID, playerID)
            put(COLUMN_PLAYER_NAME, playerName)
            put(COLUMN_PLAYER_EMAIL, playerEmail)
            put(COLUMN_PLAYER_PHOTOURL, playerPhotoURL)
            put(COLUMN_PLAYER_TOKEN, playerToken)
            put(COLUMN_PLAYER_CODE, playerCode)
        }
        val result: Long = db.insert(TABLE_NAME, null, cv)
        if(result == "-1".toLong()){
            Log.d(TAG, "Addition of record in INVITES DATABASE Failed")
//            Toast.makeText(MainActivityContext, "Record Addition Failed", Toast.LENGTH_SHORT).show()
        }else{
            Log.d(TAG, "Addition of record in INVITES DATABASE Successful")
//            Toast.makeText(MainActivityContext, "Record Addition Success", Toast.LENGTH_SHORT).show()
            com.neilkrishna_kabara.tic_tac_toe_online.DataSource_Invites_List.list.add(0, Invites(playerID, playerName, playerEmail, playerPhotoURL, playerToken, playerCode))
            mListener.updateUI(0)
//            val handler: Handler()
//            invitesAdapter.post


        }
    }

    fun readAllData(query: String = "SELECT * FROM $TABLE_NAME", arg: String? = null): Cursor? {
//        val query = "SELECT * FROM $TABLE_NAME"
        val db: SQLiteDatabase = this.readableDatabase
        var cursor: Cursor? = null
        if (db != null){
            if(arg == null){
                cursor = db.rawQuery(query, null)
            }else {
                cursor = db.rawQuery(query, arrayOf(arg))
            }
        }
        return cursor
    }

    fun deleteRow(playerID: String){
        val db = this.readableDatabase
        val result = db.delete(TABLE_NAME, "$COLUMN_PLAYER_ID=?", arrayOf(playerID))
        if(result == -1){
            Log.d(TAG, "Deletion of record in INVITES DATABASE Failed")
//            Toast.makeText(MainActivityContext, "Record DELETION Failed", Toast.LENGTH_SHORT).show()
        }else{
            Log.d(TAG, "Deletion of record in INVITES DATABASE Successful")
//            Toast.makeText(MainActivityContext, "Record DELETION Success", Toast.LENGTH_SHORT).show()
        }
    }
    companion object{
        lateinit var mListener: UpdateUIInterface

        fun setUpdateUIInterfaceListener(listener: UpdateUIInterface){
            Log.d(TAG, "mListener has been set")
            mListener = listener
        }
        private const val DATABASE_NAME: String = "InvitedPlayers.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "InvitedPlayers"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_PLAYER_ID = "playerID"
        private const val COLUMN_PLAYER_NAME = "playerName"
        private const val COLUMN_PLAYER_EMAIL = "playerEmail"
        private const val COLUMN_PLAYER_PHOTOURL = "playerPhotoURL"
        private const val COLUMN_PLAYER_TOKEN = "playerToken"
        private const val COLUMN_PLAYER_CODE = "playerCode"
        private val context = this
    }
}