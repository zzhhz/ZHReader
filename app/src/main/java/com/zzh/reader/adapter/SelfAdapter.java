package com.zzh.reader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zzh.reader.listener.DragGridListener;
import com.zzh.reader.R;
import com.zzh.reader.dao.BookDao;
import com.zzh.reader.model.Book;
import com.zzh.reader.util.GreenDaoManager;
import com.zzh.reader.view.BookView;
import com.zzh.reader.view.DragGridView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
 * Created by ZZH on 17/2/14.
 *
 * @Date: 17/2/14
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description: 首页书籍适配器
 */
public class SelfAdapter extends BaseAdapter implements DragGridListener {
    private List<Book> dataList;
    private Context mContext;
    private OnClickBook mListener;
    private int mHidePosition = -1;

    public SelfAdapter(Context ctx) {
        this.dataList = new ArrayList<>();
        this.mContext = ctx;
    }

    public void setListener(OnClickBook listener) {
        mListener = listener;
    }

    public void addBook(Book book) {
        dataList.add(book);
    }

    public void addAll(List<Book> list) {
        dataList.addAll(list);
    }

    public void clear() {
        dataList.clear();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Book getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dataList.get(position).getBookId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == mHidePosition) {
            convertView.setVisibility(View.INVISIBLE);
        } else {
            convertView.setVisibility(View.VISIBLE);
        }
        if (DragGridView.getShowDeleteButton()){
            holder.mDelete.setVisibility(View.VISIBLE);
        } else {
            holder.mDelete.setVisibility(View.GONE);
        }
        final Book book = dataList.get(position);
        holder.mTextView.setText(book.getBookName() + "");
        holder.bookCover.setListener(new BookView.OnAnimationListener() {
            @Override
            public void onAnimationOpenEnd(BookView bookView) {
                if (mListener != null) {
                    mListener.onClickOpen(bookView, book);
                }
            }

            @Override
            public void onAnimationCloseEnd(BookView bookView) {
                if (mListener != null) {
                    mListener.onClickClose(bookView, book);
                }
            }
        });
        return convertView;
    }

    @Override
    public void reorderItems(int oldPosition, int newPosition) {
        if (oldPosition > dataList.size() || newPosition > dataList.size()){
            return;
        }
        BookDao bookDao = GreenDaoManager.getInstance().getDaoSession().getBookDao();
        Book temp = dataList.get(oldPosition);
        List<Book> bookList = bookDao.loadAll();
        long tempId = bookList.get(newPosition).getBookId();

        if (oldPosition < newPosition) {
            for (int i = oldPosition; i < newPosition; i++) {
                List<Book> books = bookDao.loadAll();
                long dataBaseId = books.get(i).getBookId();
                Collections.swap(dataList, i, i + 1);
                updateBookPosition(i, dataBaseId, dataList);
            }
        } else if (oldPosition > newPosition) {
            for (int i = oldPosition; i > newPosition; i--) {
                List<Book> books = bookDao.loadAll();
                long dataBaseId = books.get(i).getBookId();
                Collections.swap(dataList, i, i - 1);
                updateBookPosition(i, dataBaseId, dataList);
            }
        }
        dataList.set(newPosition, temp);
        updateBookPosition(newPosition, tempId, dataList);
        notifyDataSetChanged();
    }

    /**
     * 更改位置
     *
     * @param i
     * @param dataBaseId
     * @param dataList
     */
    private void updateBookPosition(int i, long dataBaseId, List<Book> dataList) {
        Book book = new Book();
        Book book1 = new Book();
        String bookPath = dataList.get(i).getBookPath();
        String bookName = dataList.get(i).getBookName();
        book.setBookPath(bookPath);
        book1.setBookName(bookName);
        updateBoot2Db(dataBaseId, book, book1);
    }

    /**
     * 保存到数据库
     *
     * @param dataBaseId
     * @param book
     * @param book1
     */
    private void updateBoot2Db(long dataBaseId, Book book, Book book1) {
        BookDao dao = GreenDaoManager.getInstance().getDaoSession().getBookDao();
        Book load = dao.load(dataBaseId);
        if (load == null) {
            Book book2 = new Book(dataBaseId, book1.getBookName(), book.getBookPath(), null, false);
            dao.update(book2);
        }else {
            load.setBookName(book1.getBookName());
            load.setBookPath(book.getBookPath());
            dao.update(load);
        }
    }

    @Override
    public void setHideItem(int hidePosition) {
        this.mHidePosition = hidePosition;
        notifyDataSetChanged();
    }

    @Override
    public void removeItem(int deletePosition) {
        Book book = dataList.get(deletePosition);
        GreenDaoManager.getBookDao().delete(book);
        dataList.remove(deletePosition);
        notifyDataSetChanged();
    }

    @Override
    public void setItemToFirst(int openPosition) {

    }

    @Override
    public void nitifyDataRefresh() {
        notifyDataSetChanged();
    }

    class ViewHolder {
        @BindView(R.id.bookName)
        TextView mTextView;
        @BindView(R.id.bookCover)
        BookView bookCover;
        @BindView(R.id.item_close_Im)
        ImageButton mDelete;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnClickBook {
        void onClickOpen(BookView bookView, Book book);
        void onClickClose(BookView bookView, Book book);
    }
}
