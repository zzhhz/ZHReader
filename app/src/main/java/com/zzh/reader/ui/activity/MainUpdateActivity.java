package com.zzh.reader.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.qq.e.ads.banner2.UnifiedBannerADListener;
import com.qq.e.ads.banner2.UnifiedBannerView;
import com.qq.e.ads.interstitial2.UnifiedInterstitialAD;
import com.qq.e.ads.interstitial2.UnifiedInterstitialADListener;
import com.qq.e.comm.util.AdError;
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

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
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
    private ViewGroup mHeaderView;
    UnifiedBannerView mBannerView ;
    UnifiedInterstitialAD iad;
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
                if (drawerLayout != null) {
                    drawerLayout.openDrawer(navigationView);
                }
            }
        });

        mHeaderView = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.item_header_view_ad_grid_book, null);
        mBannerView = new UnifiedBannerView(this, Constants.AD_APP_ID, Constants.AD_BANNER_ID, new UnifiedBannerADListener() {
            @Override
            public void onNoAD(AdError adError) {

            }

            @Override
            public void onADReceive() {
                mAdapter.notifyDataSetChanged();
            }


            @Override
            public void onADExposure() {
                Log.d(TAG, "onADExposure: ");
            }

            @Override
            public void onADClosed() {
                Log.d(TAG, "onADClosed: ");
            }

            @Override
            public void onADClicked() {
                Log.d(TAG, "onADClicked: ");
            }

            @Override
            public void onADLeftApplication() {
                Log.d(TAG, "onADLeftApplication: ");
            }

            @Override
            public void onADOpenOverlay() {
                Log.d(TAG, "onADOpenOverlay: ");
            }

            @Override
            public void onADCloseOverlay() {

                Log.d(TAG, "onADCloseOverlay: ");
            }
        });
        mBannerView.setRefresh(100);

        mBannerView.loadAD();
        mHeaderView.addView(mBannerView);
        initNavigationView();
        iad = new UnifiedInterstitialAD(this, Constants.AD_APP_ID, Constants.AD_CHA_PING_ID, new UnifiedInterstitialADListener() {
            @Override
            public void onADReceive() {
                iad.showAsPopupWindow();
            }

            @Override
            public void onVideoCached() {

            }

            @Override
            public void onNoAD(AdError adError) {
                Log.d(TAG, "onNoAD: ---iad---"+adError.getErrorMsg()+", "+adError.getErrorCode());
            }

            @Override
            public void onADOpened() {

            }

            @Override
            public void onADExposure() {

            }

            @Override
            public void onADClicked() {

            }

            @Override
            public void onADLeftApplication() {

            }

            @Override
            public void onADClosed() {

            }
        });
        //iad.setAdListener();
//请求插屏广告，每次重新请求都可以调用此方法。
        iad.loadAD();
    }
    @Override
    protected void onResume() {
        super.onResume();
        iad.loadAD();
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
        mAdapter.addHeaderView(mHeaderView);
        DividerGridItemDecoration itemDecoration = new DividerGridItemDecoration(mContext);
        mBookGrid.addItemDecoration(itemDecoration);
        itemTouchHelper = new ItemTouchHelper(new DragItemTouchCallback(mAdapter).setOnDragListener(this));
        itemTouchHelper.attachToRecyclerView(mBookGrid);
        BookDao bookDao = GreenDaoManager.getBookDao();
        List<Book> list = bookDao.queryBuilder().list();
        if (list != null) {
            mAdapter.addAllBook(list);
        }
    }

    @Override
    protected void initSetListener() {
        findViewById(R.id.fab).setOnClickListener(this);
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
        if (book.getBookPath().endsWith(".epub") || book.getBookPath().endsWith(".EPUB")){
            Intent intent = new Intent(mContext, ReadEpubActivity.class);
            intent.putExtra(ReadActivity.DATA_BOOK, book);
            startActivity(intent);
        } else {
            Intent intent = new Intent(mContext, ReadActivity.class);
            intent.putExtra("bookname", book.getBookName());
            intent.putExtra("bookpath", book.getBookPath());
            intent.putExtra(ReadActivity.DATA_BOOK, book);
            startActivity(intent);
        }
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
        if (mAdapter.getItemCount() == mAdapter.getRealItemCount()) {
            mAdapter.setShowDeleteButton(true);
            itemTouchHelper.startDrag(vh);
            VibratorUtil.Vibrate((Activity) mContext, 50);   //震动50ms
        } else {
            mAdapter.removeAllFooterView();
            mAdapter.removeAllHeaderView();
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClickDeleteBook(int position, Book book) {

    }
}
