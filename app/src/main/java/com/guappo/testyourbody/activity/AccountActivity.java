package com.guappo.testyourbody.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.guappo.testyourbody.MySQLiteHelper;
import com.guappo.testyourbody.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountActivity extends AppCompatActivity {

    private CircleImageView profilePic;
    private TextView userName, userEmail;
    private TextView txtType1, txtType2, txtType3, txtType4, txtType5;
    private TextView txtValue1, txtValue2, txtValue3, txtValue4, txtValue5;
    private ImageView image1, image2, image3, image4, image5;
    private TextView noData;
    private ImageButton imageButton;

    CardView cardView1, cardView2, cardView3, cardView4, cardView5;
    private MySQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)));


        cardView1 = findViewById(R.id.card1);
        cardView2 = findViewById(R.id.card2);
        cardView3 = findViewById(R.id.card3);
        cardView4 = findViewById(R.id.card4);
        cardView5 = findViewById(R.id.card5);

        txtValue1 = findViewById(R.id.value1);
        txtValue2 = findViewById(R.id.value2);
        txtValue3 = findViewById(R.id.value3);
        txtValue4 = findViewById(R.id.value4);
        txtValue5 = findViewById(R.id.value5);

        txtType1 = findViewById(R.id.type1);
        txtType2 = findViewById(R.id.type2);
        txtType3 = findViewById(R.id.type3);
        txtType4 = findViewById(R.id.type4);
        txtType5 = findViewById(R.id.type5);

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);

        cardView1.setVisibility(View.GONE);
        cardView2.setVisibility(View.GONE);
        cardView3.setVisibility(View.GONE);
        cardView4.setVisibility(View.GONE);
        cardView5.setVisibility(View.GONE);

        noData = findViewById(R.id.noData);

        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);

        db = new MySQLiteHelper(this);

        userName.setText(SignInActivity.getUserName().toString());
        userEmail.setText(SignInActivity.getUserEmail().toString());
        profilePic = findViewById(R.id.profilePic);
        Picasso.with(this).load(SignInActivity.getProfilePic()).into(profilePic);





        if (db.getAllResults().size() == 0) {
            noData.setVisibility(View.VISIBLE);
        } else {
            noData.setVisibility(View.GONE);
        }
        if (cardView1.getVisibility() == View.GONE && db.getAllResults().size() >= 1) {
            cardView1.setVisibility(View.VISIBLE);
            txtType1.setText(db.getResult(db.getAllResults().size()).getType());
            txtValue1.setText(String.valueOf(db.getResult(db.getAllResults().size()).getResult()));
            if (db.getResult(db.getAllResults().size()).getType().equals("HeartRate")) {
                image1.setImageResource(R.drawable.ic_heart);
            }
            if (db.getResult(db.getAllResults().size()).getType().equals("BMI")) {
                if (db.getResult(db.getAllResults().size()).getResult() > 40) {
                    image1.setImageResource(R.drawable.obese_iii);
                }
                if (db.getResult(db.getAllResults().size()).getResult() > 35 && db.getResult(db.getAllResults().size()).getResult() <= 40) {
                    image1.setImageResource(R.drawable.obese_ii);
                }
                if (db.getResult(db.getAllResults().size()).getResult() > 30 && db.getResult(db.getAllResults().size()).getResult() <= 35) {
                    image1.setImageResource(R.drawable.obese_i);
                }
                if (db.getResult(db.getAllResults().size()).getResult() > 25 && db.getResult(db.getAllResults().size()).getResult() <= 30) {
                    image1.setImageResource(R.drawable.overweight);
                }
                if (db.getResult(db.getAllResults().size()).getResult() > 18.5 && db.getResult(db.getAllResults().size()).getResult() <= 25) {
                    image1.setImageResource(R.drawable.normal);
                }
                if (db.getResult(db.getAllResults().size()).getResult() > 17.5 && db.getResult(db.getAllResults().size()).getResult() <= 18.5) {
                    image1.setImageResource(R.drawable.underweight);
                }
                if (db.getResult(db.getAllResults().size()).getResult() > 16 && db.getResult(db.getAllResults().size()).getResult() <= 17.5) {
                    image1.setImageResource(R.drawable.severely_underweight);
                }
                if (db.getResult(db.getAllResults().size()).getResult() < 16) {
                    image1.setImageResource(R.drawable.very_severely_underweight);
                }
            } else if (db.getResult(db.getAllResults().size()).getType().equals("Hearing Test")) {
                image1.setImageResource(R.drawable.ic_ear);
            } else if (db.getResult(db.getAllResults().size()).getType().equals("Vision Test")) {
                image1.setImageResource(R.drawable.ic_eye);
            }
        }
        if (cardView2.getVisibility() == View.GONE && db.getAllResults().size() >= 2) {
            cardView2.setVisibility(View.VISIBLE);
            txtType2.setText(db.getResult(db.getAllResults().size() - 1).getType());
            txtValue2.setText(String.valueOf(db.getResult(db.getAllResults().size() - 1).getResult()));
            if (db.getResult(db.getAllResults().size() - 1).getType().equals("HeartRate")) {
                image2.setImageResource(R.drawable.ic_heart);
            } else if (db.getResult(db.getAllResults().size() - 1).getType().equals("BMI")) {
                if (db.getResult(db.getAllResults().size() - 1).getResult() > 40) {
                    image2.setImageResource(R.drawable.obese_iii);
                }
                if (db.getResult(db.getAllResults().size() - 1).getResult() > 35 && db.getResult(db.getAllResults().size() - 1).getResult() <= 40) {
                    image2.setImageResource(R.drawable.obese_ii);
                }
                if (db.getResult(db.getAllResults().size() - 1).getResult() > 30 && db.getResult(db.getAllResults().size() - 1).getResult() <= 35) {
                    image2.setImageResource(R.drawable.obese_i);
                }
                if (db.getResult(db.getAllResults().size() - 1).getResult() > 25 && db.getResult(db.getAllResults().size() - 1).getResult() <= 30) {
                    image2.setImageResource(R.drawable.overweight);
                }
                if (db.getResult(db.getAllResults().size() - 1).getResult() > 18.5 && db.getResult(db.getAllResults().size() - 1).getResult() <= 25) {
                    image2.setImageResource(R.drawable.normal);
                }
                if (db.getResult(db.getAllResults().size() - 1).getResult() > 17.5 && db.getResult(db.getAllResults().size() - 1).getResult() <= 18.5) {
                    image2.setImageResource(R.drawable.underweight);
                }
                if (db.getResult(db.getAllResults().size() - 1).getResult() > 16 && db.getResult(db.getAllResults().size() - 1).getResult() <= 17.5) {
                    image2.setImageResource(R.drawable.severely_underweight);
                }
                if (db.getResult(db.getAllResults().size() - 1).getResult() < 16) {
                    image2.setImageResource(R.drawable.very_severely_underweight);
                }
            } else if (db.getResult(db.getAllResults().size()).getType().equals("Hearing Test")) {
                image2.setImageResource(R.drawable.ic_ear);
            } else if (db.getResult(db.getAllResults().size()).getType().equals("Vision Test")) {
                image2.setImageResource(R.drawable.ic_eye);
            }
        }
        if (cardView3.getVisibility() == View.GONE && db.getAllResults().size() >= 3) {
            cardView3.setVisibility(View.VISIBLE);
            txtType3.setText(db.getResult(db.getAllResults().size() - 2).getType());
            txtValue3.setText(String.valueOf(db.getResult(db.getAllResults().size() - 2).getResult()));
            if (db.getResult(db.getAllResults().size() - 2).getType().equals("HeartRate")) {
                image3.setImageResource(R.drawable.ic_heart);
            } else if (db.getResult(db.getAllResults().size() - 2).getType().equals("BMI")) {
                if (db.getResult(db.getAllResults().size() - 2).getResult() > 40) {
                    image3.setImageResource(R.drawable.obese_iii);
                }
                if (db.getResult(db.getAllResults().size() - 2).getResult() > 35 && db.getResult(db.getAllResults().size() - 2).getResult() <= 40) {
                    image3.setImageResource(R.drawable.obese_ii);
                }
                if (db.getResult(db.getAllResults().size() - 2).getResult() > 30 && db.getResult(db.getAllResults().size() - 2).getResult() <= 35) {
                    image3.setImageResource(R.drawable.obese_i);
                }
                if (db.getResult(db.getAllResults().size() - 2).getResult() > 25 && db.getResult(db.getAllResults().size() - 2).getResult() <= 30) {
                    image3.setImageResource(R.drawable.overweight);
                }
                if (db.getResult(db.getAllResults().size() - 2).getResult() > 18.5 && db.getResult(db.getAllResults().size() - 2).getResult() <= 25) {
                    image3.setImageResource(R.drawable.normal);
                }
                if (db.getResult(db.getAllResults().size() - 2).getResult() > 17.5 && db.getResult(db.getAllResults().size() - 2).getResult() <= 18.5) {
                    image3.setImageResource(R.drawable.underweight);
                }
                if (db.getResult(db.getAllResults().size() - 2).getResult() > 16 && db.getResult(db.getAllResults().size() - 2).getResult() <= 17.5) {
                    image3.setImageResource(R.drawable.severely_underweight);
                }
                if (db.getResult(db.getAllResults().size() - 2).getResult() < 16) {
                    image3.setImageResource(R.drawable.very_severely_underweight);
                }
            } else if (db.getResult(db.getAllResults().size()).getType().equals("Hearing Test")) {
                image3.setImageResource(R.drawable.ic_ear);
            } else if (db.getResult(db.getAllResults().size()).getType().equals("Vision Test")) {
                image3.setImageResource(R.drawable.ic_eye);
            }
        }
        if (cardView4.getVisibility() == View.GONE && db.getAllResults().size() >= 4) {
            cardView4.setVisibility(View.VISIBLE);
            txtType4.setText(db.getResult(db.getAllResults().size() - 3).getType());
            txtValue4.setText(String.valueOf(db.getResult(db.getAllResults().size() - 3).getResult()));
            if (db.getResult(db.getAllResults().size() - 3).getType().equals("HeartRate")) {
                image4.setImageResource(R.drawable.ic_heart);
            } else if (db.getResult(db.getAllResults().size() - 3).getType().equals("BMI")) {
                if (db.getResult(db.getAllResults().size() - 3).getResult() > 40) {
                    image4.setImageResource(R.drawable.obese_iii);
                }
                if (db.getResult(db.getAllResults().size() - 3).getResult() > 35 && db.getResult(db.getAllResults().size() - 3).getResult() <= 40) {
                    image4.setImageResource(R.drawable.obese_ii);
                }
                if (db.getResult(db.getAllResults().size() - 3).getResult() > 30 && db.getResult(db.getAllResults().size() - 3).getResult() <= 35) {
                    image4.setImageResource(R.drawable.obese_i);
                }
                if (db.getResult(db.getAllResults().size() - 3).getResult() > 25 && db.getResult(db.getAllResults().size() - 3).getResult() <= 30) {
                    image4.setImageResource(R.drawable.overweight);
                }
                if (db.getResult(db.getAllResults().size() - 3).getResult() > 18.5 && db.getResult(db.getAllResults().size() - 3).getResult() <= 25) {
                    image4.setImageResource(R.drawable.normal);
                }
                if (db.getResult(db.getAllResults().size() - 3).getResult() > 17.5 && db.getResult(db.getAllResults().size() - 3).getResult() <= 18.5) {
                    image4.setImageResource(R.drawable.underweight);
                }
                if (db.getResult(db.getAllResults().size() - 3).getResult() > 16 && db.getResult(db.getAllResults().size() - 3).getResult() <= 17.5) {
                    image4.setImageResource(R.drawable.severely_underweight);
                }
                if (db.getResult(db.getAllResults().size() - 3).getResult() < 16) {
                    image4.setImageResource(R.drawable.very_severely_underweight);
                }
            } else if (db.getResult(db.getAllResults().size()).getType().equals("Hearing Test")) {
                image4.setImageResource(R.drawable.ic_ear);
            } else if (db.getResult(db.getAllResults().size()).getType().equals("Vision Test")) {
                image4.setImageResource(R.drawable.ic_eye);
            }
        }
        if (cardView5.getVisibility() == View.GONE && db.getAllResults().size() >= 5) {
            cardView5.setVisibility(View.VISIBLE);
            txtType5.setText(db.getResult(db.getAllResults().size() - 4).getType());
            txtValue5.setText(String.valueOf(db.getResult(db.getAllResults().size() - 4).getResult()));
            if (db.getResult(db.getAllResults().size() - 4).getType().equals("HeartRate")) {
                image5.setImageResource(R.drawable.ic_heart);
            } else if (db.getResult(db.getAllResults().size() - 4).getType().equals("BMI")) {
                if (db.getResult(db.getAllResults().size() - 4).getResult() > 40) {
                    image5.setImageResource(R.drawable.obese_iii);
                }
                if (db.getResult(db.getAllResults().size() - 4).getResult() > 35 && db.getResult(db.getAllResults().size() - 4).getResult() <= 40) {
                    image5.setImageResource(R.drawable.obese_ii);
                }
                if (db.getResult(db.getAllResults().size() - 4).getResult() > 30 && db.getResult(db.getAllResults().size() - 4).getResult() <= 35) {
                    image5.setImageResource(R.drawable.obese_i);
                }
                if (db.getResult(db.getAllResults().size() - 4).getResult() > 25 && db.getResult(db.getAllResults().size() - 4).getResult() <= 30) {
                    image5.setImageResource(R.drawable.overweight);
                }
                if (db.getResult(db.getAllResults().size() - 4).getResult() > 18.5 && db.getResult(db.getAllResults().size() - 4).getResult() <= 25) {
                    image5.setImageResource(R.drawable.normal);
                }
                if (db.getResult(db.getAllResults().size() - 4).getResult() > 17.5 && db.getResult(db.getAllResults().size() - 4).getResult() <= 18.5) {
                    image5.setImageResource(R.drawable.underweight);
                }
                if (db.getResult(db.getAllResults().size() - 4).getResult() > 16 && db.getResult(db.getAllResults().size() - 4).getResult() <= 17.5) {
                    image5.setImageResource(R.drawable.severely_underweight);
                }
                if (db.getResult(db.getAllResults().size() - 4).getResult() < 16) {
                    image5.setImageResource(R.drawable.very_severely_underweight);
                }
            } else if (db.getResult(db.getAllResults().size()).getType().equals("Hearing Test")) {
                image5.setImageResource(R.drawable.ic_ear);
            } else if (db.getResult(db.getAllResults().size()).getType().equals("Vision Test")) {
                image5.setImageResource(R.drawable.ic_eye);
            }
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
            case R.id.clear:
                if (db.getAllResults().size() != 0) {
                    db.deleteDatabase(getApplicationContext());
                    finish();
                    startActivity(new Intent(getApplicationContext(), AccountActivity.class));
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.clear,menu);
        return true;
    }
}
