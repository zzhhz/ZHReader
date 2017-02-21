package com.zzh.reader.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.zzh.reader.Constants;
import com.zzh.reader.R;
import com.zzh.reader.activity.FileActivity;
import com.zzh.reader.adapter.BookGridAdapter;
import com.zzh.reader.adapter.DividerGridItemDecoration;
import com.zzh.reader.base.BaseReaderNoSwipeActivity;
import com.zzh.reader.dao.BookDao;
import com.zzh.reader.listener.callback.DragItemTouchCallback;
import com.zzh.reader.model.Book;
import com.zzh.reader.util.GreenDaoManager;
import com.zzh.reader.util.VibratorUtil;
import com.zzh.reader.view.BookView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ----------Dragon be here!----------/
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑
 * 　　　　┃　　　┃代码无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━永无BUG━━━━━
 *
 * @Date: 2017/2/21 11:06
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description: MainUpdateActivity.java 升级拖拽控件
 */
public class MainUpdateActivity extends BaseReaderNoSwipeActivity implements DragItemTouchCallback.OnDragListener, BookGridAdapter.OnClickBookListener {
    @BindView(R.id.recyclerView_book_grid)
    public RecyclerView mBookGrid;

    @BindView(R.id.drawer_layout)
    protected DrawerLayout drawerLayout;
    @BindView(R.id.navigationView)
    protected NavigationView navigationView;

    private BookView mOpenBookView; //打开的那一本图书
    private boolean isOpen = false;//是否打开了图书
    private BookGridAdapter mAdapter;
    //
    private ItemTouchHelper itemTouchHelper;
    private boolean isExit = false;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main_update;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        setToolbar(R.id.toolbar);
        toolbars("天问", R.drawable.ic_menu_white_24dp, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        initNavigationView();
    }

    /**
     * 初始化drawerLoyout
     */
    private void initNavigationView() {
        //设置侧滑菜单选择监听事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
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
                        MainUpdateActivity.this.finish();
                        System.exit(0);
                        break;
                }
                //关闭抽屉侧滑菜单
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    protected void initData() {
        mAdapter = new BookGridAdapter();
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        mBookGrid.setHasFixedSize(true);
        mBookGrid.setLayoutManager(manager);
        mBookGrid.setAdapter(mAdapter);
        DividerGridItemDecoration itemDecoration = new DividerGridItemDecoration(mContext);
        //itemDecoration.setHorizontalDivider(getResources().getDrawable(R.drawable.bookshelf_dock));
        mBookGrid.addItemDecoration(itemDecoration);
        itemTouchHelper = new ItemTouchHelper(new DragItemTouchCallback(mAdapter).setOnDragListener(this));
        itemTouchHelper.attachToRecyclerView(mBookGrid);
        BookDao bookDao = GreenDaoManager.getInstance().getBookDao();
        List<Book> list = bookDao.queryBuilder().list();
        if (list != null) {
            mAdapter.addAllBook(list);
        }

    }

    @Override
    protected void initSetListener() {
        /*mBookGrid.addOnItemTouchListener(new OnRecyclerItemClickListener(mBookGrid) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getAdapterPosition();
                if (position >= 0) {
                    Book book = mAdapter.getItemObject(position);

                    mOpenBookView = ((BookViewHolder) vh).bookView;
                    resetDragGridView();
                    isOpen = true;
                    Intent intent = new Intent(mContext, ReadActivity.class);
                    intent.putExtra("bookname", book.getBookName());
                    intent.putExtra("bookpath", book.getBookPath());
                    intent.putExtra(ReadActivity.DATA_BOOK, book);

                    //startActivity(intent);
                }
                showMessage(position + "");
                Log.d(TAG, "onItemClick: " + position);
            }

            @Override
            public void onLongClick(RecyclerView.ViewHolder vh) {
                mAdapter.setShowDeleteButton(true);
                if (vh.getLayoutPosition() != mAdapter.getItemCount() - 1) {
                    itemTouchHelper.startDrag(vh);
                    VibratorUtil.Vibrate((Activity) mContext, 50);   //震动70ms
                }
            }
        });*/
        mAdapter.setOnClickListener(this);
    }

    @Override
    protected void handlerMessage(Message message) {

    }

    @Override
    public void onFinishDrag() {
        //ACache.get(mContext).put("items",(ArrayList<ClipData.Item>)results);
        mAdapter.notifyDataSetChanged();
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
            Intent intent = new Intent(MainUpdateActivity.this, FileActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Intent intent = new Intent(mContext, FileActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                drawerLayout.closeDrawers();
            } else {
                if (mAdapter.isShowDeleteButton()) {
                    resetDragGridView();
                } else {
                    exitBy2Click();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
                mAdapter.notifyDataSetChanged();
                break;
            case Constants.ACTION_REFRESH_BOOK_LIST:
            case Constants.ACTION_CLOSE_BOOK:
                refreshBookList();
                break;
        }
    }

    private void refreshBookList() {
        mAdapter.setShowDeleteButton(false);
        if (mOpenBookView != null) {
            if (isOpen) {
                mOpenBookView.closeBook();
            }
        }
        List<Book> books = GreenDaoManager.getBookDao().loadAll();
        mAdapter.clear();
        mAdapter.addAllBook(books);
        mAdapter.notifyDataSetChanged();
    }

    private void resetDragGridView() {
        mAdapter.setShowDeleteButton(false);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 在2秒内按下返回键两次才退出
     */
    private void exitBy2Click() {
        // press twice to exit
        Timer tExit;
        if (!isExit) {
            isExit = true; // ready to exit
            if (mAdapter.isShowDeleteButton()) {
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

    @Override
    public void onClickOpenBook(BookView bookView, Book book) {
        mOpenBookView = bookView;
        resetDragGridView();
        isOpen = true;
        Intent intent = new Intent(mContext, ReadActivity.class);
        intent.putExtra("bookname", book.getBookName());
        intent.putExtra("bookpath", book.getBookPath());
        intent.putExtra(ReadActivity.DATA_BOOK, book);
        startActivity(intent);
    }

    @Override
    public void onClickCloseBook(BookView bookView, Book book) {
        mAdapter.setShowDeleteButton(false);
        BookDao bookDao = GreenDaoManager.getInstance().getDaoSession().getBookDao();
        List<Book> list = bookDao.queryBuilder().list();
        mAdapter.clear();
        mAdapter.addAllBook(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLongClickBook(RecyclerView.ViewHolder vh, Book book) {
        mAdapter.setShowDeleteButton(true);
        itemTouchHelper.startDrag(vh);
        VibratorUtil.Vibrate((Activity) mContext, 50);   //震动70ms
    }

    @Override
    public void onClickDeleteBook(int position, Book book) {

    }
}
