package com.example.greenpolepowersolutions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity implements View.OnClickListener{
    private CardView VONE,SONIC;
    ImageView logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        VONE = findViewById(R.id.VONE);
        SONIC = findViewById(R.id.SONIC);
        logout = findViewById(R.id.logout);


        VONE.setOnClickListener((View.OnClickListener) this);
        SONIC.setOnClickListener((View.OnClickListener) this);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(loginActivity.this, "LOGOUT SUCCESSFUL", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){
            case R.id.VONE : i = new Intent(this,VONE.class);startActivity(i);break;
            case R.id.SONIC : i = new Intent(this,SONIC.class);startActivity(i);break;
            default:break;
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id  = item.getItemId();
        if (id == R.id.logout){
            AlertDialog.Builder builder = new AlertDialog.Builder(loginActivity.this);
            builder.setMessage("DO YOU WANT TO LOGOUT ?");
            builder.setCancelable(true);

            builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        return true;

    }





}


