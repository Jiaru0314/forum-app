package com.jit.avtivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jit.R;
import com.jit.adapter.LinearCommentAdapter;
import com.jit.bean.BlogDto;
import com.jit.bean.Comment;
import com.jit.retrofit.ApiService;
import com.jit.retrofit.RetrofitManager;
import com.jit.utils.DateFormatUtil;
import com.jit.utils.HttpUtil;
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
    @BindView(R.id.blog_prefer)
    TextView text_prefers;
    @BindView(R.id.blog_user_name)
    TextView text_username;
    @BindView(R.id.edit_comment)
    EditText editText;
    @BindView(R.id.comment_submit)
    TextView comment_submit;
    @BindView(R.id.rv_comments)
    RecyclerView recyclerView;

    private BlogDto blogDto = null;
    private Integer blogId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        ButterKnife.bind(this);
        intiData();
        initEvents();
    }

    private void initEvents() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText.getText().toString().isEmpty()) {
                    comment_submit.setVisibility(View.INVISIBLE);
                } else {
                    comment_submit.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private void intiData() {
        blogId = getIntent().getIntExtra("blogId", 1);
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
                    text_comments.setText("评论 " + blogDto.getComments().size());
                    text_username.setText(blogDto.getUsername());
                    String path = blogDto.getAvatar().substring(1);
                    Glide.with(BlogActivity.this).load(HttpUtil.BaseUrl + path).into(img_user_avatar);
                    initRecyclerView();
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

    @OnClick(R.id.comment_submit)
    public void comment_submit(View view) {
        SharedPreferences sp = getSharedPreferences("forumInfo", Context.MODE_PRIVATE);
        int comment_user_id = sp.getInt("USER_ID", 0);
        String comment_content = editText.getText().toString();
        Comment comment = new Comment().setContent(comment_content)
                .setBlog_id(blogDto.getId()).setUser_id(comment_user_id);
        ApiService apiService = RetrofitManager.getInstance().getService();
        Call<Integer> call = apiService.addComment(comment);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.body() != 1) {
                    Toast.makeText(BlogActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BlogActivity.this, "评论成功", Toast.LENGTH_LONG).show();
                    finish();
                    Intent intent = new Intent(BlogActivity.this, BlogActivity.class);
                    intent.putExtra("blogId", blogDto.getId());
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(BlogActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearCommentAdapter linearCommentAdapter = new LinearCommentAdapter(this, blogDto.getComments());
        recyclerView.setAdapter(linearCommentAdapter);
    }
}
