package com.jit.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jit.R;
import com.jit.adapter.LinearBlogAdapter;
import com.jit.bean.BlogDto;
import com.jit.retrofit.ApiService;
import com.jit.retrofit.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author : XZQ
 * date   : 2019/11/30
 * description    :
 */
public class RecommendFragment extends Fragment {

    @BindView(R.id.xrv)
    XRecyclerView meyerView;
    private LinearBlogAdapter linearAdapter;
    private List<BlogDto> blogList = new ArrayList<>();
    private final String Tag = "RecommendFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.xrecycler_view, container, false);
        ButterKnife.bind(this, view);
        initData();
        initRecyclerView();
        return view;
    }


    private void initData() {
        ApiService apiService = RetrofitManager.getInstance().getService();
        Call<List<BlogDto>> callBlogList = apiService.getRandList();
        callBlogList.enqueue(new Callback<List<BlogDto>>() {
            @Override
            public void onResponse(Call<List<BlogDto>> call, Response<List<BlogDto>> response) {
                if (response.body() != null) {
                    blogList.addAll(response.body());//Collections.addAll()
                }
            }

            @Override
            public void onFailure(Call<List<BlogDto>> call, Throwable t) {
                System.out.println(Tag + "请求失败");
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initRecyclerView() {
        meyerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        linearAdapter = new LinearBlogAdapter(getActivity(), blogList);
        meyerView.setAdapter(linearAdapter);
        meyerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        meyerView.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
        meyerView.setPullRefreshEnabled(true);//设置可以上拉
        meyerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(() -> {
                    blogList.clear();
                    initData();
                    linearAdapter.notifyDataSetChanged();
                    meyerView.refreshComplete();
                }, 1000);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(() -> {
                    blogList.clear();
                    initData();
                    //刷新完成
                    linearAdapter.notifyDataSetChanged();
                    meyerView.loadMoreComplete();
                }, 1000);
            }
        });
    }
}
