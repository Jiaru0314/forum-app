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
import com.jit.avtivity.ResultActivity;
import com.jit.bean.User;
import com.jit.utils.HttpUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * author : XZQ
 * date   : 2019/11/30
 * description    :
 */
public class LinearUserAdapter extends RecyclerView.Adapter<LinearUserAdapter.LinearViewHolder> {

    private Context context;
    private List<User> userList;

    public LinearUserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_users_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {
        User user = userList.get(position);
        holder.tv_title.setText(user.getUsername());
        holder.tv_content.setText(user.getDescription());
        String path = user.getAvatar().substring(1);
        Glide.with(context).load(HttpUtil.BaseUrl + path).into(holder.imageView);
        Intent intent = new Intent(context, ResultActivity.class);
        intent.putExtra("UserID", user.getId());
        holder.linearLayout.setOnClickListener((v) -> context.startActivity(intent));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.line_index)
        LinearLayout linearLayout;
        @BindView(R.id.users_avatar)
        ImageView imageView;

        private LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
