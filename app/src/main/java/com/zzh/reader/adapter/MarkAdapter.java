package com.zzh.reader.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zzh.reader.R;
import com.zzh.reader.model.BookMark;
import com.zzh.reader.util.BookPageFactory;
import com.zzh.zlibs.utils.ZUtils;

import java.text.DecimalFormat;
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
 * @Date: 2017/2/20 14:46
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description: MarkAdapter.java 标签列表
 */
public class MarkAdapter extends BaseAdapter {
    private Context mContext;
    private List<BookMark> dataList;
//    private Typeface typeface;
    public MarkAdapter(Context context, List<BookMark> list) {
         mContext = context;
         this.dataList = new ArrayList<>();
        this.dataList.addAll(list);
//         typeface = Typeface.createFromAsset(mContext.getAssets(),"font/QH.ttf");
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    public BookMark getItem(int position) {
        return dataList.get(position);
    }

    public void addAll(List<BookMark> list){
        dataList.addAll(list);
    }



    public long getItemId(int position) {
        return dataList.get(position).getBookMarkId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.marklistview_item,null);
            viewHolder.text_mark = (TextView) convertView.findViewById(R.id.text_mark);
            viewHolder.progress1 = (TextView) convertView.findViewById(R.id.progress1);
            viewHolder.mark_time = (TextView) convertView.findViewById(R.id.mark_time);
//            viewHolder.text_mark.setTypeface(typeface);
//            viewHolder.progress1.setTypeface(typeface);
//            viewHolder.mark_time.setTypeface(typeface);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        BookMark bookMark = dataList.get(position);
        viewHolder.text_mark.setText(bookMark.getBookMark());
        long begin = bookMark.getBegin();
        float fPercent = (float) (begin * 1.0 / BookPageFactory
                .getM_mbBufLen());
        DecimalFormat df = new DecimalFormat("#0");
        String strPercent = df.format(fPercent * 100) + "%";
        viewHolder.progress1.setText(strPercent);
        viewHolder.mark_time.setText(ZUtils.formatDateTime(bookMark.getTime()));
        return convertView;
    }

    /**
     * 删除指定书签
     * @param itemPosition
     */
    public void remove(int itemPosition) {
        dataList.remove(itemPosition);
    }

    /**
     * 清空数据
     */
    public void clear()
    {
        dataList.clear();
    }

    class ViewHolder {

        TextView text_mark,progress1,mark_time;
    }

}
