package com.jacky.launcher.media;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jacky.launcher.util.AssetsUtil;

import java.io.IOException;
import java.util.List;

public class MediaPlayerImp implements IMediaPlayer, MediaPlayer.OnPreparedListener {

    private static final int STATUS_IDLE = 0;
    private static final int STATUS_PREPARING = 1;
    private static final int STATUS_PREPARED = 2;
    private static final int STATUS_STARTED = 3;
    private static final int STATUS_PAUSED = 4;
    private static final int STATUS_STOPPED = 5;
    private int mStatus = STATUS_IDLE;

    private SurfaceView mSurfaceView;
    private Context mContext;
    private MediaPlayer mMediaPlayer;
    private MediaPlayer.OnCompletionListener onCompletionListener;
    private MediaPlayer.OnErrorListener onErrorListener;
    private boolean isFullScreen = false;
    private int screenHeight = 0;
    private int screenWidth = 0;

    public MediaPlayerImp(SurfaceView mSurfaceView, Context mContext) {
        this.mSurfaceView = mSurfaceView;
        this.mContext = mContext;
        mStatus = STATUS_IDLE;
    }

    public interface FileError {
        void onFileError();
    }


    public void setOnCompletionListener(MediaPlayer.OnCompletionListener onCompletionListener) {
        this.onCompletionListener = onCompletionListener;
    }

    public void setOnErrorListener(MediaPlayer.OnErrorListener onErrorListener) {
        this.onErrorListener = onErrorListener;
    }

    private FileError onfileError;

    public void setOnFileErrorListener(FileError fileErrorListener) {
        this.onfileError = fileErrorListener;
    }

    public void setScreenHeightAndWight(int height, int width) {
        screenHeight = 1080;
        screenWidth = 1920;
        WindowManager w = ((Activity) mContext).getWindowManager();
        Display d = w.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);
        int widthPixels = 0;
        int heightPixels = 0;
        if (Build.VERSION.SDK_INT >= 17) {
            try {
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(d, realSize);
                widthPixels = realSize.x;
                heightPixels = realSize.y;
            } catch (Exception ignored) {

            }
            screenWidth = widthPixels;
            screenHeight = heightPixels;
        }
    }

    public boolean isPlaying() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.isPlaying();
        }
        return false;
    }

    public void updateRatio() {
        isFullScreen = true;
        if (!isFullScreen) {
            mMediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                @Override
                public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                    if (width == 0 || height == 0)
                        return;
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.MATCH_PARENT);
                    int displayWidth, displayHeight;
                    if (width * screenHeight > height * screenWidth) {
                        displayWidth = screenWidth;
                        displayHeight = height * screenWidth / width;
                    } else {
                        displayHeight = screenHeight;
                        displayWidth = screenHeight * width / height;
                    }

                    if (displayHeight > screenHeight) {
                        displayHeight = screenHeight;
                    }

                    if (displayWidth > screenWidth) {
                        displayWidth = screenWidth;
                    }
                    int marginw = (screenWidth - displayWidth) / 2;
                    int marginh = (screenHeight - displayHeight) / 2;
                    lp.setMargins(marginw, marginh, marginw, marginh);
                    mSurfaceView.setLayoutParams(lp);
                }
            });
        } else {
            mMediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                @Override
                public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                    if (width == 0 || height == 0)
                        return;
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(15, 20, 15, 15);
                    mSurfaceView.setLayoutParams(lp);
                }
            });
        }
    }

    /**
     * @param url  资源地址
     * @param isFD 是否app自带资源
     */
    @Override
    public void play(String url, boolean isFD, boolean isLoop) {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.release();
        }
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnErrorListener(onErrorListener);
        mMediaPlayer.setOnCompletionListener(onCompletionListener);
        mMediaPlayer.reset();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(this);
        updateRatio();
        if (mSurfaceView != null) {
            mMediaPlayer.setDisplay(mSurfaceView.getHolder());
        }
        try {
            Log.e(TAG, "play: " + url.toString());
            if (isFD) {
                AssetFileDescriptor fd = mContext.getAssets().openFd("video/" + url);
                mMediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            } else {
                mMediaPlayer.setDataSource(url);
            }
            mMediaPlayer.prepare();
            Log.e(TAG, "play: " + url.toString());
        } catch (IOException e) {
            e.printStackTrace();
            if (onfileError != null) {
                onfileError.onFileError();
            }
        }
        mStatus = STATUS_PREPARING;
        if (isLoop) {
            mMediaPlayer.setLooping(true);
        }
    }

    @Override
    public void stop() {
        if (mMediaPlayer == null) return;
        mMediaPlayer.stop();
        mStatus = STATUS_STOPPED;
    }

    private static final String TAG = "MediaPlayerImp";

    @Override
    public void close() {
        stop();
        if (mMediaPlayer != null) {
            mMediaPlayer.setDisplay(null);
            mMediaPlayer.release();
        }
        mMediaPlayer = null;
    }

    @Override
    public void pause() {
        if (mMediaPlayer == null) return;
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mStatus = STATUS_PAUSED;
        }
    }

    @Override
    public void unPause() {
        if (mMediaPlayer == null) return;
        if (mStatus == STATUS_PREPARED || mStatus == STATUS_PAUSED) {
            mMediaPlayer.start();
            mStatus = STATUS_STARTED;
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if (mMediaPlayer == null) return;
        mStatus = STATUS_PREPARED;
        mMediaPlayer.start();
        Log.e(TAG, "onPrepared: " + mMediaPlayer.getDuration());
        mStatus = STATUS_STARTED;
    }
}
