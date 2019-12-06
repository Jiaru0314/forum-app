package com.jit.avtivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.jit.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageActivity extends AppCompatActivity {

    @BindView(R.id.image_view)
    ImageView imageView;
    @BindView(R.id.btn_load)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(ImageActivity.this);
    }

    @OnClick({R.id.btn_load})
    public void loadImage(View view) {
        String url = "http://192.168.64.1:8080/image/ico_06.jpg";
        Glide.with(this).load(url).into(imageView);
    }
}
