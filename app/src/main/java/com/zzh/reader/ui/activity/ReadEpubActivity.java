package com.zzh.reader.ui.activity;

import android.os.Message;
import android.view.View;

import com.zzh.reader.R;
import com.zzh.reader.base.BaseReaderNoSwipeActivity;

import butterknife.ButterKnife;

/**
 * Created by user on 2017/9/30.
 *
 * @Date: 2017/9/30
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description: 阅读
 */
public class ReadEpubActivity extends BaseReaderNoSwipeActivity {
    @Override
    protected int setLayoutId() {
        return R.layout.activity_epub_read;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

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

    @Override
    public void onClick(View view) {

    }
}
