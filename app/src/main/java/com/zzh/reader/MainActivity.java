package com.zzh.reader;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.zzh.reader.activity.FileActivity;
import com.zzh.reader.adapter.SelfAdapter;
import com.zzh.reader.base.BaseReaderNoSwipeActivity;
import com.zzh.reader.dao.BookDao;
import com.zzh.reader.model.Book;
import com.zzh.reader.ui.activity.ReadActivity;
import com.zzh.reader.ui.activity.WebActivity;
import com.zzh.reader.util.GreenDaoManager;
import com.zzh.reader.view.BookView;
import com.zzh.reader.view.DragGridView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZZH on 17/1/23
 *
 * @Date: 17/1/23 16:08
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @since 1.0.0 这个版本之后不再使用。换成RecyclerView
 * @Description: 书籍表格页
 */
@Deprecated
public class MainActivity extends BaseReaderNoSwipeActivity implements SelfAdapter.OnClickBook{

    @BindView(R.id.bookShelf)
    protected DragGridView mDragGridView;
    @BindView(R.id.drawer_layout)
    protected DrawerLayout drawerLayout;
    @BindView(R.id.navigationView)
    protected NavigationView navigationView;
    SelfAdapter mAdapter;
    private BookView mOpenBookView; //打开的那一本图书
    private boolean isOpen = false;//是否打开了图书

    public static final int ANIMATION_DURATION = 800;
    private boolean isExit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main1;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        getWindow().setBackgroundDrawable(null);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);//toolbar当actionbar使用
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);//设置导航图标
        initNavigationView();
    }

    @Override
    protected void initData() {
        mAdapter = new SelfAdapter(mContext);
        mDragGridView.setAdapter(mAdapter);
        BookDao bookDao = GreenDaoManager.getInstance().getDaoSession().getBookDao();
        List<Book> list = bookDao.queryBuilder().list();
        if (list != null) {
            mAdapter.addAll(list);
        }

    }

    @Override
    protected void initSetListener() {
        findViewById(R.id.fab).setOnClickListener(this);
        mAdapter.setListener(this);
    }

    @Override
    protected void handlerMessage(Message message) {
    }

    /**
     * 初始化drawerLoyout
     */
    private void initNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //设置侧滑菜单选择监听事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                switch (menuItem.getItemId()){
                    case R.id.nav_blog:
                        Intent intent = new Intent(mContext, WebActivity.class);
                        intent.putExtra(WebActivity.OPEN_URL, Constants.URL_MY_BLOG);
                        startActivity(intent);
                        break;
                    case R.id.nav_ver://版本信息
                        break;
                    case R.id.nav_about:
                        Intent intentA = new Intent(mContext, WebActivity.class);
                        intentA.putExtra(WebActivity.OPEN_URL, Constants.URL_MY_GITHUB);
                        startActivity(intentA);
                        break;
                    case R.id.sub_exit://退出
                        MainActivity.this.finish();
                        break;
                }
                //关闭抽屉侧滑菜单
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);  //加载菜单
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, FileActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        DragGridView.setIsShowDeleteButton(false);
        super.onRestart();
    }

    @Override
    protected void onStop() {
        DragGridView.setIsShowDeleteButton(false);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        DragGridView.setIsShowDeleteButton(false);
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                drawerLayout.closeDrawers();
            } else {
                if (DragGridView.getShowDeleteButton()) {
                    resetDragGridView();
                } else {
                    exitBy2Click();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 在2秒内按下返回键两次才退出
     */
    private void exitBy2Click() {
        // press twice to exit
        Timer tExit;
        if (!isExit) {
            isExit = true; // ready to exit
            if (DragGridView.getShowDeleteButton()) {
                //要保证是同一个adapter对象,否则在Restart后无法notifyDataSetChanged
                //adapter.notifyDataSetChanged();
                resetDragGridView();
            } else {
                Toast.makeText(
                        this,
                        this.getResources().getString(R.string.press_twice_to_exit),
                        Toast.LENGTH_SHORT).show();
            }
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // cancel exit
                }
            }, 2000); // 2 seconds cancel exit task

        } else {
            finish();
        }
    }

    private WindowManager.LayoutParams getDefaultWindowParams() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                0, 0,
                WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,//windown类型,有层级的大的层级会覆盖在小的层级
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                PixelFormat.RGBA_8888);

        return params;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Intent intent = new Intent(MainActivity.this, FileActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onEventMainThread(Intent intent) {
        if (intent == null)
            return;
        String action = intent.getAction();
        if (TextUtils.isEmpty(action))
            return;
        switch (action) {
            case Constants.ACTION_SHOW_DELETE_BOOK_LIST:
                DragGridView.setIsShowDeleteButton(true);
                mAdapter.notifyDataSetChanged();
                break;
            case Constants.ACTION_REFRESH_BOOK_LIST:
            case Constants.ACTION_CLOSE_BOOK:
                refreshBookList();
                break;
        }
    }

    private void refreshBookList() {
        DragGridView.setIsShowDeleteButton(false);
        if (mOpenBookView != null) {
            if (isOpen) {
                mOpenBookView.closeBook();
            }
        }
        List<Book> books = GreenDaoManager.getBookDao().loadAll();
        mAdapter.clear();
        mAdapter.addAll(books);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickOpen(BookView bookView, Book book) {
        ///
        mOpenBookView = bookView;
        resetDragGridView();
        isOpen = true;
        Intent intent = new Intent(mContext, ReadActivity.class);
        intent.putExtra("bookname", book.getBookName());
        intent.putExtra("bookpath", book.getBookPath());
        intent.putExtra(ReadActivity.DATA_BOOK, book);
        startActivity(intent);
    }

    private void resetDragGridView() {
        DragGridView.setIsShowDeleteButton(false);
        mAdapter.notifyDataSetChanged();
    }

    //关闭动画执行结束时，执行此动画
    @Override
    public void onClickClose(BookView bookView, Book book) {
        DragGridView.setIsShowDeleteButton(false);
        BookDao bookDao = GreenDaoManager.getInstance().getDaoSession().getBookDao();
        List<Book> list = bookDao.queryBuilder().list();
        mAdapter.clear();
        mAdapter.addAll(list);
        mAdapter.notifyDataSetChanged();
    }
}
