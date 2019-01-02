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

public class SplashScreenHearing extends MaterialIntroActivity {
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
                .backgroundColor(R.color.first_slide_background)
                .buttonsColor(R.color.first_slide_buttons)
                .image(R.drawable.ic_ear)
                .title("Test dell'udito")
                .description("Segui le prossime istruzioni")
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.fourth_slide_background)
                .buttonsColor(R.color.fourth_slide_buttons)
                .image(R.drawable.ic_play_button)
                .title("Premi sulle icone per ascoltare")
                .description("Se senti il suono premi il 'tick', altrimenti la croce\n")
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