package com.jacky.launcher.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.jacky.launcher.R;
import com.jacky.launcher.model.AppModel;

import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.MyViewHolder> {
    private Context context;
    private OnItemCallBack onItemCallBack;

    private List<AppModel> appModels;
    private LayoutInflater layoutInflater;

    public AppAdapter(Context context, List<AppModel> mdatas) {
        this.context = context;
        this.appModels = mdatas;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_app, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        AppModel provinceBean = appModels.get(position);
        holder.tv_text.setText(provinceBean.getTitle());
        holder.iv_pic.setImageDrawable(context.getResources().getDrawable(provinceBean.getDrawable()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemCallBack.onItemClick(v, position);
            }
        });
        holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    holder.rl_scale.animate().scaleX(1.05f).scaleY(1.05f).start();
                    holder.iv_pic.setBackground(context.getResources().getDrawable(R.drawable.shape_app_item_selected_bg));
                } else {
                    holder.rl_scale.animate().scaleX(1f).scaleY(1f).start();
                    holder.iv_pic.setBackground(context.getResources().getDrawable(R.drawable.shape_app_item_unselected_bg));
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return appModels.size();
    }

    private class MyFocusChange implements View.OnFocusChangeListener {
        int position;
        MyViewHolder holder;

        public MyFocusChange(int position, MyViewHolder holder) {
            this.position = position;
            this.holder = holder;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                holder.rl_scale.animate().scaleX(1.15f).scaleY(1.15f).start();
            } else {
                holder.rl_scale.animate().scaleX(1f).scaleY(1f).start();
            }
            if (onItemCallBack != null) {
                onItemCallBack.onFocusChange(v, hasFocus, position);
            }
        }
    }

    private class MyClick implements View.OnClickListener {
        int position;

        public MyClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (onItemCallBack != null) {
                onItemCallBack.onItemClick(v, position);
            }
        }
    }

    public void setOnItemCallBack(OnItemCallBack onItemCallBack) {
        this.onItemCallBack = onItemCallBack;
    }

    public interface OnItemCallBack {
        public void onFocusChange(View v, boolean hasFocus, int posiotion);

        public void onItemClick(View v, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_pic;
        public TextView tv_text;
        public RelativeLayout rl_scale;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_pic = (ImageView) itemView.findViewById(R.id.iv_pic);
            tv_text = (TextView) itemView.findViewById(R.id.tv_text);
            rl_scale = (RelativeLayout) itemView.findViewById(R.id.rl_scale);
        }
    }
}
