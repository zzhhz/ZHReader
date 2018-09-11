package com.zzh.reader.ui.activity;

import android.content.Intent;
import android.os.Message;
import android.view.View;

import com.zzh.reader.R;
import com.zzh.reader.base.BaseReaderNoSwipeActivity;
import com.zzh.reader.model.Book;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.ButterKnife;
import nl.siegmann.epublib.domain.Metadata;
import nl.siegmann.epublib.epub.EpubReader;

/**
 * Created by user on 2017/9/30.
 *
 * @Date: 2017/9/30
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @author: zzh
 * @Description: 阅读 Epub格式的文件
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
        Intent intent = getIntent();
        Book book = (Book) intent.getSerializableExtra(DATA_BOOK);
        String bookPath = book.getBookPath();
        EpubReader reader = new EpubReader();
        InputStream in = null;
        try {
            in = new FileInputStream(bookPath);
            nl.siegmann.epublib.domain.Book readEpub = reader.readEpub(in);
            Metadata metadata = readEpub.getMetadata();
            String title = metadata.getTitles().get(0);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
