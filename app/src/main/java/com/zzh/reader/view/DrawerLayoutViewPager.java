package com.zzh.reader.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

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
 * Created by ZZH on 17/2/18.
 *
 * @Date: 17/2/18
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description: 自定义的ViewPager，主要是解决ViewPager和DrawerLayout滑动冲突
 */
public class DrawerLayoutViewPager extends ViewPager {
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private int index = -1;// ViewPager显示的position
    public static final String TAG = "VIEW_PAGER";
    private DrawerLayout mParentLayout;

    public void setParentLayout(DrawerLayout parentLayout) {
        mParentLayout = parentLayout;
    }

    public void setViewPager(ViewPager viewPager) throws Exception {
        if (viewPager == null)
            throw new Exception("The ViewPager can't be null");
        mViewPager = viewPager;
        mPagerAdapter = mViewPager.getAdapter();
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public DrawerLayoutViewPager(Context context) {
        super(context);
    }

    public DrawerLayoutViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mParentLayout != null) {
            mPagerAdapter = getAdapter();
            if (index == mPagerAdapter.getCount() - 1) {
                mParentLayout.requestDisallowInterceptTouchEvent(false);
                Log.d(TAG, " false");
            } else {
                mParentLayout.requestDisallowInterceptTouchEvent(true);
                Log.d(TAG, " true");
            }
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        super.onPageScrolled(position, offset, offsetPixels);
        Log.d(TAG, "onPageScrolled: getCurrentItem-" + getCurrentItem());
        index = position;
    }
}
