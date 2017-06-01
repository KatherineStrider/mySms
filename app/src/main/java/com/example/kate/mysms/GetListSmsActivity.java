package com.example.kate.mysms;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.kate.mysms.sqlite.DBSmsList;
import com.example.kate.mysms.sqlite.DBSmsListProvider;

/**
 * Created by Kate on 01.06.2017.
 */

public class GetListSmsActivity extends AppCompatActivity {

    ListView listSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_list_sms);

        initView();
    }

    private void initView(){

        listSms = (ListView) findViewById(R.id.getListSms);

        Uri queryUri = DBSmsListProvider.CONTENT_URI_ITEMS;

        Cursor cursor = this.getContentResolver().query(queryUri, null, null, null, null);
        String[] from = new String[]{DBSmsList.TableItems.C_ADDRESS, DBSmsList.TableItems.C_BODY};
        int[] to = new int[]{R.id.tvAddress, R.id.tvBody};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.view_list, cursor, from, to, 0);

        listSms.setAdapter(adapter);
    }
}
