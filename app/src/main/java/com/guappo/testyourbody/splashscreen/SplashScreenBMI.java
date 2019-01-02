package com.guappo.testyourbody.splashscreen;

import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.view.View;

import com.guappo.testyourbody.R;
import com.guappo.testyourbody.activity.SignInActivity;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;

public class SplashScreenBMI extends MaterialIntroActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableLastSlideAlphaExitTransition(true);

        getBackButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.custom_slide_background)
                .buttonsColor(R.color.custom_slide_buttons)
                .image(R.drawable.ic_weight_scale)
                .title("Test BMI")
                .description("Segui le prossime istruzioni")
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.first_slide_background)
                .buttonsColor(R.color.first_slide_buttons)
                .image(R.drawable.ic_muscle)
                .title("Inserisci peso e altezza")
                .description("I risultati saranno immediati")
                .build());
    }

    @Override
    public void onFinish() {
        super.onFinish();
    }

    @Override
    public void onBackPressed() {

    }
}