package com.zzh.reader.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzh.reader.R;
import com.zzh.reader.adapter.holder.BookViewHolder;
import com.zzh.reader.listener.callback.DragItemTouchCallback;
import com.zzh.reader.model.Book;
import com.zzh.reader.view.BookView;

import java.util.ArrayList;
import java.util.Collections;
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
 * Created by Administrator on 2017/2/21.
 *
 * @Date: 2017/2/21
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description:
 */
public class BookGridAdapter extends RecyclerView.Adapter<BookViewHolder> implements DragItemTouchCallback.ItemTouchAdapter {

    private List<Book> dataList;
    private OnClickBookListener mClickListener;

    private boolean isShowDeleteButton = false;

    public BookGridAdapter() {
        this.dataList = new ArrayList<>();
    }

    public void setShowDeleteButton(boolean showDeleteButton) {
        isShowDeleteButton = showDeleteButton;
    }

    public void setOnClickListener(OnClickBookListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    public void addAllBook(List<Book> list) {
        this.dataList.addAll(list);
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BookViewHolder holder, final int position) {
        final Book book = dataList.get(position);
        holder.bookName.setText(book.getBookName());
        if (isShowDeleteButton){
            holder.delete.setVisibility(View.VISIBLE);
        } else {
            holder.delete.setVisibility(View.GONE);
        }

        holder.bookView.setListener(new BookView.OnAnimationListener() {
            @Override
            public void onAnimationOpenEnd(BookView bookView) {
                if (mClickListener != null){
                    mClickListener.onClickOpenBook(holder.bookView, book);
                }
            }

            @Override
            public void onAnimationCloseEnd(BookView bookView) {
                if (mClickListener != null){
                    mClickListener.onClickCloseBook(holder.bookView,book);
                }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null){
                    mClickListener.onClickDeleteBook(position, book);
                }
            }
        });

        holder.bookView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mClickListener != null){
                    mClickListener.onLongClickBook(holder, book);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        if (fromPosition==dataList.size()-1 || toPosition==dataList.size()-1){
            return;
        }
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(dataList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(dataList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onSwiped(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        dataList.clear();
    }

    public Book getItemObject(int position) {
       return dataList.get(position);
    }

    public boolean isShowDeleteButton() {
        return isShowDeleteButton;
    }

    public interface OnClickBookListener {
        void onClickOpenBook(BookView bookView, Book book);
        void onClickCloseBook(BookView bookView, Book book);
        void onLongClickBook(RecyclerView.ViewHolder vh, Book book);
        void onClickDeleteBook(int position, Book book);
    }
}
