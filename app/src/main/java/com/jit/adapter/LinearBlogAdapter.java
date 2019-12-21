package com.jit.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jit.R;
import com.jit.avtivity.BlogActivity;
import com.jit.bean.BlogDto;
import com.jit.utils.HttpUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : XZQ
 * date   : 2019/11/30
 * description    :
 */
public class LinearBlogAdapter extends RecyclerView.Adapter<LinearBlogAdapter.LinearViewHolder> {

    private Context context;
    private List<BlogDto> blogList;

    public LinearBlogAdapter(Context context, List<BlogDto> blogList) {
        this.context = context;
        this.blogList = blogList;
    }

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_blogs_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {
        BlogDto blogDto = blogList.get(position);
        holder.tv_title.setText(blogDto.getTitle());
        holder.tv_username.setText(blogDto.getUsername());
        holder.tv_content.setText(blogDto.getContent());
        holder.tv_views.setText(blogDto.getViews().toString());
        holder.tv_prefers.setText(blogDto.getPrefers().toString());
        String path = blogDto.getAvatar().substring(1);
        Glide.with(context).load(HttpUtil.BaseUrl + path).into(holder.imageView);//  .skipMemoryCache(true)禁止默认内存缓存
        Intent intent = new Intent(context, BlogActivity.class);
        intent.putExtra("blogId", blogDto.getId());
        holder.linearLayout.setOnClickListener((v) -> context.startActivity(intent));
    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.blog_prefers)
        TextView tv_prefers;
        @BindView(R.id.blog_views)
        TextView tv_views;
        @BindView(R.id.blog_user_name)
        TextView tv_username;
        @BindView(R.id.line_index)
        LinearLayout linearLayout;
        @BindView(R.id.blog_avatar)
        ImageView imageView;

        private LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
