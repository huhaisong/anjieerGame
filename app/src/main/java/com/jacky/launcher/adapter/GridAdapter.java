package com.jacky.launcher.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jacky.launcher.R;
import com.jacky.launcher.entity.GameEntity;
import com.jacky.launcher.entity.ThemeEntity;

import java.util.List;


public class GridAdapter extends BaseQuickAdapter<ThemeEntity, BaseViewHolder> {

    public GridAdapter(int layoutResId, @Nullable List<ThemeEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ThemeEntity item) {
        ImageView imageView = helper.getView(R.id.item_img);
        imageView.setImageResource(item.getRes());
        helper.addOnClickListener(R.id.item_img);
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemCallBack != null)
                    onItemCallBack.onItemClick(v, item);
            }
        });
        ImageView selected = helper.getView(R.id.iv_selected);

        if (item.isSelected()) {
            selected.setVisibility(View.VISIBLE);
            helper.itemView.requestFocus();
        } else {
            selected.setVisibility(View.GONE);
        }

        helper.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (onItemCallBack != null) {
                    onItemCallBack.onFocusChange(v, hasFocus, item);
                }

                if (hasFocus) {
                    v.setAlpha(1.0f);
//                    title.setTextColor(mContext.getResources().getColor(R.color.red));
                } else {
                    v.setAlpha(0.7f);
//                    title.setTextColor(mContext.getResources().getColor(R.color.black));
                }
            }
        });
    }

    private GridAdapter.OnItemCallBack onItemCallBack;

    public void setSelected(int position) {
        for (int i = 0; i < getData().size(); i++) {
            getData().get(i).setSelected(false);
        }
        getData().get(position).setSelected(true);
        notifyDataSetChanged();
    }

    public void setOnItemCallBack(GridAdapter.OnItemCallBack onItemCallBack) {
        this.onItemCallBack = onItemCallBack;
    }

    public interface OnItemCallBack {
        public void onFocusChange(View v, boolean hasFocus, ThemeEntity integer);

        public void onItemClick(View v, ThemeEntity integer);
    }
}
