package com.jit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jit.R;
import com.jit.bean.Comment;
import com.jit.utils.HttpUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : XZQ
 * date   : 2019/12/4
 * description    :
 */

public class LinearCommentAdapter extends RecyclerView.Adapter<LinearCommentAdapter.LinearViewHolder> {

    private Context context;
    private List<Comment> commentList;

    public LinearCommentAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearCommentAdapter.LinearViewHolder(LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        String path = comment.getAvatar().substring(1);
        Glide.with(context).load(HttpUtil.BaseUrl + path).into(holder.comment_avatar);//  .skipMemoryCache(true)禁止默认内存缓存
        holder.comment_name.setText(comment.getUsername());
        holder.comment_createTime.setText(comment.getCreateTime().toString());
        holder.comment_content.setText(comment.getContent());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.comment_avatar)
        ImageView comment_avatar;
        @BindView(R.id.comment_name)
        TextView comment_name;
        @BindView(R.id.comment_createTime)
        TextView comment_createTime;
        @BindView(R.id.comment_content)
        TextView comment_content;

        private LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
