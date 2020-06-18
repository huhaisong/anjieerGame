package com.jacky.launcher.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.jacky.launcher.R;
import com.jacky.launcher.broadcast.DialogBroadcastReceiver;
import com.jacky.launcher.util.AppUtil;
import com.jacky.launcher.util.LightUtil;
import com.jacky.launcher.util.MMKVUtil;
import com.jacky.launcher.util.WifiUtil;
import com.jacky.launcher.util.runtimepermissions.PermissionsManager;
import com.jacky.launcher.util.runtimepermissions.PermissionsResultAction;

public class SettingActivity extends AppCompatActivity {
    private static final String[] name = {"汉语", "English"};
    private Spinner languageSpinner, themeSpinner;
    private ArrayAdapter languageAdapter, themeAdapter;
    private Switch wifiSwitch, bluetoothSwitch, voiceSwitch;
    private SeekBar lightSeekBar;
    private RadioGroup radioGroup;

    private WifiUtil mWifiUtil;
    private BluetoothAdapter mBluetoothAdapter;
    private DialogBroadcastReceiver dialogBroadcastReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mWifiUtil = new WifiUtil();
       /* PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
                Toast.makeText(SettingActivity.this, "所有的权限被拒绝", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                Toast.makeText(SettingActivity.this, "权限 " + permission + "被拒绝", Toast.LENGTH_SHORT).show();
                finish();
            }
        });*/
        initView();
        initListener();
    }


    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DialogBroadcastReceiver.DISMISS_DIALOG);
        intentFilter.addAction(DialogBroadcastReceiver.SHOW_DIALOG);
        dialogBroadcastReceiver = new DialogBroadcastReceiver();
        registerReceiver(dialogBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(dialogBroadcastReceiver);
    }

    private void initListener() {

        findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        themeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (mWifiUtil.checkState() == WifiManager.WIFI_STATE_ENABLED) {
            wifiSwitch.setChecked(true);
        } else if (mWifiUtil.checkState() == WifiManager.WIFI_STATE_DISABLED) {
            wifiSwitch.setChecked(false);
        }
        wifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mWifiUtil.openWifi();
                        }
                    }).start();
                } else {
                    mWifiUtil.closeWifi();
                }
            }
        });

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter.isEnabled()) {
            bluetoothSwitch.setChecked(true);
        } else {
            bluetoothSwitch.setChecked(false);
        }

        bluetoothSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!mBluetoothAdapter.isEnabled()) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mBluetoothAdapter.enable();
                            }
                        }).start();
                    }
                } else {
                    if (mBluetoothAdapter.isEnabled())
                        mBluetoothAdapter.disable();
                }
            }
        });

        if (MMKVUtil.getVoiceState()) {
            voiceSwitch.setChecked(true);
        } else {
            voiceSwitch.setChecked(false);
        }

        voiceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MMKVUtil.setVoiceSetting(isChecked);
            }
        });

        lightSeekBar.setProgress(MMKVUtil.getLightState());
        lightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                LightUtil.setBrightness(progress, SettingActivity.this);
                MMKVUtil.setLightSetting(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                LightUtil.setBrightness(seekBar.getProgress(), SettingActivity.this);
                MMKVUtil.setLightSetting(seekBar.getProgress());
            }
        });

        switch (MMKVUtil.getLockState()) {
            case 10:
                radioGroup.check(R.id.rb1);
                break;
            case 20:
                radioGroup.check(R.id.rb2);
                break;
            case 30:
                radioGroup.check(R.id.rb3);
                break;
            case 60:
                radioGroup.check(R.id.rb4);
                break;
            case 0:
                radioGroup.check(R.id.rb5);
                break;
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb1:
                        MMKVUtil.setLockSetting(10);
                        break;
                    case R.id.rb2:
                        MMKVUtil.setLockSetting(20);
                        break;
                    case R.id.rb3:
                        MMKVUtil.setLockSetting(30);
                        break;
                    case R.id.rb4:
                        MMKVUtil.setLockSetting(60);
                        break;
                    case R.id.rb5:
                        MMKVUtil.setLockSetting(0);
                        break;
                }
            }
        });
    }

    private void initView() {
        voiceSwitch = findViewById(R.id.switch_voice);
        wifiSwitch = findViewById(R.id.switch_wifi);
        bluetoothSwitch = findViewById(R.id.switch_bluetooth);
        languageSpinner = findViewById(R.id.spinner_language);
        languageAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, name);
        languageAdapter.setDropDownViewResource(R.layout.dropdown_style);
        languageSpinner.setAdapter(languageAdapter);
        themeSpinner = findViewById(R.id.spinner_theme);
        themeAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, name);
        themeAdapter.setDropDownViewResource(R.layout.dropdown_style);
        themeSpinner.setAdapter(languageAdapter);
        lightSeekBar = findViewById(R.id.sb_light);
        radioGroup = findViewById(R.id.rgroup);
        TextView appVersion = findViewById(R.id.tv_app_version);
        appVersion.setText(AppUtil.getAPPVersion(SettingActivity.this));
        String phoneInfo = ", VERSION.RELEASE: " + android.os.Build.VERSION.RELEASE;
        phoneInfo += ", VERSION.CODENAME: " + android.os.Build.VERSION.CODENAME;
        Log.e(TAG, "initView: " + phoneInfo);
    }

    private static final String TAG = "SettingActivity";
}
