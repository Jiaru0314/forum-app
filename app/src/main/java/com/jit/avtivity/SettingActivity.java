package com.jit.avtivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.jit.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    public void logout(View view) {
        SharedPreferences sp = this.getSharedPreferences("forumInfo", Context.MODE_PRIVATE);
        sp.edit().clear().apply();
        startActivity(new Intent(SettingActivity.this, MainActivity.class));
    }
}
