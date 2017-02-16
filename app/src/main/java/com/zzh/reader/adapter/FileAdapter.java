package com.zzh.reader.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.zzh.reader.R;
import com.zzh.reader.adapter.holder.FileViewHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
 * @Description: 文件列表适配器
 */
public class FileAdapter extends RecyclerView.Adapter<FileViewHolder> {

    //当前文件路径
    public String mCurPath;


    public List<String> mCheckedFiles;//选中的文件

    private List<File> fileList;
    private OnClickItemListener clickItemListener;
    private List<String> mExistBookList; //存在图书列表中得书籍路径

    public FileAdapter() {
        this.mCheckedFiles = new ArrayList<>();
        this.fileList = new ArrayList<>();
    }

    public void addList(List<File> files) {
        if (fileList != null) {
            fileList.addAll(files);
        }
    }

    public void setExistBookList(List<String> existBookList) {
        mExistBookList = existBookList;
    }

    public void setClickItemListener(OnClickItemListener clickItemListener) {
        this.clickItemListener = clickItemListener;
    }

    public void remove(int index) {
        fileList.remove(index);
    }


    public void changeDirectory(String cd) {
        this.mCurPath = cd;
    }

    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View content = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file, null);
        return new FileViewHolder(content);
    }

    @Override
    public void onBindViewHolder(FileViewHolder holder, int position) {
        if (position < getItemCount()-1) {
            holder.itemView.setVisibility(View.VISIBLE);
            final File file = fileList.get(position);
            if (file.isDirectory()) {
                holder.fileIcon.setImageResource(R.drawable.folder);
                holder.isCheck.setVisibility(View.GONE);
            } else {
                holder.fileIcon.setImageResource(R.drawable.file_type_txt);
                if (mExistBookList != null && mExistBookList.contains(file.getAbsolutePath())){
                    holder.isCheck.setVisibility(View.GONE);
                } else {
                    holder.isCheck.setVisibility(View.VISIBLE);
                    //判断是否选中
                    if (mCheckedFiles.contains(file.getAbsolutePath())){
                        holder.isCheck.setChecked(true);
                    }else
                    {
                        holder.isCheck.setChecked(false);
                    }
                }
            }
            holder.fileName.setText(file.getName());
            holder.fileSize.setText(file.length() + "");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickItemListener != null)
                        clickItemListener.onClickFile(file);
                }
            });
            holder.isCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        mCheckedFiles.add(file.getAbsolutePath());
                    } else {
                        Log.d("----", "onCheckedChanged: =------"+isChecked);
                        mCheckedFiles.remove(file.getAbsolutePath());
                    }
                }
            });
        } else {
            holder.itemView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return fileList.size()+1;
    }

    public void clear() {
        fileList.clear();
    }

    public interface OnClickItemListener
    {
        void onClickFile(File file);
    }

    public List<String> getCheckedFiles() {
        return mCheckedFiles;
    }
}
