package com.example.kate.mysms;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.kate.mysms.sqlite.DBSmsList;
import com.example.kate.mysms.sqlite.DBSmsListProvider;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fabNewSms;
    ListView listSms;
    SimpleCursorAdapter adapter;

    private static final Uri SMS_INBOX = Uri.parse("content://sms/inbox");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {

        fabNewSms = (FloatingActionButton) findViewById(R.id.fabSms);
        listSms = (ListView) findViewById(R.id.listSms);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SendSMSActivity.class);
                startActivity(intent);
            }
        };

        Cursor cursor = this.getContentResolver().query(SMS_INBOX, null, null, null, null);
        String[] from = new String[]{"address", "body"};
        int[] to = new int[]{R.id.tvAddress, R.id.tvBody};

        adapter = new SimpleCursorAdapter(this, R.layout.view_list, cursor, from, to, 0);

        listSms.setAdapter(adapter);
        fabNewSms.setOnClickListener(onClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;
        switch (item.getItemId()) {
            case R.id.saveToSd:
                saveToSD();
                break;
            case R.id.getFromSd:
                intent = new Intent(MainActivity.this, GetSmsActivity.class);
                startActivity(intent);
                break;
            case R.id.saveToBd:
                saveToBD();
                break;
            case R.id.getFromBd:
                intent = new Intent(MainActivity.this, GetListSmsActivity.class);
                startActivity(intent);
                break;
            default:

                break;
        }
        return false;
    }

    private void saveToSD() {

        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "SD-карта не доступна", Toast.LENGTH_LONG).show();
            return;
        }
        File sdPath = Environment.getExternalStorageDirectory();
        sdPath = new File(sdPath.getAbsolutePath() + "/" + "mydir");
        sdPath.mkdirs();
        File sdFile = new File(sdPath, "sms.txt");

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));

            Cursor c = adapter.getCursor();
            c.moveToFirst();
            bw.write(c.getString(c.getColumnIndex("body")) + "\n");
            bw.write(c.getString(c.getColumnIndex("address")) + "\n");
            while (c.moveToNext()) {
                bw.write(c.getString(c.getColumnIndex("address")) + "\n");
                bw.write(c.getString(c.getColumnIndex("body")) + "\n");
            }
            Toast.makeText(this, "Сохранение завершено", Toast.LENGTH_LONG).show();
//            c.close();
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToBD() {

        Cursor c = adapter.getCursor();
        c.moveToFirst();

        ContentValues values = new ContentValues();
        values.put(DBSmsList.TableItems.C_ADDRESS, (c.getString(c.getColumnIndex("address"))));
        values.put(DBSmsList.TableItems.C_BODY, (c.getString(c.getColumnIndex("body"))));

        this.getContentResolver().insert(DBSmsListProvider.CONTENT_URI_ITEMS, values);

        while (c.moveToNext()) {
            values.put(DBSmsList.TableItems.C_ADDRESS, (c.getString(c.getColumnIndex("address"))));
            values.put(DBSmsList.TableItems.C_BODY, (c.getString(c.getColumnIndex("body"))));

            this.getContentResolver().insert(DBSmsListProvider.CONTENT_URI_ITEMS, values);
        }
        Toast.makeText(this, "Сохранение завершено", Toast.LENGTH_LONG).show();
    }
}
