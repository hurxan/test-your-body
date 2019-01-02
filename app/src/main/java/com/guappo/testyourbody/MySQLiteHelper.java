package com.guappo.testyourbody;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class MySQLiteHelper extends SQLiteOpenHelper {
    private static final String TABLE_RESULTS = "results";

    private static final String KEY_ID = "id";
    private static final String KEY_TYPE = "type";
    private static final String KEY_RESULT = "result";

    private static final String[] COLUMNS = {KEY_ID,KEY_TYPE,KEY_RESULT};

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ResultDB";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RESULT_TABLE = "CREATE TABLE results ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "type TEXT, " +
                "result INTEGER )";
        db.execSQL(CREATE_RESULT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS results");
        this.onCreate(db);
    }

    public void addResult(Result result){
        //for logging
        Log.d("addResult", result.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, result.getType()); // get title
        values.put(KEY_RESULT, result.getResult()); // get author

        // 3. insert
        db.insert(TABLE_RESULTS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Result getResult(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_RESULTS, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        Result result = new Result();
        result.setId(Integer.parseInt(cursor.getString(0)));
        result.setType(cursor.getString(1));
        result.setResult(Integer.parseInt(cursor.getString(2)));

        //log
        Log.d("getResult("+id+")", result.toString());

        // 5. return book
        return result;
    }

    public List<Result> getAllResults() {
        List<Result> results = new LinkedList<Result>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_RESULTS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Result result = null;
        if (cursor.moveToFirst()) {
            do {
                result = new Result();
                result.setId(Integer.parseInt(cursor.getString(0)));
                result.setType(cursor.getString(1));
                result.setResult(Integer.parseInt(cursor.getString(2)));

                // Add book to books
                results.add(result);
            } while (cursor.moveToNext());
        }

        Log.d("getAllResults()", results.toString());

        // return books
        return results;
    }

    public int updateResults(Result result) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("type", result.getType()); // get title
        values.put("result", result.getResult()); // get author

        // 3. updating row
        int i = db.update(TABLE_RESULTS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(result.getId()) }); //selection args

        // 4. close
        db.close();

        return i;

    }

    public void deleteDatabase(Context context){
        context.deleteDatabase(DATABASE_NAME);
    }


    public void deleteResult(Result result) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_RESULTS, //table name
                KEY_ID+" = ?",  // selections
                new String[] { String.valueOf(result.getId()) }); //selections args

        // 3. close
        db.close();

        //log
        Log.d("deleteResult", result.toString());

    }

}
