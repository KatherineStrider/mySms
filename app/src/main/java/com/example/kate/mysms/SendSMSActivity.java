package com.example.kate.mysms;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Kate on 31.05.2017.
 */

public class SendSMSActivity extends AppCompatActivity {

    EditText txtSendAddress;
    EditText txtSendBody;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        initView();
    }

    private void initView(){

        txtSendAddress = (EditText) findViewById(R.id.textSmsAddress);
        txtSendBody = (EditText) findViewById(R.id.textSmsBody);
        btnSend = (Button) findViewById(R.id.buttonSend);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isChecked(txtSendAddress.getText().toString())){
                    Toast.makeText(SendSMSActivity.this, "Номер введен неверно", Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(txtSendAddress.getText().toString(), null,
                        txtSendBody.getText().toString(), null, null);

                Toast.makeText(SendSMSActivity.this, "Сообщение отправлено", Toast.LENGTH_LONG)
                        .show();
                clearText();
            }
        };

        btnSend.setOnClickListener(onClickListener);
    }

    private boolean isChecked(String number){

        char[] arr = number.toCharArray();

        if(arr[0] == '+' && arr[1] == '7' && arr.length == 12 || arr[0] == '8' && arr.length == 11){
            return true;
        }
        return false;
    }

    private void clearText(){

        txtSendAddress.setText("");
        txtSendBody.setText("");
    }
}
