package com.jacky.launcher.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jacky.launcher.R;
import com.jacky.launcher.adapter.GameAdapter;
import com.jacky.launcher.config.Game;
import com.jacky.launcher.daomanager.GameDaoManager;
import com.jacky.launcher.entity.GameEntity;
import com.jacky.launcher.media.MediaPlayerImp;
import com.jacky.launcher.util.MMKVUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameListActivity extends Activity implements RadioGroup.OnCheckedChangeListener, View.OnFocusChangeListener, View.OnKeyListener, View.OnClickListener {

    private RadioGroup radioGroup;
    private RecyclerView recyclerView;
    private GameAdapter gameAdapter;
    private List<GameEntity> totalGames = new ArrayList<>();
    private List<GameEntity> currentTypeGames = new ArrayList<>();
    private Game type;
    private RadioButton rbtn_total, rbtn_sort, rbtn_last, rbtn_search;
    private MediaPlayerImp mediaPlayerImp;
    private SurfaceView mSurfaceView;
    private boolean isSurfaceViewCreated = false;
    private static final int PLAY_VIDEO_MESSAGE = 8888;
    private LinearLayout llKeyboard;
    private LinearLayout llVideo;
    private EditText etGame;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        initView();
        initClickListen();
    }

    private OnFocusChangeListener alphaFocusChangeListener = new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                v.setAlpha(1.0f);
            } else {
                v.setAlpha(0.6f);
            }
        }
    };

    private void initClickListen() {
        findViewById(R.id.tv_A).setOnClickListener(this);
        findViewById(R.id.tv_B).setOnClickListener(this);
        findViewById(R.id.tv_C).setOnClickListener(this);
        findViewById(R.id.tv_D).setOnClickListener(this);
        findViewById(R.id.tv_E).setOnClickListener(this);
        findViewById(R.id.tv_F).setOnClickListener(this);
        findViewById(R.id.tv_G).setOnClickListener(this);
        findViewById(R.id.tv_H).setOnClickListener(this);
        findViewById(R.id.tv_I).setOnClickListener(this);
        findViewById(R.id.tv_J).setOnClickListener(this);
        findViewById(R.id.tv_K).setOnClickListener(this);
        findViewById(R.id.tv_L).setOnClickListener(this);
        findViewById(R.id.tv_M).setOnClickListener(this);
        findViewById(R.id.tv_N).setOnClickListener(this);
        findViewById(R.id.tv_O).setOnClickListener(this);
        findViewById(R.id.tv_P).setOnClickListener(this);
        findViewById(R.id.tv_Q).setOnClickListener(this);
        findViewById(R.id.tv_R).setOnClickListener(this);
        findViewById(R.id.tv_S).setOnClickListener(this);
        findViewById(R.id.tv_T).setOnClickListener(this);
        findViewById(R.id.tv_U).setOnClickListener(this);
        findViewById(R.id.tv_V).setOnClickListener(this);
        findViewById(R.id.tv_W).setOnClickListener(this);
        findViewById(R.id.tv_X).setOnClickListener(this);
        findViewById(R.id.tv_Y).setOnClickListener(this);
        findViewById(R.id.tv_Z).setOnClickListener(this);
        findViewById(R.id.tv_delete).setOnClickListener(this);

        findViewById(R.id.tv_A).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_B).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_C).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_D).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_E).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_F).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_G).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_H).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_I).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_J).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_K).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_L).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_M).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_N).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_O).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_P).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_Q).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_R).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_S).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_T).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_U).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_V).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_W).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_X).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_Y).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_Z).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_123).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_abc).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_delete).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_douhao).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_juhao).setOnFocusChangeListener(alphaFocusChangeListener);
        findViewById(R.id.tv_clr).setOnFocusChangeListener(alphaFocusChangeListener);
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public boolean handleMessage(Message msg) {
            Log.e(TAG, "handleMessage" + msg.what);
            mHandler.removeMessages(PLAY_VIDEO_MESSAGE);
            switch (msg.what) {
                case PLAY_VIDEO_MESSAGE:
                    if (isSurfaceViewCreated) {
                        mHandler.removeMessages(PLAY_VIDEO_MESSAGE);
                        mediaPlayerImp.play(/*videoUrl*/ "kof98m" + ".avi", true, true);
                    } else {
                        Message message = new Message();
                        message.what = PLAY_VIDEO_MESSAGE;
                        mHandler.removeMessages(PLAY_VIDEO_MESSAGE);
                        mHandler.sendMessageDelayed(message, 1000);
                    }
                    break;
            }
            return false;
        }
    });

    private String videoUrl;
    private SurfaceView surfaceView;
    private ImageView ivVideo;

    private void initView() {
        radioGroup = findViewById(R.id.rg_container);
        recyclerView = findViewById(R.id.recyclerView);
        surfaceView = findViewById(R.id.surfaceView_main_activity);
        ivVideo = findViewById(R.id.iv_video);
        rbtn_total = findViewById(R.id.rbtn_total);
        rbtn_sort = findViewById(R.id.rbtn_sort);
        rbtn_last = findViewById(R.id.rbtn_last);
        rbtn_search = findViewById(R.id.rbtn_search);
        mSurfaceView = findViewById(R.id.surfaceView_main_activity);
        llKeyboard = findViewById(R.id.ll_keyboard);
        etGame = findViewById(R.id.et_game);
        llVideo = findViewById(R.id.ll_video);
        type = (Game) getIntent().getSerializableExtra("type");
        totalGames = GameDaoManager.getInstance().searchAll();
        currentTypeGames = GameDaoManager.getInstance().queryByType(type.getName());
        rbtn_sort.setText(type.getGameName());
        Log.e(TAG, "initView: 1 = " + totalGames.size() + ",2 = " + currentTypeGames.size());
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                isSurfaceViewCreated = true;
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                isSurfaceViewCreated = false;
            }
        });
        gameAdapter = new GameAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        gameAdapter.setNewData(currentTypeGames);
        recyclerView.setAdapter(gameAdapter);
        radioGroup.setOnCheckedChangeListener(this);
        rbtn_search.setOnFocusChangeListener(this);
        rbtn_sort.setOnFocusChangeListener(this);
        rbtn_last.setOnFocusChangeListener(this);
        rbtn_total.setOnFocusChangeListener(this);
        rbtn_search.setOnKeyListener(this);
        rbtn_sort.setOnKeyListener(this);
        rbtn_last.setOnKeyListener(this);
        rbtn_total.setOnKeyListener(this);
        gameAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.e(TAG, "onItemClick: " + ((GameEntity) adapter.getData().get(position)).getChName());
            }
        });
        mediaPlayerImp = new MediaPlayerImp(mSurfaceView, GameListActivity.this);
        mediaPlayerImp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

            }
        });
        mediaPlayerImp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return false;
            }
        });
        mediaPlayerImp.setOnFileErrorListener(new MediaPlayerImp.FileError() {
            @Override
            public void onFileError() {

            }
        });
        etGame.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                gameAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        radioGroup.check(R.id.rbtn_sort);
        gameAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        gameAdapter.setOnItemCallBack(new GameAdapter.OnItemCallBack() {
            @Override
            public void onFocusChange(View v, boolean hasFocus, GameEntity gameEntity) {
                Log.e(TAG, "onFocusChange: " + gameEntity.toString());
                if (hasFocus) {
                    videoUrl = gameEntity.getName();
                    mHandler.sendEmptyMessage(PLAY_VIDEO_MESSAGE);
                    ivVideo.setVisibility(View.GONE);
                    surfaceView.setVisibility(View.VISIBLE);
                    llKeyboard.setVisibility(View.GONE);
                    llVideo.setVisibility(View.VISIBLE);
                } else {
                    mediaPlayerImp.close();
                }
            }

            @Override
            public void onItemClick(View v, GameEntity gameEntity) {
                if (gameEntity == null)
                    return;
                File ROMFile = new File("/mnt/external_sd/" + gameEntity.getType() + "/roms/" + gameEntity.getName() + gameEntity.getGame().getSuffix());
                Toast.makeText(GameListActivity.this, "打开" + ROMFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
                Log.e(TAG, "ROMFile=" + ROMFile.getPath());
                Log.e(TAG, "Boot=" + gameEntity.getGame().getBoot());
                Log.e(TAG, "Name=" + gameEntity.getName());
                if (ROMFile.exists()) {
                    Intent BootIntent = getPackageManager().getLaunchIntentForPackage(gameEntity.getGame().getBoot());
                    if (null != BootIntent) {
                        BootIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        BootIntent.setData(Uri.parse(gameEntity.getName()));
                        BootIntent.putExtra("lang", MMKVUtil.getLanguage()); // 0 is cn, 1 is en
                        startActivity(BootIntent);
                        overridePendingTransition(0, 0);
                    } else {
                        Toast.makeText(GameListActivity.this, "emu not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private static final String TAG = "GameListActivity";

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayerImp.close();
    }

    private int oldCheckedId;

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (oldCheckedId == checkedId)
            return;
        else oldCheckedId = checkedId;
        switch (checkedId) {
            case R.id.rbtn_total:
                if (rbtn_total.isChecked()) {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            // 刷新操作
                            gameAdapter.setNewData(totalGames);
                        }
                    });
                    Log.e(TAG, "onCheckedChanged: rbtn_total");
                    ivVideo.setVisibility(View.VISIBLE);
                    surfaceView.setVisibility(View.GONE);
                    llKeyboard.setVisibility(View.INVISIBLE);
                    llVideo.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rbtn_sort:
                if (rbtn_sort.isChecked()) {
                    Log.e(TAG, "onCheckedChanged: rbtn_sort");
                    ivVideo.setVisibility(View.VISIBLE);
                    surfaceView.setVisibility(View.GONE);
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            gameAdapter.setNewData(currentTypeGames);
                        }
                    });
                    llKeyboard.setVisibility(View.INVISIBLE);
                    llVideo.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rbtn_last:
                if (rbtn_last.isChecked()) {
                    Log.e(TAG, "onCheckedChanged: rbtn_last");
                    llKeyboard.setVisibility(View.INVISIBLE);
                    ivVideo.setVisibility(View.VISIBLE);
                    surfaceView.setVisibility(View.GONE);
                    llVideo.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rbtn_search:
                if (rbtn_search.isChecked()) {
                    Log.e(TAG, "onCheckedChanged: rbtn_search");
                    llKeyboard.setVisibility(View.VISIBLE);
                    llVideo.setVisibility(View.INVISIBLE);
                    ivVideo.setVisibility(View.VISIBLE);
                    surfaceView.setVisibility(View.GONE);
                    llKeyboard.requestFocus();
                    etGame.setText("");
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            gameAdapter.setNewData(totalGames);
                        }
                    });
                }
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        radioGroup.clearCheck();
        switch (v.getId()) {
            case R.id.rbtn_total:
                if (hasFocus) {
                    radioGroup.check(R.id.rbtn_total);
                }
                break;
            case R.id.rbtn_sort:
                if (hasFocus) {
                    radioGroup.check(R.id.rbtn_sort);
                }
                break;
            case R.id.rbtn_last:
                if (hasFocus) {
                    radioGroup.check(R.id.rbtn_last);
                }
                break;
            case R.id.rbtn_search:
                if (hasFocus) {
                    radioGroup.check(R.id.rbtn_search);
                }
                break;
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_DOWN) {
            return false;
        }
        switch (v.getId()) {
            case R.id.rbtn_total:
                Log.e(TAG, "onKey: rbtn_total");
                if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                    rbtn_sort.requestFocus();
                    return true;
                } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                    return true;

                } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                    return true;

                } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                    recyclerView.requestFocus();
//                    radioGroup.clearCheck();
                    return true;
                }
                return false;
            case R.id.rbtn_sort:
                if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                    rbtn_last.requestFocus();
                    return true;
                } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                    rbtn_total.requestFocus();
                    return true;
                } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {

                    return true;
                } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                    recyclerView.requestFocus();
//                    radioGroup.clearCheck();
                    return true;
                }
                Log.e(TAG, "onKey: rbtn_sort");
                return false;
            case R.id.rbtn_last:
                if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                    rbtn_search.requestFocus();
                    return true;
                } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                    rbtn_sort.requestFocus();
                    return true;
                } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {

                    return true;
                } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                    recyclerView.requestFocus();
//                    radioGroup.clearCheck();
                    return true;
                }
                Log.e(TAG, "onKey: rbtn_last");
                return false;
            case R.id.rbtn_search:
                if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                    return true;
                } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                    rbtn_last.requestFocus();
                    return true;
                } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                    return true;

                } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                    recyclerView.requestFocus();
//                    radioGroup.clearCheck();
                    return true;
                }
                Log.e(TAG, "onKey: rbtn_search");
                return false;
        }
        return false;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        Log.e(TAG, "onClick: 1" + etGame.getText().toString());
        switch (v.getId()) {
            case R.id.tv_A:
                etGame.setText(etGame.getText().toString() + "A");
                break;
            case R.id.tv_B:
                etGame.setText(etGame.getText().toString() + "B");
                break;
            case R.id.tv_C:
                etGame.setText(etGame.getText().toString() + "C");
                break;
            case R.id.tv_D:
                etGame.setText(etGame.getText().toString() + "D");
                break;
            case R.id.tv_E:
                etGame.setText(etGame.getText().toString() + "E");
                break;
            case R.id.tv_F:
                etGame.setText(etGame.getText().toString() + "F");
                break;
            case R.id.tv_G:
                etGame.setText(etGame.getText().toString() + "G");
                break;
            case R.id.tv_H:
                etGame.setText(etGame.getText().toString() + "H");
                break;
            case R.id.tv_I:
                etGame.setText(etGame.getText().toString() + "I");
                break;
            case R.id.tv_J:
                etGame.setText(etGame.getText().toString() + "J");
                break;
            case R.id.tv_K:
                etGame.setText(etGame.getText().toString() + "K");
                break;
            case R.id.tv_L:
                etGame.setText(etGame.getText().toString() + "L");
                break;
            case R.id.tv_M:
                etGame.setText(etGame.getText().toString() + "M");
                break;
            case R.id.tv_N:
                etGame.setText(etGame.getText().toString() + "N");
                break;
            case R.id.tv_O:
                etGame.setText(etGame.getText().toString() + "O");
                break;
            case R.id.tv_P:
                etGame.setText(etGame.getText().toString() + "P");
                break;
            case R.id.tv_Q:
                etGame.setText(etGame.getText().toString() + "Q");
                break;
            case R.id.tv_R:
                etGame.setText(etGame.getText().toString() + "R");
                break;
            case R.id.tv_S:
                etGame.setText(etGame.getText().toString() + "S");
                break;
            case R.id.tv_T:
                etGame.setText(etGame.getText().toString() + "T");
                break;
            case R.id.tv_U:
                etGame.setText(etGame.getText().toString() + "U");
                break;
            case R.id.tv_V:
                etGame.setText(etGame.getText().toString() + "V");
                break;
            case R.id.tv_W:
                etGame.setText(etGame.getText().toString() + "W");
                break;
            case R.id.tv_X:
                etGame.setText(etGame.getText().toString() + "X");
                break;
            case R.id.tv_Y:
                etGame.setText(etGame.getText().toString() + "Y");
                break;
            case R.id.tv_Z:
                etGame.setText(etGame.getText().toString() + "Z");
                break;
            case R.id.tv_delete:
                if (!TextUtils.isEmpty(etGame.getText().toString()))
                    if (etGame.getText().toString().length() > 1) {
                        etGame.setText(etGame.getText().toString().substring(0, etGame.getText().toString().length() - 1));
                    } else if (etGame.getText().toString().length() == 1) {
                        etGame.setText("");
                    }
                break;
        }
        Log.e(TAG, "onClick: 2" + etGame.getText().toString());
    }
}
