package com.example.kate.mysms;

import android.app.Application;
import android.util.Log;

import com.example.kate.mysms.sqlite.DBSmsList;

/**
 * Created by Kate on 12.05.2017.
 */

public class App extends Application {

    private static DBSmsList dbSmsList = null;
    public static final String LOG_TAG = App.class.getSimpleName();

    @Override
    public void onCreate() {

        super.onCreate();

        dbSmsList = new DBSmsList(getApplicationContext());
        dbSmsList.getWritableDatabase();
    }

    public static DBSmsList getDB() {
        return dbSmsList;
    }

    public static void Log(String log) {
        Log.d(LOG_TAG, log);
    }
}
