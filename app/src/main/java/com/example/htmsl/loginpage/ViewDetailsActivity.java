package com.example.htmsl.loginpage;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewDetailsActivity extends Activity {

    String name1;
    String pass1;
    int requestphoneid =1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_details);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED){

        } else {
            if(!ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_PHONE_STATE)) {
                String message = "You need to allow access to phone state";
                showMessageOKCancel(message,new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            });
              return;
            }
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_PHONE_STATE},requestphoneid);
        }
        Intent i = getIntent();
        name1 = i.getStringExtra(EnterDetailsActivity.nameKey);
        pass1 = i.getStringExtra("text_password");
        TextView nameView = (TextView) findViewById(R.id.display_name);
            nameView.setText(name1);
            TextView PassView = (TextView) findViewById(R.id.display_pass);
            PassView.setText(pass1);

        }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(ViewDetailsActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int []grantResults) {
        if(requestCode == requestphoneid) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {}
            else{
                Toast.makeText(this,"Read phone state permission is denied",Toast.LENGTH_SHORT).show();
            }
        }
    }
    }

