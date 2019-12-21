package com.jit.avtivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jit.R;
import com.jit.bean.User;
import com.jit.retrofit.ApiService;
import com.jit.retrofit.RetrofitManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    @BindView(R.id.et_register_username)
    EditText rg_username;
    @BindView(R.id.et_register_pwd_input)
    EditText rg_password;
    @BindView(R.id.bt_register_submit)
    Button bt_submit;

    @BindView(R.id.iv_register_username_del)
    ImageView username_del;
    @BindView(R.id.iv_register_pwd_del)
    ImageView password_del;

    private String username;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register_step_two);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        rg_username.addTextChangedListener(this);
        rg_password.addTextChangedListener(this);
    }


    @OnClick({R.id.ib_navigation_back, R.id.et_register_username, R.id.et_register_pwd_input, R.id.iv_register_username_del,
            R.id.iv_register_pwd_del, R.id.bt_register_submit})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_navigation_back:
                finish();
                break;
            case R.id.et_register_username:
                rg_password.clearFocus();
                rg_username.setFocusableInTouchMode(true);
                rg_username.requestFocus();
                break;
            case R.id.et_register_pwd_input:
                rg_username.clearFocus();
                rg_password.setFocusableInTouchMode(true);
                rg_password.requestFocus();
                break;
            case R.id.iv_register_username_del:
                rg_username.setText(null);
                break;
            case R.id.iv_register_pwd_del:
                rg_password.setText(null);
                break;
            case R.id.bt_register_submit:
                register();
                break;
        }
    }

    private void register() {
        ApiService apiService = RetrofitManager.getInstance().getService();
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        Call<Integer> call = apiService.register(user);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.body() != -1) {
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    Toast.makeText(RegisterActivity.this, "恭喜，注册成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        username = rg_username.getText().toString().trim();
        password = rg_password.getText().toString().trim();
        if (username.length() > 0) {
            username_del.setVisibility(View.VISIBLE);
        } else {
            username_del.setVisibility(View.INVISIBLE);
        }

        if (password.length() > 0) {
            password_del.setVisibility(View.VISIBLE);
        } else {
            password_del.setVisibility(View.INVISIBLE);
        }

        if (!username.isEmpty() && !password.isEmpty()) {
            bt_submit.setBackgroundResource(R.drawable.bg_login_submit);
            bt_submit.setTextColor(getResources().getColor(R.color.white));
        } else {
            bt_submit.setBackgroundResource(R.drawable.bg_login_submit_lock);
            bt_submit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
        }
    }
}
