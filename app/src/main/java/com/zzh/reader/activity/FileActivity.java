package com.zzh.reader.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.zzh.reader.R;
import com.zzh.reader.ui.activity.ReadActivity;
import com.zzh.reader.adapter.FileAdapter;
import com.zzh.reader.base.BaseReaderActivity;
import com.zzh.reader.dao.BookDao;
import com.zzh.reader.model.Book;
import com.zzh.reader.model.FileComparator;
import com.zzh.reader.util.EventUtils;
import com.zzh.reader.util.GreenDaoManager;
import com.zzh.zlibs.utils.ZUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZZH on 17/2/10
 *
 * @Date: 17/2/10 13:45
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description: 文件列表类, 显示文件夹，文本文件。
 */
public class FileActivity extends BaseReaderActivity implements FileAdapter.OnClickItemListener,
        Toolbar.OnMenuItemClickListener
{

    @BindView(R.id.rl_up)
    protected View clickPath;

    @BindView(R.id.tv_file_path)
    protected TextView tv_showPath;
    private String sdPath;
    private List<File> mCurPahtFiles;
    private FileAdapter mAdapter;
    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;
    private List<String> isExistBookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_file;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        setToolbar(R.id.toolbar);
        toolbars("浏览图书", R.drawable.return_button1, null);
    }

    @Override
    protected void initData() {
        mAdapter = new FileAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        requestReadStoragePermission();
    }

    @Override
    protected void initSetListener() {
        clickPath.setOnClickListener(this);
        mAdapter.setClickItemListener(this);
        if (mToolbar!= null){
            mToolbar.setOnMenuItemClickListener(this);
        }

    }

    @OnClick(R.id.fab_complete)
    public void clickButton(View view){
        switch (view.getId()){
            case R.id.fab_complete:
                addBook2Db();
                break;
        }
    }

    @Override
    protected void handlerMessage(Message message) {
        if (message == null){
            return;
        }
        switch (message.what){
            case 0:
                mCurPahtFiles = (List<File>) message.obj;
                mAdapter.addList(mCurPahtFiles);
                mAdapter.changeDirectory(sdPath);
                tv_showPath.setText(sdPath);
                mAdapter.setExistBookList(isExistBookList);
                mAdapter.notifyDataSetChanged();
                break;
            case 1:
                mAdapter.setExistBookList(isExistBookList);
                mAdapter.notifyDataSetChanged();
                EventUtils.sendEventRefreshBookList();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_up://上一级
                if (ZUtils.getSDCardRootPath().equals(sdPath)){
                    showMessage("已经是根目录了");
                } else {
                    File file = new File(sdPath);
                    sdPath = file.getParentFile().getAbsolutePath();
                    loadFileList(sdPath);
                }
                break;
        }
    }

    @Override
    protected void notifyPermission(int code, boolean flag) {
        if (code == REQUEST_CODE_READ_PERMISSION){
            if (flag){
                sdPath = ZUtils.getSDCardRootPath();
                loadFileList(sdPath);
            }
        }
    }
    private void loadFileList(String filePath){
        LoadFileRunnable runnable = new LoadFileRunnable(filePath, mHandler);
        Thread thread = new Thread(runnable);
        thread.start();
    }

    @Override
    public void onClickFile(File file) {
        if (file.isDirectory()){ // 点击的是一个文件夹
            sdPath = file.getAbsolutePath();
            mAdapter.clear();
            loadFileList(sdPath);
        } else {
            //点击的是一个文件打开
            BookDao bookDao = GreenDaoManager.getInstance().getDaoSession().getBookDao();
            List<Book> books = bookDao.queryRaw(" WHERE bookName =? AND bookPath = ?", file.getName(), file.getAbsolutePath());
            Book entity = null;
            if (books == null || books.size()<=0){
                //添加到数据库
                entity = new Book(null, file.getName(), file.getAbsolutePath(), null, false);
                bookDao.insert(entity);
            } else {
                entity = books.get(0);
            }
            Intent intent = new Intent(this, ReadActivity.class);
            intent.putExtra("bookname",file.getName());
            intent.putExtra("bookpath", file.getAbsolutePath());
            intent.putExtra(ReadActivity.DATA_BOOK, entity);
            EventUtils.sendEventRefreshBookList();
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        mToolbar.getMenu().getItem(0).setVisible(true);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    class LoadFileRunnable implements Runnable{

        private String mCurPath;
        private Handler sHandler;

        public LoadFileRunnable(String mCurPath, Handler handler) {
            this.mCurPath = mCurPath;
            this.sHandler = handler;
        }

        @Override
        public void run() {
            //列出所有的文件
            File file = new File(mCurPath);
            List<File> files = Arrays.asList(file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    String name = pathname.getName();
                    if (pathname.isDirectory() && !name.startsWith(".")){
                        return true;
                    } else {
                        if ((name.endsWith(".text") || name.endsWith(".txt")) && !name.startsWith(".")){
                            return true;
                        }
                    }
                    return false;
                }
            }));
            //拍排序
            if (files != null){
                Collections.sort(files, new FileComparator());
            }
            //图书列表中的书籍
            List<Book> books = GreenDaoManager.getBookDao().loadAll();
            if (isExistBookList == null){
                isExistBookList = new ArrayList<>();
            }
            isExistBookList.clear();
            for (Book book : books){
                isExistBookList.add(book.getBookPath());
            }
            //发送消息
            Message message = Message.obtain(sHandler);
            message.what = 0;
            message.obj = files;
            sHandler.sendMessage(message);
        }
    }

    public void addBook2Db(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                List<String> checkedFiles = mAdapter.getCheckedFiles();
                if (checkedFiles== null || checkedFiles.size() < 1){
                    return;
                }
                List<Book> books = new ArrayList<>();
                for (String bookPath :
                        checkedFiles) {
                    File tmp = new File(bookPath);
                    Book e = new Book(null, tmp.getName(), bookPath, null, false);
                    books.add(e);
                }
                GreenDaoManager.getBookDao().insertInTx(books);
                List<Book> bookList = GreenDaoManager.getBookDao().loadAll();
                if (isExistBookList==null)
                {
                    isExistBookList  = new ArrayList<>();
                }
                isExistBookList.clear();
                for (Book str : bookList){
                    isExistBookList.add(str.getBookPath());
                }
                mHandler.sendEmptyMessageDelayed(1, 1000);
            }
        };
        Thread th = new Thread(runnable);
        th.start();

    }
}
