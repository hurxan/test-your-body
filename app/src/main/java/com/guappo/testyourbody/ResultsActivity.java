package com.guappo.testyourbody;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.guappo.testyourbody.activity.MainActivity;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        int result = intent.getIntExtra("result", 0);
        TextView textView = findViewById(R.id.textResult);
        Button button = findViewById(R.id.btnHome);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        switch (result){

            case 1:textView.setText("Il tuo orecchio ha un'eta' di 60 anni");
            break;

            case 2:textView.setText("Il tuo orecchio ha un'eta' di 60 anni");
                break;

            case 3:textView.setText("Il tuo orecchio ha un'eta' di 40 anni");
                break;

            case 4:textView.setText("Il tuo orecchio ha un'eta' di 30 anni");
                break;

            case 5:textView.setText("Il tuo orecchio ha un'eta' minore di 20 anni");
                break;

             default: textView.setText("devi");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.noanim, R.anim.noanim);
        finish();
    }
}
