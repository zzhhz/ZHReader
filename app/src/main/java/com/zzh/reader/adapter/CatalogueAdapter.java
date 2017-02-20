package com.zzh.reader.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zzh.reader.R;
import com.zzh.reader.model.Catalogue;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *----------Dragon be here!----------/
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
 * @Date: 2017/2/20 14:40
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description: CatalogueAdapter.java 列表适配器
 */
public class CatalogueAdapter extends BaseAdapter {
    private Context mContext;
    private List<Catalogue> dataList;
    //private Typeface typeface;

    public CatalogueAdapter(Context context, List<Catalogue> bookCatalogueList) {
        mContext = context;
        this.dataList = new ArrayList<>();
        this.dataList.addAll(bookCatalogueList);
//        typeface = Typeface.createFromAsset(mContext.getAssets(), "font/QH.ttf");
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.cataloguelistview_item, null);
            viewHolder.catalogue_tv = (TextView) convertView.findViewById(R.id.catalogue_tv);
//            viewHolder.catalogue_tv.setTypeface(typeface);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.catalogue_tv.setText(dataList.get(position).getCatalogue());
        return convertView;
    }

    class ViewHolder {
        TextView catalogue_tv;
    }
}
