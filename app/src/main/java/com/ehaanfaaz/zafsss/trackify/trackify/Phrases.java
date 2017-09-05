package com.ehaanfaaz.zafsss.trackify.trackify;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Phrases extends Activity {
    public static final String MyPreference = "GreedUserPreference";
    private SharedPreferences sharedPreferences;
    public static final String PHASE1 = "1_PHASE";
    public static final String PHASE2 = "2_PHASE";
    public static final String PHASE3 = "3_PHASE";
    private EditText petPhrase1, petPhrase2, petPhrase3;
    private Button pbtnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases);
        petPhrase1=(EditText)findViewById(R.id.etPhrase1);
        petPhrase2=(EditText)findViewById(R.id.etPhrase2);
        petPhrase3=(EditText)findViewById(R.id.etPhrase3);
        pbtnSave=(Button)findViewById(R.id.btnSave);
        sharedPreferences = getSharedPreferences(MyPreference, MODE_PRIVATE);
    }
    public void OnPhraseRegister(View view) {
        String gphrase1 = petPhrase1.getText().toString();
        String gphrase2 = petPhrase2.getText().toString();
        String gphrase3 = petPhrase3.getText().toString();


        if (gphrase1.equals(""))
        {
            petPhrase1.setError("Enter Phrase 1");
        }
        else if (gphrase2.equals(""))
        {
            petPhrase2.setError("Enter Phrase 2");
        }
        else if (gphrase3.equals(""))
        {
            petPhrase3.setError("Enter Phrase 3");
        }
        else
            {
                SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(PHASE1, gphrase1);
            editor.putString(PHASE2, gphrase2);
            editor.putString(PHASE3, gphrase3);
                editor.commit();
            Intent ir = new Intent(getBaseContext(), Password.class);
            startActivity(ir);
                finish();
            Toast.makeText(getBaseContext(), "Phrases Saved Successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
