package com.zzh.reader.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.zzh.reader.R;
import com.zzh.reader.adapter.AppBarViewPagerAdapter;
import com.zzh.reader.base.BaseReaderNoSwipeActivity;
import com.zzh.reader.fragment.BookMarkFragment;
import com.zzh.reader.fragment.CatalogueFragment;
import com.zzh.reader.fragment.NotesFragment;
import com.zzh.reader.model.Catalogue;
import com.zzh.reader.util.BookUtils;
import com.zzh.reader.util.GreenDaoManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import butterknife.BindView;
import butterknife.ButterKnife;
import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Relator;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.domain.TableOfContents;
import nl.siegmann.epublib.epub.EpubReader;

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

    @BindView(R.id.navigationView)
    protected NavigationView mNoteView;
    @BindView(R.id.read_drawer_layout)
    protected DrawerLayout mDrawerLayout;

    // 目录相关的信息
    protected ViewPager mViewPager;
    protected AppBarViewPagerAdapter mAdapter;
    protected TabLayout mTabLayout;
    protected BookMarkFragment mArkFragment;
    protected CatalogueFragment mCatalogFragment;
    protected NotesFragment mNotesFragment;
    com.zzh.reader.model.Book data;
    @Override
    protected int setLayoutId() {
        //Executors.newCachedThreadPool();
        //new ThreadPoolExecutor()
        return R.layout.activity_epub_read;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//保存屏幕常亮
        hideSystemUI();//隐藏
        mViewPager = mNoteView.findViewById(R.id.viewPager);
        mTabLayout = mNoteView.findViewById(R.id.tabLayout);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        data = (com.zzh.reader.model.Book) intent.getSerializableExtra(ReadActivity.DATA_BOOK);
        EpubReader reader = new EpubReader();
        try {
            final Book book = reader.readEpub(new FileInputStream(data.getBookPath()));
            if (!data.isCatalogue()) {
                // TODO 需要优化使用线程池
                new Thread() {
                    @Override
                    public void run() {
                        List<Catalogue> list = BookUtils.getBookCatalogues(data.getBookPath());
                        GreenDaoManager.getCatalogueDao().insertInTx(list);
                        data.setIsCatalogue(true);
                        GreenDaoManager.getBookDao().update(data);
                    }
                }.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        initCatalogue();
    }

    private void initCatalogue() {
        List<Fragment> fragmentList = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putString(CatalogueFragment.ARGUMENT, data.getBookPath());
        bundle.putString(BookMarkFragment.ARGUMENT, data.getBookPath());
        mArkFragment = new BookMarkFragment();
        mCatalogFragment = new CatalogueFragment();
        mNotesFragment = new NotesFragment();

        mArkFragment.setArguments(bundle);
        mCatalogFragment.setArguments(bundle);
        mNotesFragment.setArguments(bundle);

        fragmentList.add(mCatalogFragment);
        fragmentList.add(mArkFragment);
        fragmentList.add(mNotesFragment);

        List<String> titles = new ArrayList<>();
        titles.add("目录");
        titles.add("书签");
        titles.add("笔记");
        mAdapter = new AppBarViewPagerAdapter(getSupportFragmentManager(), mContext, fragmentList, titles);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);


        int tabCount = mTabLayout.getTabCount();
        for (int i = 0; i < tabCount; i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(mAdapter.getTabView(i));
            }
            tab.setTag(i + "");
            //默认初始化第一条显示样式
        }

        //mTabLayout.addOnTabSelectedListener(this);
        final TabLayout.TabLayoutOnPageChangeListener listener =
                new TabLayout.TabLayoutOnPageChangeListener(mTabLayout);
        mViewPager.addOnPageChangeListener(listener);
        mViewPager.setCurrentItem(0);
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


        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // 对数据的记录
                hideSystemUI();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mViewPager.getCurrentItem() == (mAdapter.getCount() -1)) {
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                } else {
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void handlerMessage(Message msg) {

    }

    @Override
    public void onClick(View view) {

    }
}
