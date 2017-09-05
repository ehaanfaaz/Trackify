package com.ehaanfaaz.zafsss.trackify.trackify;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.ehaanfaaz.zafsss.trackify.trackify.Sms.MyPreference;
import static com.ehaanfaaz.zafsss.trackify.trackify.Sms.NUMBER;


public class OTPActivity extends Activity {
    public EditText etOTPN;
    public Button btnVerifyN;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        etOTPN=(EditText)findViewById(R.id.etOTP);
        btnVerifyN=(Button)findViewById(R.id.btnVerify);
        sharedPreferences=getSharedPreferences(MyPreference,MODE_PRIVATE);

        btnVerifyN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otpv=etOTPN.getText().toString();
                if(otpv.equals(sharedPreferences.getString(NUMBER,"")))
                {
                    Intent ir=new Intent(getBaseContext(),Phrases.class);
                    startActivity(ir);
                    finish();
                    Toast.makeText(getBaseContext(), "Verified", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getBaseContext(), "Wrong OTP.\n Enter correct OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}