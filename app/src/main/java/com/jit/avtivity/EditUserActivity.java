package com.jit.avtivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.jit.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditUserActivity extends AppCompatActivity {

    @BindView(R.id.user_icon)
    ImageView imageView;
    @BindView(R.id.user_nickname)
    TextView tv_nickname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user);
        ButterKnife.bind(this);
    }
}



