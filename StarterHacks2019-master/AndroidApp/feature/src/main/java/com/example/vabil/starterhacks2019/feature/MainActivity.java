package com.example.vabil.starterhacks2019.feature;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Dialer code
        findViewById(R.id.callButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dialPhoneNumber("1234567890");
            }
        });

        //Recording audio to file
        Button recordingButton = (Button)findViewById(R.id.buttonRecord);
        recordingButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Do stuff
            }

        });
    }


    //Dialer function call
    private void dialPhoneNumber(final String phoneNumber){
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }



}
