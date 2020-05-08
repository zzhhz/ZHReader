package com.zzh.reader.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.util.AdError;
import com.zzh.reader.Constants;
import com.zzh.reader.R;
import com.zzh.reader.base.BaseReaderActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZZH on 17/1/23
 *
 * @Date: 17/1/23 13:57
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description: 启动页面
 */
public class WelcomeActivity extends BaseReaderActivity implements SplashADListener {
    private static int SPLASH_DURATION = 3000;

    private ImageView welcomeImageView;//

    private SplashAD mSplashAD;//广告位
    @BindView(R.id.splash_container)
    public FrameLayout mADLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.welcomeactivity;
    }


    @Override
    protected void initView() {
        ButterKnife.bind(this);
        welcomeImageView = (ImageView) findViewById(R.id.wecome_img);
        Glide.with(this).load(R.mipmap.ic_splash)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(welcomeImageView);
        requestPermission(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                },
                REQUEST_CODE_READ_PERMISSION);
    }

    @Override
    protected void initData() {
        mSplashAD = new SplashAD(this, null, Constants.AD_APP_ID, Constants.AD_SPLASH_ID, this, 3000);
        mSplashAD.showAd(mADLayout);
    }

    @Override
    protected void initSetListener() {

    }

    @Override
    protected void handlerMessage(Message msg) {

    }

    private void startAppDelay() {
        welcomeImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                startApp();
            }
        }, SPLASH_DURATION);
    }

    private void startApp() {
        startActivity(new Intent(this, MainUpdateActivity.class));
        finish(); // destroy itself
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseImageViewResouce(welcomeImageView);
    }

    //回收图
    public static void releaseImageViewResouce(ImageView imageView) {
        if (imageView == null) return;
        Drawable drawable = imageView.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

    @Override
    public void onADDismissed() {
        Log.d(TAG, "onADDismissed: ");
    }

    @Override
    public void onNoAD(AdError adError) {
        Log.d(TAG, "onNoAD: " + adError.getErrorMsg() + ",   " + adError.getErrorCode());

    }

    @Override
    public void onADPresent() {
        Log.d(TAG, "onADPresent: ");
    }

    @Override
    public void onADClicked() {
        Log.d(TAG, "onADClicked: ");
    }

    @Override
    public void onADTick(long l) {
        Log.d(TAG, "onADTick: ");
    }

    @Override
    public void onADExposure() {
        Log.d(TAG, "onADExposure: ");
    }

    @Override
    public void onADLoaded(long l) {
        Log.d(TAG, "onADLoaded: " + l);
    }

    @Override
    protected void notifyPermission(int code, boolean flag) {
        if (code == REQUEST_CODE_READ_PERMISSION && flag) {
            startAppDelay();
        }
    }
}
