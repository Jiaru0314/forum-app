package com.jit.avtivity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jit.R;
import com.jit.bean.User;
import com.jit.retrofit.ApiService;
import com.jit.retrofit.RetrofitManager;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, ViewTreeObserver.OnGlobalLayoutListener, TextWatcher {

    @BindView(R.id.ib_navigation_back)
    ImageButton mIbNavigationBack;//返回按钮
    @BindView(R.id.ll_login_layer)
    View mLlLoginLayer;
    @BindView(R.id.et_login_username)
    EditText mEtLoginUsername;//用户名输入框
    @BindView(R.id.et_login_pwd)
    EditText mEtLoginPwd;//密码输入框
    @BindView(R.id.ll_login_username)
    LinearLayout mLlLoginUsername;
    @BindView(R.id.iv_login_username_del)
    ImageView mIvLoginUsernameDel;
    @BindView(R.id.bt_login_submit)
    Button mBtLoginSubmit;
    @BindView(R.id.ll_login_pwd)
    LinearLayout mLlLoginPwd;
    @BindView(R.id.iv_login_pwd_del)
    ImageView mIvLoginPwdDel;
    @BindView(R.id.iv_login_logo)
    ImageView mIvLoginLogo;
    @BindView(R.id.ly_retrieve_bar)
    LinearLayout mLayBackBar;
    @BindView(R.id.tv_login_forget_pwd)
    TextView mTvLoginForgetPwd;
    @BindView(R.id.bt_login_register)
    Button mBtLoginRegister;
    @BindView(R.id.cb_auto_login)
    CheckBox cb_autoLogin;

    private int mLogoHeight;
    private int mLogoWidth;
    private SharedPreferences sp;


    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        return super.onCreatePanelMenu(featureId, menu);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        autoLogin();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void autoLogin() {
        sp = this.getSharedPreferences("forumInfo", Context.MODE_PRIVATE);
        if (sp.getBoolean("IS_AUTO_LOGIN", false)) {//如果设置了自动登录
            String sp_username = sp.getString("sp_username", "");
            String sp_password = sp.getString("sp_password", "");
            loginRequest(sp_username, sp_password);
        }
    }

    //初始化视图
    private void initView() {
        //注册其它事件
        mLayBackBar.getViewTreeObserver().addOnGlobalLayoutListener(this);
        mEtLoginUsername.setOnFocusChangeListener(this);
        mEtLoginUsername.addTextChangedListener(this);
        mEtLoginPwd.setOnFocusChangeListener(this);
        mEtLoginPwd.addTextChangedListener(this);
    }

    @OnClick({R.id.ib_navigation_back, R.id.et_login_username, R.id.et_login_pwd, R.id.iv_login_username_del,
            R.id.iv_login_pwd_del, R.id.bt_login_submit, R.id.bt_login_register, R.id.tv_login_forget_pwd, R.id.ll_login_layer})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_navigation_back: //返回
                finish();
                break;
            case R.id.et_login_username:
                mEtLoginPwd.clearFocus();
                mEtLoginUsername.setFocusableInTouchMode(true);
                mEtLoginUsername.requestFocus();
                break;
            case R.id.et_login_pwd:
                mEtLoginUsername.clearFocus();
                mEtLoginPwd.setFocusableInTouchMode(true);
                mEtLoginPwd.requestFocus();
                break;
            case R.id.iv_login_username_del:
                //清空用户名
                mEtLoginUsername.setText(null);
                break;
            case R.id.iv_login_pwd_del:
                //清空密码
                mEtLoginPwd.setText(null);
                break;
            case R.id.bt_login_submit:
                //登录
                String username = mEtLoginUsername.getText().toString().trim();
                String password = mEtLoginPwd.getText().toString().trim();
                loginRequest(username, password);
                break;
            case R.id.bt_login_register:
                //注册
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                break;
            case R.id.tv_login_forget_pwd:
                //忘记密码
                startActivity(new Intent(MainActivity.this, ForgetPwdActivity.class));
                break;
            case R.id.ll_login_layer:
            default:
                break;
        }
    }

    //用户名密码焦点改变
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();

        if (id == R.id.et_login_username) {
            if (hasFocus) {
                mLlLoginUsername.setActivated(true);
                mLlLoginPwd.setActivated(false);
            }
        } else {
            if (hasFocus) {
                mLlLoginPwd.setActivated(true);
                mLlLoginUsername.setActivated(false);
            }
        }
    }

    //显示或隐藏logo
    @Override
    public void onGlobalLayout() {
        final ImageView ivLogo = this.mIvLoginLogo;
        Rect KeypadRect = new Rect();

        mLayBackBar.getWindowVisibleDisplayFrame(KeypadRect);

        int screenHeight = mLayBackBar.getRootView().getHeight();
        int keypadHeight = screenHeight - KeypadRect.bottom;

        //隐藏logo
        if (keypadHeight > 300 && ivLogo.getTag() == null) {
            final int height = ivLogo.getHeight();
            final int width = ivLogo.getWidth();
            this.mLogoHeight = height;
            this.mLogoWidth = width;

            ivLogo.setTag(true);

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0);
            valueAnimator.setDuration(400).setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(animation -> {
                float animatedValue = (float) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = ivLogo.getLayoutParams();
                layoutParams.height = (int) (height * animatedValue);
                layoutParams.width = (int) (width * animatedValue);
                ivLogo.requestLayout();
                ivLogo.setAlpha(animatedValue);
            });

            if (valueAnimator.isRunning()) {
                valueAnimator.cancel();
            }
            valueAnimator.start();
        }
        //显示logo
        else if (keypadHeight < 300 && ivLogo.getTag() != null) {
            final int height = mLogoHeight;
            final int width = mLogoWidth;

            ivLogo.setTag(null);

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(400).setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(animation -> {
                float animatedValue = (float) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = ivLogo.getLayoutParams();
                layoutParams.height = (int) (height * animatedValue);
                layoutParams.width = (int) (width * animatedValue);
                ivLogo.requestLayout();
                ivLogo.setAlpha(animatedValue);
            });

            if (valueAnimator.isRunning()) {
                valueAnimator.cancel();
            }
            valueAnimator.start();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    //用户名密码输入事件
    @Override
    public void afterTextChanged(Editable s) {
        String username = mEtLoginUsername.getText().toString().trim();
        String pwd = mEtLoginPwd.getText().toString().trim();

        //是否显示清除按钮
        if (username.length() > 0) {
            mIvLoginUsernameDel.setVisibility(View.VISIBLE);
        } else {
            mIvLoginUsernameDel.setVisibility(View.INVISIBLE);
        }
        if (pwd.length() > 0) {
            mIvLoginPwdDel.setVisibility(View.VISIBLE);
        } else {
            mIvLoginPwdDel.setVisibility(View.INVISIBLE);
        }

        //登录按钮是否可用
        if (!TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(username)) {
            mBtLoginSubmit.setBackgroundResource(R.drawable.bg_login_submit);
            mBtLoginSubmit.setTextColor(getResources().getColor(R.color.white));
        } else {
            mBtLoginSubmit.setBackgroundResource(R.drawable.bg_login_submit_lock);
            mBtLoginSubmit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
        }
    }

    //登录
    private void loginRequest(String username, String password) {
        ApiService apiService = RetrofitManager.getInstance().getService();
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        Call<Integer> call = apiService.login(user);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Integer userId = response.body();
                if (null != userId) {//验证成功
                    SharedPreferences.Editor editor = sp.edit();
                    if (cb_autoLogin.isChecked()) {
                        editor.putString("sp_username", username);
                        editor.putString("sp_password", password);
                        editor.putBoolean("IS_AUTO_LOGIN", true);
                    }
                    editor.putInt("USER_ID", userId);
                    editor.apply();
                    startActivity(new Intent(MainActivity.this, ContainerActivity.class));
                } else {
                    Toast.makeText(MainActivity.this, "用户名或密码错误，请重写登录", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}