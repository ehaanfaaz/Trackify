package com.ehaanfaaz.zafsss.trackify.trackify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;



public class SMSReceiver extends BroadcastReceiver {
    public static final String MyPreference = "GreedUserPreference";
    private SharedPreferences sharedPreferences;
    public static final String PHASE1 = "1_PHASE";
    public static final String PHASE2 = "2_PHASE";
    public static final String PHASE3 = "3_PHASE";
    public static final String CNUMBER = "C_NUMBER";
    String strMessage ;
    String senderNumber ;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        {

            sharedPreferences = context.getSharedPreferences(MyPreference, Context.MODE_PRIVATE);
            Bundle bundle = intent.getExtras();



            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdus.length; i++) {
                    SmsMessage sms= SmsMessage.createFromPdu((byte[]) pdus[i]);
                    senderNumber = sms.getOriginatingAddress();
                    strMessage =sms.getDisplayMessageBody();
                    String incnumb = null;
                }

            }
            if(senderNumber.equals(sharedPreferences.getString(CNUMBER,"")))
            {
                if (strMessage.equals(sharedPreferences.getString(PHASE1,"")) || strMessage.equals(sharedPreferences.getString(PHASE2,"")) || strMessage.equals(sharedPreferences.getString(PHASE3,"")))
                {

                    Toast.makeText(context, "From : "+senderNumber+ " Message : " +strMessage, Toast.LENGTH_SHORT).show();
                    SmsManager smsManager=SmsManager.getDefault();
                    smsManager.sendTextMessage(senderNumber,null,"This is just a Test. We'll get back" +
                            " to you shortly. Thank You :)", null, null);
                    /*Intent i = new Intent();
                    i.setClassName("com.test", "com.test.MainActivity");
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);*/
                    Intent intentone = new Intent(context.getApplicationContext(), LocationRetrieval.class);
                    intentone.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intentone);

                }
            }
        }
    }
}
