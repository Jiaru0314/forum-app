package com.jit.avtivity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.jit.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPwdActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_retrieve_pwd);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ib_navigation_back)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_navigation_back:
                finish();
                break;
        }
    }
}
