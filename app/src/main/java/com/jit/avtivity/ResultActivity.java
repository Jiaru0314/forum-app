package com.jit.avtivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jit.R;
import com.jit.adapter.LinearBlogAdapter;
import com.jit.adapter.LinearUserAdapter;
import com.jit.bean.BlogDto;
import com.jit.bean.User;
import com.jit.fragment.UsersFragment;
import com.jit.retrofit.ApiService;
import com.jit.retrofit.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultActivity extends AppCompatActivity {

    @BindView(R.id.rv_results)
    RecyclerView result_recyclerView;
    @BindView(R.id.result_number)
    TextView textView;

    private List<BlogDto> blogDtoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        ApiService apiService = RetrofitManager.getInstance().getService();
        String query = getIntent().getStringExtra("query");
        Integer userId = getIntent().getIntExtra("UserID", 0);
        if (null != query) {
            Call<List<BlogDto>> call = apiService.searchBlog(query);
            call.enqueue(new Callback<List<BlogDto>>() {
                @Override
                public void onResponse(Call<List<BlogDto>> call, Response<List<BlogDto>> response) {
                    if (null == response.body()) {
                        textView.setText("共 0 条数据");
                    } else {
                        blogDtoList = response.body();
                        textView.setText("共" + blogDtoList.size() + "条数据");
                        initRecyclerView();
                    }
                }

                @Override
                public void onFailure(Call<List<BlogDto>> call, Throwable t) {
                    Toast.makeText(ResultActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (!userId.equals(0)) {
            Call<List<BlogDto>> call = apiService.getUserBlogs(userId);
            call.enqueue(new Callback<List<BlogDto>>() {
                @Override
                public void onResponse(Call<List<BlogDto>> call, Response<List<BlogDto>> response) {
                    if (null == response.body()) {
                        textView.setText("共 0 篇博客");
                    } else {
                        blogDtoList = response.body();
                        textView.setText("共" + blogDtoList.size() + "篇博客");
                        initRecyclerView();
                    }
                }

                @Override
                public void onFailure(Call<List<BlogDto>> call, Throwable t) {

                }
            });
        }
    }

    private void initRecyclerView() {
        result_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearBlogAdapter linearBlogAdapter = new LinearBlogAdapter(this, blogDtoList);
        result_recyclerView.setAdapter(linearBlogAdapter);
        result_recyclerView.addItemDecoration(new ResultActivity.myDecoration());
    }

    class myDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0, 0, 0, getResources().getDimensionPixelOffset(R.dimen.dividerHeight));
        }

        @Override
        public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.onDraw(c, parent, state);
        }
    }
}
