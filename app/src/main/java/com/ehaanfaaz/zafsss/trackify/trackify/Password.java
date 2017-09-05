package com.ehaanfaaz.zafsss.trackify.trackify;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Password extends Activity {

    public static final String MyPreference = "GreedUserPreference";
    private SharedPreferences sharedPreferences;
    public static final String PASSWORD = "1_PASSWORD";
    public static final String RETYPEPASS = "2_PASSWORD";

    private EditText etPass, etRePass;
    private Button btnLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        etPass=(EditText)findViewById(R.id.etPassword);
        etRePass=(EditText)findViewById(R.id.etRePass);
        btnLock=(Button)findViewById(R.id.btnLockApp);
        sharedPreferences = getSharedPreferences(MyPreference, MODE_PRIVATE);


        btnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String gpass=etPass.getText().toString();
                String gretype=etRePass.getText().toString();

        if(gpass.equals(""))
        {
            etPass.setError("Enter the password");
        }
        else if(gretype.equals(""))
        {
            etRePass.setError("Retype the Password");
        }
        else if(!gpass.equals(gretype))
        {
            etPass.setError("Password doesn't match");
            etRePass.setError("Password doesn't match");
                    etPass.setText("");
            etRePass.setText("");
        }
        else if (gpass.equals(gretype))
        {

            Toast.makeText(getBaseContext(), "Application Locked", Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PASSWORD,gpass);
        editor.putString(RETYPEPASS, gretype);
        editor.commit();
            Intent ir=new Intent(getBaseContext(),MainActivity.class);
            startActivity(ir);
            finish();
        }
        else
        {

        }
    }
});
    }
}
