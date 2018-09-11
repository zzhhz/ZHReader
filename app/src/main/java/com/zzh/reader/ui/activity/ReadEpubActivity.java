package com.zzh.reader.ui.activity;

import android.content.Intent;
import android.os.Message;
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
import nl.siegmann.epublib.domain.Metadata;
import nl.siegmann.epublib.domain.Relator;
import nl.siegmann.epublib.epub.EpubReader;

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
        String bookpath = intent.getStringExtra("bookpath");

        EpubReader reader = new EpubReader();
        try {
            Book book = reader.readEpub(new FileInputStream(bookpath));
            String title = book.getTitle();
            out.println(title);

            Metadata metadata = book.getMetadata();
            List<Author> authors = metadata.getAuthors();
            showAuthorDetails(authors);
            out.println("------------------------------分割线----------------------------------------");
            List<Author> contributors = metadata.getContributors();
            showAuthorDetails(authors);
            out.println("------------------------------分割线----------------------------------------");
            List<Date> dates = metadata.getDates();
            if (dates != null) {
                for (Date date : dates) {
                    out.println("--event-" + date.getEvent() + "---value--" + date.getValue());
                }
            } else {
                out.println("-------list<Data> 为空--------------------------------");
            }
            List<String> descriptions = metadata.getDescriptions();
            showString(descriptions);
            out.println("------------------------------分割线----------------------------------------");
            String firstTitle = metadata.getFirstTitle();
            out.println("-----first title----" + firstTitle);
            String format = metadata.getFormat();
            out.println("-----format----" + format);
            List<Identifier> identifiers = metadata.getIdentifiers();
            if (identifiers != null) {
                for (Identifier ids :
                        identifiers) {
                    out.println("-----scheme--" + ids.getScheme() + "--value-" + ids.getValue());
                }
            } else {
                out.println("-------List<Identifier> 为空--------------------------------");
            }
            out.println("------------------------------分割线----------------------------------------");
            String language = metadata.getLanguage();
            out.println("-----language---" + language);
            Map<QName, String> otherProperties = metadata.getOtherProperties();
            if (otherProperties != null){
                Set<QName> qNames = otherProperties.keySet();
                if (qNames != null){
                    for (QName name : qNames) {
                        out.println("------name-"+name+"------value--"+otherProperties.get(name));
                    }
                } else {
                    out.println("---------------qName为空--------------------------");
                }
            } else {
                out.println("-------otherProperties 为空--------------------------------");
            }


            List<String> publishers = metadata.getPublishers();
            showString(publishers);
            out.println("------------------------------分割线----------------------------------------");
            List<String> rights = metadata.getRights();
            showString(rights);
            out.println("------------------------------分割线----------------------------------------");
            List<String> subjects = metadata.getSubjects();
            showString(subjects);
            out.println("------------------------------分割线----------------------------------------");
            List<String> titles = metadata.getTitles();
            showString(titles);
            out.println("------------------------------分割线----------------------------------------");
            List<String> types = metadata.getTypes();
            showString(types);
            out.println("------------------------------分割线----------------------------------------");


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
