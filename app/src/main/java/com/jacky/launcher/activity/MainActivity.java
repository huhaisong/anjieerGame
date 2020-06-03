
package com.jacky.launcher.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.jacky.launcher.R;
import com.jacky.launcher.adapter.AppAdapter;
import com.jacky.launcher.config.Game;
import com.jacky.launcher.model.AppModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    private RecyclerView mGridView;
    private String[] appName = new String[]{"主题", "视频", "音乐", "图库", "设置", "文件", "搜索"};
    private int[] appDrawable = new int[]{R.mipmap.app_8, R.mipmap.app_1, R.mipmap.app_2, R.mipmap.app_6, R.mipmap.app_5, R.mipmap.app_6, R.mipmap.app_7};

    private AppAdapter appAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<AppModel> appModels = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            AppModel provinceBean = new AppModel();
            provinceBean.setTitle(appName[i]);
            provinceBean.setDrawable(appDrawable[i]);
            appModels.add(provinceBean);
        }
        appAdapter = new AppAdapter(getBaseContext(), appModels);
        mGridView = findViewById(R.id.recyclerView);
        mGridView.setAdapter(appAdapter);
        appAdapter.setOnItemCallBack(new AppAdapter.OnItemCallBack() {
            @Override
            public void onFocusChange(View v, boolean hasFocus, int posiotion) {

            }

            @Override
            public void onItemClick(View v, int position) {
                Intent intent;
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(MainActivity.this, SettingActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        startActivityForResult(intent, 1);
                        break;
                    case 6:
                        break;
                }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mGridView.setLayoutManager(new GridLayoutManager(this, 7));
        findViewById(R.id.iv_pic1).setOnClickListener(this);
        findViewById(R.id.iv_pic2).setOnClickListener(this);
        findViewById(R.id.iv_pic3).setOnClickListener(this);
        findViewById(R.id.iv_pic4).setOnClickListener(this);
        findViewById(R.id.iv_pic5).setOnClickListener(this);
        findViewById(R.id.iv_pic6).setOnClickListener(this);
        findViewById(R.id.iv_pic7).setOnClickListener(this);
    }

    private static final String TAG = "MainActivity";

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, GameListActivity.class);
        switch (v.getId()) {
            case R.id.iv_pic1:
                Log.e(TAG, "onClick: iv_pic1" + v.getId());
                intent.putExtra("type", Game.VARC);
                startActivity(intent);
                break;
            case R.id.iv_pic2:
                intent.putExtra("type", Game.VN64);
                startActivity(intent);
                Log.e(TAG, "onClick: iv_pic2" + v.getId());
                break;
            case R.id.iv_pic3:
                intent.putExtra("type", Game.VNES);
                startActivity(intent);
                Log.e(TAG, "onClick: iv_pic3" + v.getId());
                break;
            case R.id.iv_pic4:
                intent.putExtra("type", Game.VPSP);
                startActivity(intent);
                Log.e(TAG, "onClick: iv_pic4" + v.getId());
                break;
            case R.id.iv_pic5:
                intent.putExtra("type", Game.VGEN);
                startActivity(intent);
                Log.e(TAG, "onClick: iv_pic5" + v.getId());
                break;
            case R.id.iv_pic6:
                intent.putExtra("type", Game.VSFC);
                startActivity(intent);
                Log.e(TAG, "onClick: iv_pic6" + v.getId());
                break;
            case R.id.iv_pic7:
                intent.putExtra("type", Game.VGBA);
                startActivity(intent);
                Log.e(TAG, "onClick: iv_pic7" + v.getId());
                break;
        }
    }
}
