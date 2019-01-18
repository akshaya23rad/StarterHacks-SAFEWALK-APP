package com.stacktips.speechtotext;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView mVoiceInputTv;
    private ImageButton mSpeakBtn;
    private RecognizerIntent myText;
    private boolean found = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVoiceInputTv = (TextView) findViewById(R.id.voiceInput);
        mSpeakBtn = (ImageButton) findViewById(R.id.btnSpeak);
        mSpeakBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                startVoiceInput();
                speechInput();
            }
        });

    }

    private void speechInput() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    //result = data.getStringArrayExtra(RecognizerIntent.EXTRA_RESULTS);
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String[] results = result.get(0).split(" ");
                    String empty = new String();
                    //Sets text on app
                    TextView input = (TextView) findViewById(R.id.textView);
                    input.setText(result.get(0));
                    EditText keyWord = (EditText)findViewById(R.id.keyWordEdit);
                    String keyWordString = keyWord.getText().toString();
                    EditText phoneNumber = (EditText)findViewById(R.id.phoneNumberEdit);
                    String phoneNumberString = phoneNumber.getText().toString();

                    // Default vaules
                    if (keyWordString.equals(empty)){
                        keyWordString = "pineapple";
                    }
                    if (phoneNumberString.equals(empty)){
                        phoneNumberString = "1234567890";
                    }
                    //Checks if keyword is detected, if so dials set phone number
                    for (int i=0; i<results.length; i++){
                        if (results[i].equals(keyWordString)){
                            found = true;
                            dialPhoneNumber(phoneNumberString);
                        }
                    }
                }
                break;
            }

        }
    }
    public boolean getFound(){
        return found;
    }

    //Dialer function call
    private void dialPhoneNumber(final String phoneNumber){
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }
}