package com.example.htmsl.loginpage;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;

import com.mixpanel.android.mpmetrics.MixpanelAPI;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class EnterDetailsActivity extends AppCompatActivity {
    EditText Text1;
    EditText Text2;
    final String username = "Tarun";
    final String password1 = "agarwal";
    public String name;
    public String password;
    public static String nameKey="key_name";
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    List<String> permissionsNeeded = new ArrayList<String>();
    String permission;
    /*int flag = 0;
    String[] permissions = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    final int requestpermissionsid = 1;  */
    private void insertAccountPermission() {
        if(ContextCompat.checkSelfPermission(this,permission) == PackageManager.PERMISSION_GRANTED) {
            name = Text1.getText().toString();
            password = Text2.getText().toString();
            if ((name.equals(username)) && (password.equals(password1))) {
                Intent intent = new Intent(EnterDetailsActivity.this, com.example.htmsl.loginpage.CoffeeOrder.class);
                startActivity(intent);
            } else {
                Intent intent2 = new Intent(EnterDetailsActivity.this, com.example.htmsl.loginpage.InvalidCred.class);
                startActivity(intent2);
            }

        } else {
            String message = "You need to grant access to accounts";
            if(!ActivityCompat.shouldShowRequestPermissionRationale(this,permission)) {
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
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
            ActivityCompat.requestPermissions(this,new String[] {permission}
                    ,REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        }
    }

    private void insertPermissions() {

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add("GPS");
        if (!addPermission(permissionsList, Manifest.permission.READ_PHONE_STATE))
            permissionsNeeded.add("Read Contacts");
        if(!addPermission(permissionsList,Manifest.permission.GET_ACCOUNTS))
            permissionsNeeded.add("Get Accounts");

        if (permissionsList.size() > 0) {

            if(permissionsNeeded.contains("GPS")) {
                permissionsList.remove(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if(permissionsNeeded.contains("Read Contacts")) {
                permissionsList.remove(Manifest.permission.READ_PHONE_STATE);
            }
            if(permissionsNeeded.contains("Get Accounts")) {
                permissionsList.remove(Manifest.permission.GET_ACCOUNTS);
            }
            if((permissionsNeeded.size()) == 3 || permissionsList.size() == 0){
                // Log.v("permission list size",""+permissionsList.size());
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                // Log.v("first permission needed",permissionsNeeded.get(0));
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
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
            ActivityCompat.requestPermissions(this, permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return;
        }
        // insertDummyContact();
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,permission))
                return false;
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(EnterDetailsActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        if(requestCode == REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                name = Text1.getText().toString();
                password = Text2.getText().toString();
                if ((name.equals(username)) && (password.equals(password1))) {
                    Intent intent = new Intent(EnterDetailsActivity.this, com.example.htmsl.loginpage.CoffeeOrder.class);
                    startActivity(intent);
                } else {
                    Intent intent2 = new Intent(EnterDetailsActivity.this, com.example.htmsl.loginpage.InvalidCred.class);
                    startActivity(intent2);
                }
            }
        }
    }
   /* public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
            {
                Map<String, Integer> perms = new HashMap<String, Integer>();

                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.GET_ACCOUNTS,PackageManager.PERMISSION_GRANTED);

                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED)
                {
                    if (permissionsNeeded.size() > 0) {
                        // Need Rationale
                        String message = "You need to grant access to " + permissionsNeeded.get(0);
                        // Log.v("first permission needed",permissionsNeeded.get(0));
                        for (int i = 1; i < permissionsNeeded.size(); i++)
                            message = message + ", " + permissionsNeeded.get(i);
                        showMessageOKCancel(message,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                                        intent.setData(uri);
                                        startActivity(intent);
                                        // requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                        //        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                                    }
                                });
                        return;
                    }


                } else {
                    // Permission Denied
                    Toast.makeText(EnterDetailsActivity.this, "Some Permission is Denied", Toast.LENGTH_SHORT)
                            .show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }  */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String projectToken = "520e83fa3ac530e88b8f5c31499846f1";
        MixpanelAPI mixpanel = MixpanelAPI.getInstance(this, projectToken);
        // mixpanel.track("Enter Details",null);
        // mixpanel.flush();

        try {
            JSONObject props = new JSONObject();
            props.put("Gender", "Female");
            props.put("Logged in", false);
            mixpanel.track("MainActivity - onCreate called", props);
        } catch (JSONException e) {
            Log.e("MYAPP", "Unable to add properties to JSONObject", e);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button myButton = (Button) findViewById(R.id.login_button);
        Button LocationButton = (Button) findViewById(R.id.location_id);
        Button ViewDetailsButton = (Button) findViewById(R.id.view_details_id);
         Text1 = (EditText) findViewById(R.id.name_edit);
         Text2 = (EditText) findViewById(R.id.password_edit);
        try {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            Log.v("IMEI no. is", telephonyManager.getDeviceId());
        }catch(Exception e) {
            e.printStackTrace();
        }

        final TelephonyManager telephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (telephony.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
            final GsmCellLocation location = (GsmCellLocation) telephony.getCellLocation();
            Log.v("Location is",""+location);
            if (location != null) {
                String cellId = Integer.toString(location.getCid());
                Log.v("cell Id is",cellId);
                String lac = Integer.toString(location.getLac());
                Log.v("lac Id is",lac);
                String psc = Integer.toString(location.getPsc());
                Log.v("psc Id is",psc);
            }
        }

         String android_id = Settings.Secure.getString(this.getContentResolver(),
                 Settings.Secure.ANDROID_ID);
        Log.v("android id is",android_id);

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Log.v("Bluetooth address is",mBluetoothAdapter.getAddress());

        WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String address = info.getMacAddress();
        Log.v("Mac address is",address);

        //   final String username = "Tarun";
     // final String password1 = "agarwal";
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permission = Manifest.permission.GET_ACCOUNTS;
                insertAccountPermission();
             //   name = Text1.getText().toString();
              //  password = Text2.getText().toString();
             //   if ((name.equals(username)) && (password.equals(password1))) {
             //       Intent intent = new Intent(EnterDetailsActivity.this, com.example.htmsl.loginpage.CoffeeOrder.class);
             //       startActivity(intent);
             //   } else {
             //       Intent intent2 = new Intent(EnterDetailsActivity.this, com.example.htmsl.loginpage.InvalidCred.class);
             //       startActivity(intent2);
             //   }

            }
        });
       // insertAccountPermission();
        LocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permission = Manifest.permission.ACCESS_FINE_LOCATION;
                insertAccountPermission();
            }
        });
        ViewDetailsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EnterDetailsActivity.this,ViewDetailsActivity.class);
                startActivity(i);
            }
         });
        }
/* public void insertPermissions() {
    if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            && (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)) {
    } else {
     /*   if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(this, "Location Permission is required", Toast.LENGTH_SHORT).show();
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
            Toast.makeText(this, "Read Contacts Permission is required", Toast.LENGTH_SHORT).show();
        }
        if ((!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE))
                &&(!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))) {
            ShowMessageOkCancel("You need to allow access to CONTACTS and LOCATION", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            });
            return;
        }
        else if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ShowMessageOkCancel("You need to allow access to LOCATION", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            });
            return;
        }
        else if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
            ShowMessageOkCancel("You need to allow access to CONTACTS", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            });
            return;
        }

        // if(flag != 1)
        ActivityCompat.requestPermissions(this, permissions, requestpermissionsid);
    }
}


    public void ShowMessageOkCancel(String message,DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK",okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == requestpermissionsid) {
                Log.v("Request code", "" + requestCode);
                Log.v("requestpermissionsid", "" + requestpermissionsid);
                Log.v("Permission1", "" + grantResults[0]);
                Log.v("Permission2", "" + grantResults[1]);
                if ((grantResults[0] == PackageManager.PERMISSION_GRANTED)&&(grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                }
                //Intent Refresh = new Intent(EnterDetailsActivity.this, com.example.htmsl.loginpage.InvalidCred.class);
                //startActivity(Refresh);
                //finish();
                else {
                    Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access data.", Toast.LENGTH_LONG).show();
                }

        }
    }   */


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_enter_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
