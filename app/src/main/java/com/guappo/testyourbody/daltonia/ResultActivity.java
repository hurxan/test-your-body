package com.guappo.testyourbody.daltonia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.guappo.testyourbody.R;

public class ResultActivity extends AppCompatActivity {
    private TextView txtResult;
    private Button btnHome;
    private TextView txtAnswer;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);
        txtResult=(TextView)findViewById(R.id.textResult);
        btnHome=(Button)findViewById(R.id.btnHome);
        txtAnswer=(TextView)findViewById(R.id.textResult);
        Bundle datipassati = getIntent().getExtras();
        int m = Integer.parseInt(datipassati.getString("Risultato"));
        String tipo=datipassati.getString("Tipo");
        if(m==5)txtResult.setText("Non sei affetto da "+tipo);
        if(m>=2&&m<5)txtResult.setText("Hai una media "+tipo);
        if(m>=0&&m<2)txtResult.setText("Sei affetto da "+tipo);
        txtAnswer.setText("Hai fatto giusto "+m+" immagini su 5 totali");
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openPage1= new Intent(ResultActivity.this,HomeActivity.class);
                startActivity(openPage1);
            }
        });

    }
}
