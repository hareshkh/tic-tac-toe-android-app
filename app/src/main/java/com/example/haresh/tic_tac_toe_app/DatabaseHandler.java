package com.example.haresh.tic_tac_toe_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Haresh on 18-01-2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABSE_VERSION=1;
    private static final String DATABASE_NAME="scoreboardManager",
                                TABLE_SCORES="scoreboard",
                                KEY_ID="id",
                                KEY_NAME="name",
                                KEY_SCORE="score";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABSE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_SCORES + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_SCORE + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISITS " + TABLE_SCORES);
        onCreate(db);
    }
    public void addScore(scoreboard sb) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, sb.get_name()); // Contact Name
        values.put(KEY_SCORE, sb.get_score()); // Contact Phone Number

        // Inserting Row
        db.insert(TABLE_SCORES, null, values);
        db.close(); // Closing database connection
    }

    public String checkUser(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SCORES, new String[]{KEY_ID,
                        KEY_NAME, KEY_SCORE}, KEY_NAME + " = ?",
                new String[]{String.valueOf(name)}, null, null, null, null);
        if(cursor!=null){
            return "FOUND";
        }
        return "NOT FOUND";
    }
    public scoreboard getPlayer(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SCORES, new String[] { KEY_ID,
                        KEY_NAME, KEY_SCORE }, KEY_NAME + " = ?",
                new String[] { String.valueOf(name) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        scoreboard sb = new scoreboard(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), Integer.parseInt(cursor.getString(2)));
        // return contact
        return sb;
    }

    public List<scoreboard> getAllContacts() {
        List<scoreboard> scoreList = new ArrayList<scoreboard>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SCORES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                scoreboard sb = new scoreboard();
                sb.set_id(Integer.parseInt(cursor.getString(0)));
                sb.set_name(cursor.getString(1));
                sb.set_score(Integer.parseInt(cursor.getString(2)));
                // Adding contact to list
                scoreList.add(sb);
            } while (cursor.moveToNext());
        }

        // return contact list
        return scoreList;
    }
    public int updatePlayer(scoreboard sb) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, sb.get_name());
        values.put(KEY_SCORE, sb.get_score());

        // updating row
        return db.update(TABLE_SCORES, values, KEY_NAME + " = ?",
                new String[] { String.valueOf(sb.get_name()) });
    }
    public int getPlayerCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SCORES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}
