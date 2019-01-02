package com.guappo.testyourbody.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.guappo.testyourbody.ResultsActivity;
import com.guappo.testyourbody.splashscreen.SplashScreenHearing;
import com.guappo.testyourbody.R;

public class HearingTestActivity extends AppCompatActivity {

    private boolean isPlaying = false;
    private int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearing_test);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)));

        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUNHEARING", true);
        if (isFirstRun) {
            startActivity(new Intent(this, SplashScreenHearing.class));
            SharedPreferences.Editor editor = wmbPreference.edit();
            editor.putBoolean("FIRSTRUNHEARING", false);
            editor.commit();
        }

        final CheckBox check3k = (CheckBox) findViewById(R.id.check3k);
        final CheckBox check8k = (CheckBox) findViewById(R.id.check8k);
        final CheckBox check13k = (CheckBox) findViewById(R.id.check13k);
        final CheckBox check15k = (CheckBox) findViewById(R.id.check15k);
        final CheckBox check19k = (CheckBox) findViewById(R.id.check19k);
        final CheckBox failed3k = (CheckBox) findViewById(R.id.fail3k);
        final CheckBox failed8k = (CheckBox) findViewById(R.id.fail8k);
        final CheckBox failed13k = (CheckBox) findViewById(R.id.fail13k);
        final CheckBox failed15k = (CheckBox) findViewById(R.id.fail15k);
        final CheckBox failed19k = (CheckBox) findViewById(R.id.fail19k);

        final MediaPlayer mp1 = MediaPlayer.create(this, R.raw.a3k);
        final MediaPlayer mp2 = MediaPlayer.create(this, R.raw.a8k);
        final MediaPlayer mp3 = MediaPlayer.create(this, R.raw.a13k);
        final MediaPlayer mp4 = MediaPlayer.create(this, R.raw.a15k);
        final MediaPlayer mp5 = MediaPlayer.create(this, R.raw.a19k);

        final ImageView btn3k = (ImageView)findViewById(R.id.btn3k);
        btn3k.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(isPlaying){
                    isPlaying=false;
                    mp1.pause();
                    return;
                }
                mp1.start();
                isPlaying=true;
            }
        });

        final ImageView btn8k = (ImageView)findViewById(R.id.btn8k);
        btn8k.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(isPlaying){
                    isPlaying=false;
                    mp2.pause();
                    return;
                }
                mp2.start();
                isPlaying=true;
            }
        });

        final ImageView btn13k = (ImageView)findViewById(R.id.btn13k);
        btn13k.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(isPlaying){
                    isPlaying=false;
                    mp3.pause();
                    return;
                }
                mp3.start();
                isPlaying=true;
            }
        });

        final ImageView btn15k = (ImageView)findViewById(R.id.btn15k);
        btn15k.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(isPlaying){
                    isPlaying=false;
                    mp4.pause();
                    return;
                }
                mp4.start();
                isPlaying=true;
            }
        });

        final ImageView btn19k = (ImageView)findViewById(R.id.btn19k);
        btn19k.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(isPlaying){
                    isPlaying=false;
                    mp5.pause();
                    return;
                }
                mp5.start();
                isPlaying=true;
            }
        });

        check8k.setEnabled(false);
        check15k.setEnabled(false);
        check13k.setEnabled(false);
        check19k.setEnabled(false);


        //LISTENER CHECK

        check3k.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ( isChecked ) {
                    check3k.setEnabled(false);
                    check8k.setEnabled(true);
                    failed8k.setEnabled(true);
                    result=1;
                }
            }
        });

        check8k.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ( isChecked ) {
                    check3k.setEnabled(false);
                    check8k.setEnabled(false);
                    check13k.setEnabled(true);
                    failed13k.setEnabled(true);
                    result=2;
                }
            }
        });

        check13k.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ( isChecked ) {
                    check13k.setEnabled(false);
                    check15k.setEnabled(true);
                    failed15k.setEnabled(true);
                    result=3;
                }
            }
        });

        check15k.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ( isChecked ) {
                    check15k.setEnabled(false);
                    check19k.setEnabled(true);
                    failed19k.setEnabled(true);
                    result=4;
                }
            }
        });

        check19k.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ( isChecked ) {
                    check19k.setEnabled(false);
                    result=5;
                }
            }
        });

        //LISTENER FAIL

        failed3k.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ( isChecked ) {
                    check3k.setEnabled(false);
                    check8k.setEnabled(false);
                    check13k.setEnabled(false);
                    check15k.setEnabled(false);
                    check19k.setEnabled(false);
                    failed8k.setEnabled(false);
                    failed13k.setEnabled(false);
                    failed15k.setEnabled(false);
                    failed19k.setEnabled(false);
                    result=0;
                }
            }
        });

        failed8k.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ( isChecked ) {
                    check8k.setEnabled(false);
                    check13k.setEnabled(false);
                    check15k.setEnabled(false);
                    check19k.setEnabled(false);
                    failed8k.setEnabled(false);
                    failed13k.setEnabled(false);
                    failed15k.setEnabled(false);
                    failed19k.setEnabled(false);
                }
            }
        });

        failed13k.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ( isChecked ) {
                    check13k.setEnabled(false);
                    check15k.setEnabled(false);
                    check19k.setEnabled(false);
                    failed8k.setEnabled(false);
                    failed13k.setEnabled(false);
                    failed15k.setEnabled(false);
                    failed19k.setEnabled(false);
                }
            }
        });

        failed15k.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ( isChecked ) {
                    check15k.setEnabled(false);
                    check19k.setEnabled(false);
                    failed8k.setEnabled(false);
                    failed13k.setEnabled(false);
                    failed15k.setEnabled(false);
                    failed19k.setEnabled(false);;
                }
            }
        });

        failed19k.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ( isChecked ) {
                    check19k.setEnabled(false);
                    failed8k.setEnabled(false);
                    failed13k.setEnabled(false);
                    failed15k.setEnabled(false);
                    failed19k.setEnabled(false);
                }
            }
        });

        final Button btnEnd = (Button)findViewById(R.id.btnEnd);
        btnEnd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                endTest();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.noanim, R.anim.noanim);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void endTest(){
        resetCheck();
        Intent intent = new Intent(this, ResultsActivity.class);
        //Log.d("owo", String.valueOf(result));
        intent.putExtra("result",result);
        result=0;
        startActivity(intent);
    }

    public void resetCheck(){
        final CheckBox check3k = (CheckBox) findViewById(R.id.check3k);
        final CheckBox check8k = (CheckBox) findViewById(R.id.check8k);
        final CheckBox check13k = (CheckBox) findViewById(R.id.check13k);
        final CheckBox check15k = (CheckBox) findViewById(R.id.check15k);
        final CheckBox check19k = (CheckBox) findViewById(R.id.check19k);

        check3k.setEnabled(true);
        check8k.setEnabled(false);
        check13k.setEnabled(false);
        check15k.setEnabled(false);
        check19k.setEnabled(false);
        clearCheck();
    }

    public void clearCheck(){
        final CheckBox check3k = (CheckBox) findViewById(R.id.check3k);
        final CheckBox check8k = (CheckBox) findViewById(R.id.check8k);
        final CheckBox check13k = (CheckBox) findViewById(R.id.check13k);
        final CheckBox check15k = (CheckBox) findViewById(R.id.check15k);
        final CheckBox check19k = (CheckBox) findViewById(R.id.check19k);
        final CheckBox failed3k = (CheckBox) findViewById(R.id.fail3k);
        final CheckBox failed8k = (CheckBox) findViewById(R.id.fail8k);
        final CheckBox failed13k = (CheckBox) findViewById(R.id.fail13k);
        final CheckBox failed15k = (CheckBox) findViewById(R.id.fail15k);
        final CheckBox failed19k = (CheckBox) findViewById(R.id.fail19k);

        check3k.setChecked(false);
        check8k.setChecked(false);
        check13k.setChecked(false);
        check15k.setChecked(false);
        check19k.setChecked(false);
        failed3k.setChecked(false);
        failed8k.setChecked(false);
        failed13k.setChecked(false);
        failed15k.setChecked(false);
        failed19k.setChecked(false);
    }
}
