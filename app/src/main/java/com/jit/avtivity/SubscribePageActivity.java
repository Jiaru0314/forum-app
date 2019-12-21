package com.jit.avtivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jit.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubscribePageActivity extends AppCompatActivity {

    @BindView(R.id.check_follow)
    TextView checkFollow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.follow_and_subscribe_list);
        ButterKnife.bind(this);
        initPage();
    }

    public void initPage() {
        checkFollow.setOnClickListener(v -> {
            String content = (String) checkFollow.getText();
            switch (content) {
                case "已关注":
                    checkFollow.setTextColor(Color.RED);
                    checkFollow.setText("关注");
                    break;
                case "关注":
                    checkFollow.setTextColor(Color.parseColor("#9d9d9d"));
                    checkFollow.setText("已关注");
                    break;
            }
        });

    }
}
