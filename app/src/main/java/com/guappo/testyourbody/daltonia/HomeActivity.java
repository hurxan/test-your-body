package com.guappo.testyourbody.daltonia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.guappo.testyourbody.R;
import com.guappo.testyourbody.activity.AccountActivity;
import com.guappo.testyourbody.activity.MainActivity;
import com.guappo.testyourbody.splashscreen.SplashScreenVision;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {
    private CircleImageView img1;
    private CircleImageView img2;
    private CircleImageView img3;
    private Intent openPage1 ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)));

        img1=findViewById(R.id.img1);
        img2=findViewById(R.id.img2);
        img3=findViewById(R.id.img3);
        openPage1= new Intent(HomeActivity.this,VisionTestActivity.class);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPage1.putExtra("Daltonia","Protanopia");
                startActivity(openPage1);
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPage1.putExtra("Daltonia","Deuteranopia");
                startActivity(openPage1);
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPage1.putExtra("Daltonia","Tritanopia");
                startActivity(openPage1);
            }
        });

        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUNEAR", true);
        if (isFirstRun)
        {
            startActivity(new Intent(this, SplashScreenVision.class));
            SharedPreferences.Editor editor = wmbPreference.edit();
            editor.putBoolean("FIRSTRUNEAR", false);
            editor.commit();
        }

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
}
