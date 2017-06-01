package com.example.kate.mysms;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Kate on 31.05.2017.
 */

public class GetSmsActivity extends AppCompatActivity {

    TextView textSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_sms);

        initView();
    }

    private void initView(){

        textSms = (TextView) findViewById(R.id.textViewSms);
        String s = getFromSD();
        if(s==null){return;}
        textSms.setText(s);
    }

    private String getFromSD(){

        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "SD-карта не доступна", Toast.LENGTH_LONG).show();
            return null;
        }
        File sdPath = Environment.getExternalStorageDirectory();
        sdPath = new File(sdPath.getAbsolutePath() + "/" + "mydir");
        File sdFile = new File(sdPath, "sms.txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader
                    (sdFile));
            StringBuffer s = new StringBuffer();
            String line = br.readLine();
            while (line != null) {
                s.append(line + "\n");
                line = br.readLine();
            }
            br.close();
            return s.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
