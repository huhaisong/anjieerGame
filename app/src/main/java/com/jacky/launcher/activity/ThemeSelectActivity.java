package com.jacky.launcher.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jacky.launcher.R;
import com.jacky.launcher.adapter.GridAdapter;
import com.jacky.launcher.entity.ThemeEntity;

import java.util.ArrayList;
import java.util.List;

public class ThemeSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_select);
        List<ThemeEntity> data = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            if (i == 0) {
                data.add(new ThemeEntity(R.mipmap.bj, true));
            } else {
                data.add(new ThemeEntity(R.mipmap.bj, false));
            }
        }
        final GridAdapter gridAdapter = new GridAdapter(R.layout.item_theme_pic, data);
        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new GridLayoutManager(this, 3));
        rv.setAdapter(gridAdapter);
        gridAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                gridAdapter.setSelected(position);
            }
        });
    }
}
