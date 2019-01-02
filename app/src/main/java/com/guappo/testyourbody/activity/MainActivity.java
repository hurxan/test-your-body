package com.guappo.testyourbody.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.guappo.testyourbody.R;
import com.guappo.testyourbody.daltonia.HomeActivity;
import com.guappo.testyourbody.daltonia.VisionTestActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView heartRateCard, bmiCard, visionTestCard, hearingTestCard, settingsCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        heartRateCard = findViewById(R.id.heartRateCard);
        bmiCard = findViewById(R.id.bmiCard);
        visionTestCard = findViewById(R.id.visionTestCard);
        hearingTestCard = findViewById(R.id.hearingTestCard);
        settingsCard = findViewById(R.id.historyCard);

        heartRateCard.setOnClickListener(this);
        bmiCard.setOnClickListener(this);
        visionTestCard.setOnClickListener(this);
        hearingTestCard.setOnClickListener(this);
        settingsCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heartRateCard:
                startActivity(new Intent(this, HeartRateMonitorActivity.class));
                overridePendingTransition(R.anim.noanim, R.anim.noanim);
                break;
            case R.id.bmiCard:
                startActivity(new Intent(this, BMIActivity.class));
                overridePendingTransition(R.anim.noanim, R.anim.noanim);
                break;
            case R.id.visionTestCard:
                startActivity(new Intent(this, HomeActivity.class));
                overridePendingTransition(R.anim.noanim, R.anim.noanim);
                break;
            case R.id.hearingTestCard:
                startActivity(new Intent(this, HearingTestActivity.class));
                overridePendingTransition(R.anim.noanim, R.anim.noanim);
                break;
            case R.id.historyCard:
                startActivity(new Intent(this, AccountActivity.class));
                overridePendingTransition(R.anim.noanim, R.anim.noanim);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
        return true;
    }
}
