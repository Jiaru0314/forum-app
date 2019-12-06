package com.jit.avtivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jit.R;
import com.jit.bean.BlogDto;
import com.jit.retrofit.ApiService;
import com.jit.retrofit.RetrofitManager;
import com.jit.util.DateFormatUtil;
import com.jit.util.HttpUtil;
import com.zzhoujay.richtext.RichText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView img_back;
    @BindView(R.id.blog_user_avatar)
    ImageView img_user_avatar;
    @BindView(R.id.blog_content)
    TextView text_content;
    @BindView(R.id.blog_title)
    TextView text_title;
    @BindView(R.id.blog_createTime)
    TextView text_createTime;
    @BindView(R.id.blog_comment_num)
    TextView text_comments;
    @BindView(R.id.blof_prefers)
    TextView text_prefers;
    @BindView(R.id.blog_user_name)
    TextView text_username;

    BlogDto blogDto = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        ButterKnife.bind(this);
        intiData();
    }

    private void intiData() {
        Integer blogId = getIntent().getIntExtra("blogId", 1);
        ApiService apiService = RetrofitManager.getInstance().getService();
        Call<BlogDto> call = apiService.getBlogById(blogId);
        call.enqueue(new Callback<BlogDto>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<BlogDto> call, Response<BlogDto> response) {
                blogDto = response.body();
                runOnUiThread(() -> {//更新UI
                    RichText.fromMarkdown(blogDto.getContent()).into(text_content);
                    text_title.setText(blogDto.getTitle());
                    text_createTime.setText(DateFormatUtil.formatDate(blogDto.getCreate_time()));
                    text_prefers.setText(blogDto.getPrefers().toString());
                    text_comments.setText(blogDto.getViews().toString());
                    text_username.setText(blogDto.getUsername());
                    String path = blogDto.getAvatar().substring(1);
                    Glide.with(BlogActivity.this).load(HttpUtil.BaseUrl + path).into(img_user_avatar);
                });
            }

            @Override
            public void onFailure(Call<BlogDto> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.iv_back)
    public void blogPage_back(View view) {
        this.finish();
    }
}
