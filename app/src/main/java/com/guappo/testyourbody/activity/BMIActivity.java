package com.guappo.testyourbody.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.guappo.testyourbody.MySQLiteHelper;
import com.guappo.testyourbody.R;
import com.guappo.testyourbody.Result;
import com.guappo.testyourbody.splashscreen.SplashScreenBMI;


public class BMIActivity extends AppCompatActivity {

    private SeekBar sliderWeight;
    private SeekBar sliderHeight;
    private TextView txtBMI;
    private TextView txtValue;
    private ImageView imgBody;
    private TextView btnWeight;
    private TextView btnHeight;
    private TextView txtKg;
    private TextView txtCm;
    private MySQLiteHelper db;

    double bmi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)));

        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUNBMI", true);
        if (isFirstRun)
        {
            startActivity(new Intent(this, SplashScreenBMI.class));
            SharedPreferences.Editor editor = wmbPreference.edit();
            editor.putBoolean("FIRSTRUNBMI", false);
            editor.commit();
        }

        sliderWeight = findViewById(R.id.sliderWeight);
        sliderHeight = findViewById(R.id.sliderHeight);
        imgBody = findViewById(R.id.imgBody);
        txtBMI = findViewById(R.id.txtBMI);
        txtValue = findViewById(R.id.txtValue);
        btnWeight = findViewById(R.id.btnWeight);
        btnHeight = findViewById(R.id.btnHeight);
        txtKg = findViewById(R.id.txtKg);
        txtCm = findViewById(R.id.txtCm);
        slider(sliderWeight);
        slider(sliderHeight);

        db = new MySQLiteHelper(this);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean isChecked = sharedPreferences.getBoolean("switch_preference_1", false);
        if (isChecked) {
            setUK();
        } else {
            setEU();
        }

    }

    public void setUK() {
        txtKg.setText(R.string.unitWeightUK);
        txtCm.setText(R.string.unitHeightUK);
        sliderWeight.setMax(350);
        sliderHeight.setMax(100);
        sliderHeight.setProgress(70);
        sliderWeight.setProgress(165);
    }

    public void setEU() {
        txtKg.setText(R.string.unitWeight);
        txtCm.setText(R.string.unitHeight);
        sliderWeight.setMax(150);
        sliderHeight.setMax(220);
        sliderHeight.setProgress(180);
        sliderWeight.setProgress(70);
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
            case R.id.save:
                db.addResult(new Result("BMI", (int) bmi));
                db.getAllResults();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void slider(SeekBar s) {
        if (s.getId() == R.id.sliderHeight) {
            btnHeight.setText("" + (s.getProgress()));
        } else {
            btnWeight.setText("" + (s.getProgress()));
        }
        s.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (seekBar.getId() == R.id.sliderHeight) {
                    btnHeight.setText("" + (progress));
                } else {
                    btnWeight.setText("" + (progress));
                }
                changeImage();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void changeImage() {
        bmi = calculateBMI(sliderWeight.getProgress(), sliderHeight.getProgress());
        if (bmi > 40) {
            imgBody.setImageResource(R.drawable.obese_iii);
            txtBMI.setText("Obesity of III class");
        }
        if (bmi > 35 && bmi <= 40) {
            imgBody.setImageResource(R.drawable.obese_ii);
            txtBMI.setText("Obesity of II class");
        }
        if (bmi > 30 && bmi <= 35) {
            imgBody.setImageResource(R.drawable.obese_i);
            txtBMI.setText("Obesity of I class");
        }
        if (bmi > 25 && bmi <= 30) {
            imgBody.setImageResource(R.drawable.overweight);
            txtBMI.setText("Overweight");
        }
        if (bmi > 18.5 && bmi <= 25) {
            imgBody.setImageResource(R.drawable.normal);
            txtBMI.setText("Normal");
        }
        if (bmi > 17.5 && bmi <= 18.5) {
            imgBody.setImageResource(R.drawable.underweight);
            txtBMI.setText("Slightly Underweight");
        }
        if (bmi > 16 && bmi <= 17.5) {
            imgBody.setImageResource(R.drawable.severely_underweight);
            txtBMI.setText("Underweight");
        }
        if (bmi < 16) {
            imgBody.setImageResource(R.drawable.very_severely_underweight);
            txtBMI.setText("Severely Underweight");
        }
        int bmi2 = (int) bmi;
        txtValue.setText("Il tuo BMI: " + bmi2);
    }

    private double calculateBMI(double weight, double height) {
        if (txtKg.getText().equals("kg")) {
            height = height * 0.01;
            return weight / (height * height);
        } else {
            return (weight / (height * height)) * 703;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        return true;
    }

}
