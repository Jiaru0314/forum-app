package com.jit.avtivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jit.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private int countdown = 4;
    private Timer timer = new Timer();
    private Handler handler;
    private Runnable runnable;

    @BindView(R.id.countdown_text)
    TextView countdownText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);
        ButterKnife.bind(this);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        timer.schedule(timerTask, 1000, 1000);
        handler = new Handler();
        handler.postDelayed(runnable = () -> {
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            finish();
        }, 3000);
    }

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(() -> {
                countdown--;
                countdownText.setText("跳过 " + countdown);
                if (countdown < 0) {
                    timer.cancel();
                    countdownText.setVisibility(View.GONE);
                }
            });
        }
    };

    @OnClick(R.id.countdown_text)
    @Override
    public void onClick(View v) {
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        finish();
        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
}
