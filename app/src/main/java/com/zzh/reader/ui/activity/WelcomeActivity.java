package com.zzh.reader.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zzh.reader.MainActivity;
import com.zzh.reader.R;
import com.zzh.reader.base.BaseReaderActivity;

/**
 * Created by ZZH on 17/1/23
 *
 * @Date: 17/1/23 13:57
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description: 启动页面
 */
public class WelcomeActivity extends BaseReaderActivity {
    private static int SPLASH_DURATION = 1500;
    private ImageView welcomeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        welcomeImageView = (ImageView) findViewById(R.id.wecome_img);
        Glide.with(this).load(R.mipmap.ic_splash).crossFade(SPLASH_DURATION)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(welcomeImageView);
        startAppDelay();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.welcomeactivity;
    }


    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

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
        startActivity(new Intent(this, MainActivity.class));
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
}
