package com.example.mysticalmusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mLight;
    MediaPlayer dayPlayer, nightPlayer;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        image = (ImageView) findViewById(R.id.imageView);
        nightPlayer = MediaPlayer.create(this, R.raw.mystical_music);
        nightPlayer.setLooping(true);
        dayPlayer = MediaPlayer.create(this, R.raw.birds);
        dayPlayer.setLooping(true);
        dayPlayer.start();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { //Изменение точности показаний датчика
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) { //Изменение показаний датчиков
        if (event.values[0] < 5) {
            image.setImageResource(R.drawable.moon);
            if (!nightPlayer.isPlaying()) {
                nightPlayer.start();
                dayPlayer.pause();
            }
        }
        else {
            image.setImageResource(R.drawable.sun);
            if (nightPlayer.isPlaying()) {
                nightPlayer.pause();
                dayPlayer.start();
            }
        }
    }
}