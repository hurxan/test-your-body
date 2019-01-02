package com.guappo.testyourbody.activity;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.gigamole.library.PulseView;
import com.guappo.testyourbody.HeartBeatView;
import com.guappo.testyourbody.MySQLiteHelper;
import com.guappo.testyourbody.R;
import com.guappo.testyourbody.Result;
import com.guappo.testyourbody.ImageProcessing;
import com.guappo.testyourbody.splashscreen.SplashScreenHeartRate;


public class HeartRateMonitorActivity extends AppCompatActivity implements PulseView.PulseListener, View.OnClickListener {

    private static final String TAG = "HeartRateMonitor";
    public static final AtomicBoolean processing = new AtomicBoolean(false);

    private static SurfaceView preview = null;
    private static SurfaceHolder previewHolder = null;
    private static Camera camera = null;
    private static TextView text = null;

    private static WakeLock wakeLock = null;

    private static int averageIndex = 0;
    private static final int averageArraySize = 3;
    private static final int[] averageArray = new int[averageArraySize];
    private static HeartBeatView heartbeat;
    private static HeartBeatView heartbeat2;


    public static enum TYPE {
        GREEN, RED
    }

    private static TYPE currentType = TYPE.GREEN;

    public static TYPE getCurrent() {
        return currentType;
    }

    private static int beatsIndex = 0;
    private static final int beatsArraySize = 4;
    private static final int[] beatsArray = new int[beatsArraySize];
    private static double beats = 0;
    private static long startTime = 0;

    public static PulseView pulseView;
    private MySQLiteHelper db;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate_monitor);

        setOffLight(camera);
        preview = (SurfaceView) findViewById(R.id.preview);
        previewHolder = preview.getHolder();
        previewHolder.addCallback(surfaceCallback);
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        setOffLight(camera);

        text = (TextView) findViewById(R.id.text);

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "DoNotDimScreen");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)));


        heartbeat = findViewById(R.id.heartbeat);
        heartbeat2 = findViewById(R.id.heartbeat2);

        heartbeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOffLight(camera);
                heartbeat.setVisibility(View.INVISIBLE);
                heartbeat2.setVisibility(View.VISIBLE);
            }
        });

        heartbeat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOnLight(camera);
                heartbeat.setVisibility(View.VISIBLE);
                heartbeat2.setVisibility(View.INVISIBLE);
            }
        });

        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUNHEART", true);
        if (isFirstRun) {
            finish();
            startActivity(new Intent(this, SplashScreenHeartRate.class));
            SharedPreferences.Editor editor = wmbPreference.edit();
            editor.putBoolean("FIRSTRUNHEART", false);
            editor.commit();
        }


        initUI();
        db = new MySQLiteHelper(this);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResume() {
        super.onResume();

        wakeLock.acquire();
        camera = Camera.open();
        startTime = System.currentTimeMillis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPause() {
        super.onPause();

        wakeLock.release();

        camera.setPreviewCallback(null);
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    public PreviewCallback previewCallback = new PreviewCallback() {

        /**
         * {@inheritDoc}
         */
        @Override
        public void onPreviewFrame(byte[] data, Camera cam) {
            if (data == null) throw new NullPointerException();
            Camera.Size size = cam.getParameters().getPreviewSize();
            if (size == null) throw new NullPointerException();

            if (!processing.compareAndSet(false, true)) return;

            int width = size.width;
            int height = size.height;

            int imgAvg = ImageProcessing.decodeYUV420SPtoRedAvg(data.clone(), height, width);
            // Log.i(TAG, "imgAvg="+imgAvg);
            if (imgAvg == 0 || imgAvg == 255) {
                processing.set(false);
                return;
            }

            if (imgAvg < 200) {
                processing.set(false);
                return;
            }

            int averageArrayAvg = 0;
            int averageArrayCnt = 0;
            for (int i = 0; i < averageArray.length; i++) {
                if (averageArray[i] > 0) {
                    averageArrayAvg += averageArray[i];
                    averageArrayCnt++;
                }
            }

            int rollingAverage = (averageArrayCnt > 0) ? (averageArrayAvg / averageArrayCnt) : 0;
            TYPE newType = currentType;
            if (imgAvg < rollingAverage) {
                newType = TYPE.RED;
                if (newType != currentType) {
                    beats++;
                }
            } else if (imgAvg > rollingAverage) {
                newType = TYPE.GREEN;
            }

            if (averageIndex == averageArraySize) averageIndex = 0;
            averageArray[averageIndex] = imgAvg;
            averageIndex++;

            // Transitioned from one state to another to the same
            if (newType != currentType) {
                currentType = newType;
            }

            long endTime = System.currentTimeMillis();
            double totalTimeInSecs = (endTime - startTime) / 1000;
            if (totalTimeInSecs >= 10) {

                double bps = (beats / totalTimeInSecs);
                int dpm = (int) (bps * 60d);
                if (dpm < 30 || dpm > 180) {
                    startTime = System.currentTimeMillis();
                    beats = 0;
                    processing.set(false);

                    return;

                }

                if (beatsIndex == beatsArraySize) beatsIndex = 0;
                beatsArray[beatsIndex] = dpm;
                beatsIndex++;

                int beatsArrayAvg = 0;
                int beatsArrayCnt = 0;
                for (int i = 0; i < beatsArray.length; i++) {
                    if (beatsArray[i] > 0) {
                        beatsArrayAvg += beatsArray[i];
                        beatsArrayCnt++;
                    }
                }
                int beatsAvg = (beatsArrayAvg / beatsArrayCnt);
                if (beatsAvg > 55 || beatsAvg < 110) {
                    text.setText(String.valueOf(beatsAvg));
                    db.addResult(new Result("HeartRate", beatsAvg));
                    db.getAllResults();
                }

                startTime = System.currentTimeMillis();
                beats = 0;
                setOffLight(camera);
            }

            processing.set(false);
        }
    };

    private SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {

        /**
         * {@inheritDoc}
         */
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                camera.setPreviewDisplay(previewHolder);
                camera.setPreviewCallback(previewCallback);
            } catch (Throwable t) {
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            Camera.Size size = getSmallestPreviewSize(width, height, parameters);
            if (size != null) {
                parameters.setPreviewSize(size.width, size.height);
                Log.d(TAG, "Using width=" + size.width + " height=" + size.height);
            }
            heartbeat.start();
            camera.setParameters(parameters);
            camera.startPreview();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            // Ignore
        }
    };

    private static Camera.Size getSmallestPreviewSize(int width, int height, Camera.Parameters parameters) {
        Camera.Size result = null;

        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;

                    if (newArea < resultArea) result = size;
                }
            }
        }

        return result;
    }

    /**
     * Turns off the torch
     *
     * @param cam the camera object
     */
    public static void setOffLight(Camera cam) {
        if (cam != null) {
            try {
                Camera.Parameters parameters = cam.getParameters();
                List<String> list = parameters.getSupportedFlashModes();
                if (list != null && list.size() > 0) {
                    parameters.setFlashMode("off");
                    cam.setParameters(parameters);
                }
                heartbeat.stop();
                pulseView.finishPulse();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Turns on the torch
     *
     * @param cam the camera object
     */
    public static void setOnLight(Camera cam) {
        if (cam != null) {
            try {
                Camera.Parameters parameters = cam.getParameters();
                List<String> list = parameters.getSupportedFlashModes();
                if (list != null && list.size() > 0) {
                    parameters.setFlashMode("torch");
                    cam.setParameters(parameters);
                }
                heartbeat.start();
                pulseView.startPulse();
            } catch (Exception e) {
            }
        }
    }


    private void initUI() {
        pulseView = findViewById(R.id.pulseview);
        pulseView.setPulseListener(this);
        pulseView.setOnClickListener(this);

        pulseView.setPulseColor(Color.RED);
        pulseView.setPulseCount(5);
        pulseView.setPulseMeasure(PulseView.PulseMeasure.WIDTH);
        pulseView.setIconHeight(180);
        pulseView.setIconWidth(157);
        pulseView.setIconRes(R.drawable.ic_heart_white);
        pulseView.setPulseAlpha(50);
        pulseView.setInterpolator(new LinearInterpolator());
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

    @Override
    public void onStartPulse() {

    }

    @Override
    public void onFinishPulse() {

    }

    @Override
    public void onClick(View view) {

    }

}