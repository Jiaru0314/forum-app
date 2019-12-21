package com.jit.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jit.R;
import com.jit.avtivity.EditUserActivity;
import com.jit.avtivity.SettingActivity;
import com.jit.bean.User;
import com.jit.retrofit.ApiService;
import com.jit.retrofit.RetrofitManager;
import com.jit.utils.HttpUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.me_avatar)
    ImageView imageView;
    @BindView(R.id.me_username)
    TextView text_username;
    @BindView(R.id.me_description)
    TextView text_userDcrp;
    @BindView(R.id.user_fans_num)
    TextView text_userFans;
    @BindView(R.id.user_folws_num)
    TextView text_userFollows;
    @BindView(R.id.lyout_userInfo)
    LinearLayout linearLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        SharedPreferences sp = getContext().getSharedPreferences("forumInfo", Context.MODE_PRIVATE);
        int user_id = sp.getInt("USER_ID", 0);
        ApiService apiService = RetrofitManager.getInstance().getService();
        Call<User> callUser = apiService.getUserById(user_id);
        callUser.enqueue(new Callback<User>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    text_username.setText(user != null ? user.getUsername() : null);
                    text_userDcrp.setText(user != null ? user.getDescription() : null);
                    text_userFans.setText(user.getFans().toString());
                    text_userFollows.setText(user.getFollow().toString());
                    String path = user.getAvatar().substring(1);
                    Glide.with(getActivity()).load(HttpUtil.BaseUrl + path).into(imageView);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick({R.id.user_setting, R.id.lyout_userInfo})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_setting: //跳转到设置界面
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
            case R.id.lyout_userInfo: //跳转到修改信息界面
                startActivity(new Intent(getContext(), EditUserActivity.class));
                break;
            default:
                break;
        }
    }
}