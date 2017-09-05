package com.ehaanfaaz.zafsss.trackify.trackify;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.TextView;

public class LocationRetrieval extends Activity implements LocationListener {
    private static final String TAG = "LocationRetrieval";
    protected LocationManager locationManager;
    protected Context context;
    TextView txtLat;
    private SharedPreferences sharedPreferences;
    public static final String CNUMBER = "C_NUMBER";
    public static final String MyPreference = "GreedUserPreference";
    String regnumr;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_retrieval);
        txtLat = (TextView) findViewById(R.id.textview1);

        sharedPreferences = getSharedPreferences(MyPreference, MODE_PRIVATE);
        regnumr = sharedPreferences.getString(CNUMBER, "");

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null);

    }

    @Override
    public void onLocationChanged(Location location) {
        txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        message=("http://maps.google.com/?q="+ location.getLatitude() +","+ location.getLongitude());
        Log.d(TAG, "onLocationChanged: message is: " + message);
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(regnumr, null, message, null, null);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
