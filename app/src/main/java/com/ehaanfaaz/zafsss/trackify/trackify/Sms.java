package com.ehaanfaaz.zafsss.trackify.trackify;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.Random;

public class Sms extends ActionBarActivity {
    private static final String TAG = "Sms";
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private EditText etNumber, etText;
    private Button btnSend;
    public static final String MyPreference = "GreedUserPreference";
    private SharedPreferences sharedPreferences;
    public static final String NUMBER = "M_NUMBER";
    public static final String CNUMBER = "C_NUMBER";


    public static final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    String phoneNo;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);


        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(Sms.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });



        etNumber = (EditText) findViewById(R.id.etMNumber);
        /**
        etText = (EditText) findViewById(R.id.etMMessage);
        **/
        btnSend = (Button) findViewById(R.id.btnSendSMS);
        sharedPreferences = getSharedPreferences(MyPreference, MODE_PRIVATE);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendSMSMessage();
            }
        });
    }


    protected void sendSMSMessage() {
        Random rand=new Random();
        int rnumber=rand.nextInt(999999)+1;
        String randString=String.valueOf(rnumber);
        phoneNo = etNumber.getText().toString().trim();/*
        message = etText.getText().toString();*/
        message=("Your 6-digit One Time Password (OTP) "+randString+". Enter the OTP");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NUMBER, randString);
        editor.putString(CNUMBER,"+91"+phoneNo);
        editor.clear();
        editor.commit();

        if (phoneNo.equals(""))
        {
            etNumber.setError("Enter the 10 digit number");
        }
        else
        {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
            Intent ir = new Intent(getBaseContext(), OTPActivity.class);
            startActivity(ir);
            finish();
        }

        int hasWriteContactsPermission = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_CONTACTS);
        }
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] {Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_FINE_LOCATION },
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
//    {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    return;
//                }
////                else
////                    {
////
////                    Toast.makeText(getApplicationContext(),
////                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
////                    return;
////                    }
//            }
//
//        }
//    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    return;

                } else {
                    // Permission Denied

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
