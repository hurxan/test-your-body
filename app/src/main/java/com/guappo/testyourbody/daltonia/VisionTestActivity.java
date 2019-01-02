package com.guappo.testyourbody.daltonia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.guappo.testyourbody.R;
import com.guappo.testyourbody.activity.MainActivity;
import com.guappo.testyourbody.splashscreen.SplashScreenVision;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class VisionTestActivity extends AppCompatActivity {

    private ArrayList<Integer> protanopia = new ArrayList<>();
    private ArrayList<Integer> deuteranopia = new ArrayList<>();
    private ArrayList<Integer> tritanopia = new ArrayList<>();
    private ArrayList<Integer> daltonia = new ArrayList<>();
    private ImageView imageView;
    private int n2 = 0;
    private int answer = 0;
    private int idDrawable = 0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision_test);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)));



        imageView = findViewById(R.id.immagine);
        protanopia.addAll(Arrays.asList(R.drawable.p_3, R.drawable.p_8, R.drawable.p_27, R.drawable.p_74, R.drawable.p_15, R.drawable.p_5));
        deuteranopia.addAll(Arrays.asList(R.drawable.d_2, R.drawable.d_6, R.drawable.d_45, R.drawable.d_29, R.drawable.d_97, R.drawable.d_57));
        tritanopia.addAll(Arrays.asList(R.drawable.t_0, R.drawable.t_12, R.drawable.t_42, R.drawable.t_5, R.drawable.t_16, R.drawable.t_7));
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        Bundle datipassati = getIntent().getExtras();
        tipo = datipassati.getString("Daltonia");
        random(tipo);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(btn1.getText().toString()) == n2) answer++;
                random();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(btn2.getText().toString()) == n2) answer++;
                random();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(btn3.getText().toString()) == n2) answer++;
                random();
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(btn4.getText().toString()) == n2) answer++;
                random();
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

    private void random(String tipo) {
        if (tipo.equals("Protanopia")) {
            daltonia.addAll(protanopia);
        } else if (tipo.equals("Deuteranopia")) {
            daltonia.addAll(deuteranopia);
        } else {
            daltonia.addAll(tritanopia);
        }
        random();
    }

    private void random() {
        int random = new Random().nextInt(daltonia.size());
        if (daltonia.size() == 1) {
            Intent openPage1 = new Intent(VisionTestActivity.this, ResultActivity.class);
            openPage1.putExtra("Risultato", "" + answer);
            openPage1.putExtra("Tipo", "" + tipo);
            startActivity(openPage1);
        } else {
            imageView.setImageResource(daltonia.get(random));
            idDrawable = daltonia.get(random);
            daltonia.remove(random);
            changeText();
        }
    }

    private void changeText() {
        String name = this.getResources().getResourceEntryName(idDrawable);
        n2 = Integer.parseInt(name.substring(2));
        setText();
    }

    private void setText() {
        int random = (int) (Math.random() * 4) + 1;
        if (random == 1) {
            btn1.setText("" + n2);
            btn2.setText("" + new Random().nextInt(99));
            btn3.setText("" + new Random().nextInt(99));
            btn4.setText("" + new Random().nextInt(99));
        }
        if (random == 2) {
            btn2.setText("" + n2);
            btn1.setText("" + new Random().nextInt(99));
            btn3.setText("" + new Random().nextInt(99));
            btn4.setText("" + new Random().nextInt(99));
        }
        if (random == 3) {
            btn3.setText("" + n2);
            btn1.setText("" + new Random().nextInt(99));
            btn2.setText("" + new Random().nextInt(99));
            btn4.setText("" + new Random().nextInt(99));
        }
        if (random == 4) {
            btn4.setText("" + n2);
            btn1.setText("" + new Random().nextInt(99));
            btn2.setText("" + new Random().nextInt(99));
            btn3.setText("" + new Random().nextInt(99));
        }
    }
}

