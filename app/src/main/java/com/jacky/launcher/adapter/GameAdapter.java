package com.jacky.launcher.adapter;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jacky.launcher.R;
import com.jacky.launcher.entity.GameEntity;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GameAdapter extends BaseQuickAdapter<GameEntity, BaseViewHolder> implements Filterable {

    public GameAdapter() {
        super(R.layout.item_game);
    }

    @Override
    protected void convert(@NotNull final BaseViewHolder baseViewHolder, final GameEntity gameEntity) {
        baseViewHolder.setText(R.id.tv_title, gameEntity.getChName());
        baseViewHolder.setText(R.id.tv_number, baseViewHolder.getAdapterPosition() + "");
        final TextView title = baseViewHolder.getView(R.id.tv_title);
        baseViewHolder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (onItemCallBack != null) {
                    onItemCallBack.onFocusChange(v, hasFocus, gameEntity);
                }
                if (hasFocus) {
                    v.setAlpha(1.0f);
                    title.setTextColor(mContext.getResources().getColor(R.color.red));
                } else {
                    v.setAlpha(0.6f);
                    title.setTextColor(mContext.getResources().getColor(R.color.black));
                }
            }
        });
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemCallBack != null)
                    onItemCallBack.onItemClick(v, gameEntity);
            }
        });
        baseViewHolder.itemView.setFocusable(true);
        baseViewHolder.itemView.setClickable(true);
    }

    private GameAdapter.OnItemCallBack onItemCallBack;

    public void setOnItemCallBack(GameAdapter.OnItemCallBack onItemCallBack) {
        this.onItemCallBack = onItemCallBack;
    }

    public interface OnItemCallBack {
        public void onFocusChange(View v, boolean hasFocus, GameEntity gameEntity);

        public void onItemClick(View v, GameEntity gameEntity);
    }


    @Override
    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new Filter() {
                //执行过滤操作
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    FilterResults filterResults = new FilterResults();
                    Log.e(TAG, "performFiltering: " + charString);
                    if (TextUtils.isEmpty(charString)) {
                        //没有过滤的内容，则使用源数据
                        filterResults.values = originDatas;
                        return filterResults;
                    } else {
                        List<GameEntity> filteredList = new ArrayList<>();
                        for (GameEntity item : originDatas) {
                            //这里根据需求，添加匹配规则
                            if (TextUtils.isEmpty(item.getChName()))
                                continue;
                            if (item.getPinyin().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(item);
                            }
                        }
                        filterResults.values = filteredList;
                    }
                    return filterResults;
                }

                //把过滤后的值返回出来
                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    isFilter = true;
                    setNewData((List<GameEntity>) filterResults.values);
                    isFilter = false;
                }
            };
        }
        return myFilter;
    }


    private Filter myFilter;
    private boolean isFilter = false;

    private List<GameEntity> originDatas;

    @Override
    public void setNewData(@Nullable List<GameEntity> data) {
        super.setNewData(data);

        if (!isFilter) {
            originDatas = data;
        }
    }
}
