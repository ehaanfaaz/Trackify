package com.ehaanfaaz.zafsss.trackify.trackify;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

public class Login extends Activity {
    private SharedPreferences sharedPreferences;
    public static final String MyPreference="GreedUserPreference";
    public static final String PASSWORD = "1_PASSWORD";

    private static final String TAG = "Login";
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private EditText etLog;
    private Button btnSub, btnSOSe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
                            status.startResolutionForResult(Login.this, REQUEST_CHECK_SETTINGS);
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
        etLog=(EditText)findViewById(R.id.etLogIn);
        btnSub=(Button)findViewById(R.id.btnSubmit);
        btnSOSe=(Button)findViewById(R.id.btnSOS);

        sharedPreferences=getSharedPreferences(MyPreference,MODE_PRIVATE);
        final String password=sharedPreferences.getString(PASSWORD,"");
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginpass=etLog.getText().toString().trim();
                if(loginpass.equals(sharedPreferences.getString(PASSWORD,"")))
                {
                    Intent ir=new Intent(getBaseContext(),MainActivity.class);
                    startActivity(ir);
                    finish();
                }
                else
                {
                    etLog.setError("Enter correct password");
                    etLog.setText("");
                }
            }
        });
        btnSOSe.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                Intent ir=new Intent(getBaseContext(),LocationRetrieval.class);
                startActivity(ir);
            }
        });


    }
}
