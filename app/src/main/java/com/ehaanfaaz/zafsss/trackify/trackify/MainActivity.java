package com.ehaanfaaz.zafsss.trackify.trackify;

import android.app.Activity;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class MainActivity extends Activity {
    public static final String MyPreference = "GreedUserPreference";
    private SharedPreferences sharedPreferences;
    public static final String PHASE1 = "1_PHASE";
    public static final String PHASE2 = "2_PHASE";
    public static final String PHASE3 = "3_PHASE";
    public static final String CNUMBER = "C_NUMBER";
    private static final String TAG = "MainActivity";
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private TextView tvRNumber,tvRPhrase1,tvRPhrase2,tvRPhrase3;
    private Button btnSavee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                            status.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
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
        tvRNumber=(TextView)findViewById(R.id.tvRegNumberr);
        tvRPhrase1=(TextView)findViewById(R.id.tvPhrase1Here);
        tvRPhrase2=(TextView)findViewById(R.id.tvPhrase2Here);
        tvRPhrase3=(TextView)findViewById(R.id.tvPhrase3Here);
        btnSavee=(Button)findViewById(R.id.btnPhraseSave);

        sharedPreferences=getSharedPreferences(MyPreference,MODE_PRIVATE);

        tvRNumber.setText(sharedPreferences.getString(CNUMBER,""));
        tvRPhrase1.setText(sharedPreferences.getString(PHASE1,""));

        tvRPhrase2.setText(sharedPreferences.getString(PHASE2,""));

        tvRPhrase3.setText(sharedPreferences.getString(PHASE3,""));
        btnSavee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String gphrase1 = tvRPhrase1.getText().toString();
                String gphrase2 = tvRPhrase2.getText().toString();
                String gphrase3 = tvRPhrase3.getText().toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(PHASE1, gphrase1);
                editor.putString(PHASE2, gphrase2);
                editor.putString(PHASE3, gphrase3);
                editor.commit();
                Toast.makeText(getBaseContext(), "Changes Saved", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
