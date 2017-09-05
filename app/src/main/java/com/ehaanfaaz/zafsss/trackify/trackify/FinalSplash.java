package com.ehaanfaaz.zafsss.trackify.trackify;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class FinalSplash extends Activity {
    private SharedPreferences sharedPreferences;
    public static final String MyPreference="GreedUserPreference";
    public static final String NUMBER = "M_NUMBER";
    public static final String CNUMBER = "C_NUMBER";

    public static final String PASSWORD = "1_PASSWORD";
    public static final String PHASE1 = "1_PHASE";
    public static final String PHASE2 = "2_PHASE";
    public static final String PHASE3 = "3_PHASE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_splash);
        sharedPreferences=getSharedPreferences(MyPreference,MODE_PRIVATE);
        final String mobilenumber=sharedPreferences.getString(CNUMBER,null);

        final String otpNumber=sharedPreferences.getString(NUMBER,null);

        final String phrase1=sharedPreferences.getString(PHASE1,null);

        final String phrase2=sharedPreferences.getString(PHASE2,null);

        final String phrase3=sharedPreferences.getString(PHASE3,null);

        final String password=sharedPreferences.getString(PASSWORD,null);
        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mobilenumber != null )
                {
                    if(phrase1 !=null && phrase2 !=null && phrase3 !=null)
                    {
                        if (password !=null)
                        {
                            Intent ir=new Intent(getBaseContext(),Login.class);
                            startActivity(ir);
                        }
                        else
                        {
                            Intent ir=new Intent(getBaseContext(),Password.class);
                            startActivity(ir);
                        }

                    }
                    else
                    {
                        Intent ir=new Intent(getBaseContext(),Phrases.class);
                        startActivity(ir);

                    }

                }
                else {
                    Intent ir = new Intent(getBaseContext(), Sms.class);
                    startActivity(ir);
                }
                finish();


            }
        },1000);
    }
}
