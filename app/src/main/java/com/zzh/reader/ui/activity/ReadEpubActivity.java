package com.zzh.reader.ui.activity;

import android.content.Intent;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.zzh.reader.R;
import com.zzh.reader.base.BaseReaderNoSwipeActivity;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;

import butterknife.ButterKnife;
import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Date;
import nl.siegmann.epublib.domain.Identifier;
import nl.siegmann.epublib.domain.MediaType;
import nl.siegmann.epublib.domain.Metadata;
import nl.siegmann.epublib.domain.Relator;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubReader;
import nl.siegmann.epublib.service.MediatypeService;

import static java.lang.System.in;
import static java.lang.System.out;

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
        com.zzh.reader.model.Book data = (com.zzh.reader.model.Book) intent.getSerializableExtra(ReadActivity.DATA_BOOK);
        EpubReader reader = new EpubReader();
        try {
            Book book = reader.readEpub(new FileInputStream(data.getBookPath()));
            List<Resource> list = book.getContents();
            if (list != null && !list.isEmpty()) {
                for (Resource resource : list) {
                    Log.d("---文件读取----", resource.toString());
                    Log.d("","----------------------------------------------------------------------------------------------------");
                }
            } else {
                Log.d(TAG, "------initData: 没有获取到目录信息");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void showString(List<String> descriptions) {
        if (descriptions != null) {
            out.println(descriptions);
        } else {
            out.println("----------------------为空------------------");
        }
    }

    private static void showAuthorDetails(List<Author> authors) {
        if (authors != null) {
            for (Author author : authors) {
                out.println("--author-First name: " + author.getFirstname() + "--last name-" + author.getLastname());

                Relator relator = author.getRelator();
                if (relator != null) {
                    out.println("---code-" + relator.getCode() + "---name-" + relator.getName());
                }
            }
        } else {
            out.println("-------list<Author>--为空---");
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
