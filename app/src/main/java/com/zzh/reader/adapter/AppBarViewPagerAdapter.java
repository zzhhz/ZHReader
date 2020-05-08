package com.zzh.reader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zzh.reader.R;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * ----------Dragon be here!----------/
 * Created by Administrator on 2017/2/17.
 *
 * @Date: 2017/2/17
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description: 侧滑菜单的标题适配器
 */

public class AppBarViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> dataList;
    private List<String> tabTitles;

    private Context mContext;


    public AppBarViewPagerAdapter(FragmentManager fm, Context ctx, List<Fragment> dataList, List<String> tabTitles) {
        super(fm);
        mContext = ctx;
        this.dataList = dataList;
        this.tabTitles = tabTitles;
    }

    public View getTabView(int position) {
        return classCategoryTabView(position);
    }

    private View classCategoryTabView(int position) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_tab_app_bar, null);
        TextView tv = (TextView) v.findViewById(R.id.textView);
        tv.setText(tabTitles.get(position));
        return v;
    }

    @Override
    public Fragment getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }
}
