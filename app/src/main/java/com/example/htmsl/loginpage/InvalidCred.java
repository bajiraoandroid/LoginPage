package com.example.htmsl.loginpage;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;


public class InvalidCred extends Activity {
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.invalid_cred);
            Button backbutton = (Button) findViewById(R.id.back_id);
            backbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent3 = new Intent(InvalidCred.this, EnterDetailsActivity.class);
                    startActivity(intent3);
                }
            });
        }
    public void requestReadPhoneState() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)== PackageManager.PERMISSION_GRANTED){

        }else
            if(!ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_PHONE_STATE)) {

            }
    }
    }
