package com.zzh.reader.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzh.reader.R;

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
 * Created by ZZH on 17/2/12.
 *
 * @Date: 17/2/12
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description:
 */
public class FileViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.local_file_icon)
    public ImageView fileIcon;
    @BindView(R.id.local_file_text)
    public TextView fileName;
    @BindView(R.id.local_file_text_size)
    public TextView fileSize;

    public String filePath;
    @BindView(R.id.local_file_image)
    public CheckBox isCheck;
    public FileViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        /*fileIcon = (ImageView) itemView.findViewById(R.id.local_file_icon);
        fileName = (TextView) itemView.findViewById(R.id.local_file_text);
        fileSize = (TextView) itemView.findViewById(R.id.local_file_text_size);
        isCheck = (CheckBox) itemView.findViewById(R.id.local_file_image);*/
    }
}
