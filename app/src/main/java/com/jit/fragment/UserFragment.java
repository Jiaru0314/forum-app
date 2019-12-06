package com.jit.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jit.R;
import com.jit.bean.User;
import com.jit.retrofit.ApiService;
import com.jit.retrofit.RetrofitManager;
import com.jit.util.HttpUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFragment extends Fragment {

    @BindView(R.id.me_avatar)
    ImageView imageView;
    @BindView(R.id.me_username)
    TextView text_username;
    @BindView(R.id.me_description)
    TextView text_userDcrp;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        ApiService apiService = RetrofitManager.getInstance().getService();
        Call<User> callUser = apiService.getUserById(2);
        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    text_username.setText(user.getUsername());
                    text_userDcrp.setText(user.getDescription());
                    String path = user.getAvatar().substring(1);
                    Glide.with(getActivity()).load(HttpUtil.BaseUrl + path).into(imageView);
                } else {
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
}