package com.example.greenpolepowersolutions;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SONIC extends AppCompatActivity {
    private EditText display;
    private EditText number, input;
    TextView Tv;
    private Button SEND;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonic);
        display = findViewById(R.id.input);
        number = findViewById(R.id.number);
        input = findViewById(R.id.input);
        SEND = findViewById(R.id.SEND);
        Tv = findViewById(R.id.Tv);
        requestPermissions();

        // Get intent object sent from the SMSBroadcastReceiver
        Intent sms_intent = getIntent();
        Bundle b = sms_intent.getExtras();
        TextView tv = (TextView) findViewById(R.id.Tv);
        if (b != null) {
            // Display SMS in the TextView
            tv.setText(b.getString("sms_str"));
        }

        SEND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                        sendSMS();
                    } else {
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
                    }
                }
            }
        });

        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(i, 101);
            }
        });

        Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = Tv.getText().toString();
                Intent tvsharingIntent = new Intent(Intent.ACTION_SEND);
                tvsharingIntent.setType("text/plain");
                tvsharingIntent.putExtra(Intent.EXTRA_SUBJECT,"WRITE YOUR SUBJECT HERE");
                tvsharingIntent.putExtra(Intent.EXTRA_TEXT,text);
                startActivity(Intent.createChooser(tvsharingIntent,"SHARE TEXT VIA"));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == MainActivity.RESULT_OK) {
            Uri uri = data.getData();
            String cols[] = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
            Cursor rs = getContentResolver().query(uri, cols, null, null, null);
            if (rs.moveToFirst()) {
                number.setText(rs.getString(0));
            }
        }

    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(SONIC.this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SONIC.this, new String[]{
                    Manifest.permission.RECEIVE_SMS
            }, 100);
        }

    }

    private void sendSMS() {
        String phoneNO = number.getText().toString().trim();
        String SMS = input.getText().toString().trim();

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNO, null, SMS, null, null);
            Toast.makeText(this, "Message is sent", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }


        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getString(R.string.display).equals(display.getText().toString())) {
                    display.setText("");
                }
            }
        });
    }

    private void updateText(String strToAdd) {
        String oldStr = display.getText().toString();
        int cursorPos = display.getSelectionStart();
        String leftStr = oldStr.substring(0, cursorPos);
        String rightStr = oldStr.substring(cursorPos);
        display.setText(String.format("%s%s%s", leftStr, strToAdd, rightStr));
    }

    public void GP(View view) {
        updateText("GET POSTURL");

    }

    public void GPF(View view) {
        updateText("GET PUSH-FREQUENCY");

    }

    public void GFS(View view) {
        updateText("GET FUEL-SENSOR");

    }

    public void GCU(View view) {
        updateText("GET GCU");

    }

    public void GHN(View view) {
        updateText("GET HOSTNAME");

    }

    public void GSID(View view) {
        updateText("GET SITE-ID");

    }

    public void GFR(View view) {
        updateText("GET FILLING-RATE");

    }

    public void GPR(View view) {
        updateText("GET PILFERAGE-RATE");

    }

    public void GFLT(View view) {
        updateText("GET FUEL-LOW-THERSHOLD");

    }

    public void GFFT(View view) {
        updateText("GET FUEL-FILL-THRESHOLD");

    }

    public void GRBR(View view) {
        updateText("GET RS485-BAUD-RATE");
    }

    public void GRSID(View view) {
        updateText("GET RS485-SLAVE-ID");
    }

    public void GVS(View view) {
        updateText("GET VIBRATION-SENSITIVITY");
    }

    public void GFTM(View view) {
        updateText("GET FUEL-TANK-MODEL");
    }

    public void GS(View view) {
        updateText("GET STATUS");
    }

    public void SRESET(View view) {
        updateText("SYSTEM-RESET");
    }

}

