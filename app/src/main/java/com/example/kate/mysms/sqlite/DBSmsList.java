package com.example.kate.mysms.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.kate.mysms.R;

/**
 * Created by Kate on 13.05.2017.
 */

public class DBSmsList extends DBSQLite {

    private static final String SQL_WHERE_BY_ID = BaseColumns._ID + "=?";
    private static final String DB_NAME = "DBSmsList.db";
    private static final int DB_VERSION = 2;

    public DBSmsList(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        DBSQLite.execSQL(db, TableItems.SQL_CREATE);

        String[] items = getContext().getResources().getStringArray(
                R.array.list_test);
        ContentValues contentValues = new ContentValues(items.length);

        for (int i = 0; i < items.length; i++) {

            String[] item = items[i].split("-");

            contentValues.put(TableItems.C_ADDRESS, item[0]);
            contentValues.put(TableItems.C_BODY, item[1]);

            db.insert(TableItems.T_NAME, null, contentValues);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        DBSQLite.dropTable(db, TableItems.T_NAME);

        this.onCreate(db);
    }

    public long addItem(String address, String body) {

        ContentValues v = new ContentValues();

        v.put(TableItems.C_ADDRESS, address);
        v.put(TableItems.C_BODY, body);

        return this.getWritableDatabase().insert(TableItems.T_NAME, null, v);

    }

    public long addItem(ContentValues values) {
        return this.getWritableDatabase().insert(TableItems.T_NAME, null, values);
    }

    public boolean updateItem(String address, String body, long id) {

        ContentValues v = new ContentValues();

        v.put(TableItems.C_ADDRESS, address);
        v.put(TableItems.C_BODY, body);

        return 1 == this.getWritableDatabase().update(TableItems.T_NAME, v,
                SQL_WHERE_BY_ID, new String[] {String.valueOf(id)});
    }

    public boolean updateItem(ContentValues values, long id) {

        return 1 == this.getWritableDatabase().update(TableItems.T_NAME, values,
                SQL_WHERE_BY_ID, new String[] {String.valueOf(id)});
    }

    public boolean deleteItem(long id) {
        return 1 == this.getWritableDatabase().delete(
                TableItems.T_NAME, SQL_WHERE_BY_ID,
                new String[] {String.valueOf(id)});
    }

    public static class TableItems implements BaseColumns {

        public static final String T_NAME = "tItems";
        public static final String C_ADDRESS = "address";
        public static final String C_BODY = "body";

        public static final String SQL_CREATE = "CREATE TABLE " + T_NAME +
                " (" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                C_ADDRESS + " TEXT, " +
                C_BODY + " TEXT"
                + ")";
    }
}
