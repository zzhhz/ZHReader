package com.zzh.reader.adapter;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzh.reader.R;
import com.zzh.reader.adapter.holder.BookViewHolder;
import com.zzh.reader.adapter.holder.CommonViewHolder;
import com.zzh.reader.listener.callback.DragItemTouchCallback;
import com.zzh.reader.model.Book;
import com.zzh.reader.util.WrapperUtils;
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
 * @Description: 添加头布局文件，底边布局
 */
public class BookGridAdapter extends RecyclerView.Adapter<CommonViewHolder> implements DragItemTouchCallback.ItemTouchAdapter {

    private List<Book> dataList;
    private OnClickBookListener mClickListener;
    public static final int BASE_ITEM_TYPE_HEADER = 10000;
    public static final int BASE_ITEM_TYPE_FOOTER = 20000;
    public static final int BASE_ITEM_TYPE_COMMON = 10;


    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();

    private boolean isShowDeleteButton = false;

    public BookGridAdapter() {
        this.dataList = new ArrayList<>();
    }

    /**
     * 添加头布局文件
     *
     * @param view 布局文件
     */
    public void addHeaderView(View view) {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
    }

    public void addFooterView(View view) {
        mFooterViews.put(mFooterViews.size() + BASE_ITEM_TYPE_FOOTER, view);
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
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mHeaderViews.get(viewType) != null) {
            CommonViewHolder holder = new CommonViewHolder(mHeaderViews.get(viewType));
            return holder;
        } else if (mFooterViews.get(viewType) != null) {
            CommonViewHolder holder = new CommonViewHolder(mFooterViews.get(viewType));
            return holder;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        if (isHeaderViewPos(position)) {
            return;
        }
        if (isFooterViewPos(position)) {
            return;
        }
        if (holder instanceof BookViewHolder) {
            onBindHolder((BookViewHolder) holder, position - getHeaderCount());
        }
    }

    private void onBindHolder(final BookViewHolder holder, final int position) {
        final Book book = dataList.get(position);
        holder.bookName.setText(book.getBookName());
        if (isShowDeleteButton) {
            holder.delete.setVisibility(View.VISIBLE);
        } else {
            holder.delete.setVisibility(View.GONE);
        }

        holder.bookView.setListener(new BookView.OnAnimationListener() {
            @Override
            public void onAnimationOpenEnd(BookView bookView) {
                if (mClickListener != null) {
                    mClickListener.onClickOpenBook(holder.bookView, book);
                }
            }

            @Override
            public void onAnimationCloseEnd(BookView bookView) {
                if (mClickListener != null) {
                    mClickListener.onClickCloseBook(holder.bookView, book);
                }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onClickDeleteBook(position, book);
                }
            }
        });

        holder.bookView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onLongClickBook(holder, book);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return getHeaderCount() + getRealItemCount() + getFooterCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPos(position)) {
            return mFooterViews.keyAt(position - getHeaderCount() - dataList.size());
        }
        return BASE_ITEM_TYPE_COMMON;
    }

    private boolean isHeaderViewPos(int position) {
        return position < getHeaderCount();
    }

    private boolean isFooterViewPos(int position) {
        return position >= getHeaderCount() + getRealItemCount();
    }

    public int getHeaderCount() {
        return mHeaderViews.size();
    }

    public int getFooterCount() {
        return mFooterViews.size();
    }

    public int getRealItemCount() {
        return this.dataList.size();
    }

    public void removeAllHeaderView() {
        mHeaderViews.clear();
    }

    public void removeAllFooterView() {
        mFooterViews.clear();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(this, recyclerView, new WrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                int viewType = getItemViewType(position);
                if (mHeaderViews.get(viewType) != null) {
                    return layoutManager.getSpanCount();
                } else if (mFooterViews.get(viewType) != null) {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null)
                    return oldLookup.getSpanSize(position);
                return 1;
            }
        });

    }

    @Override
    public void onViewAttachedToWindow(CommonViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
            WrapperUtils.setFullSpan(holder);
        }
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        if (fromPosition == dataList.size() - 1 || toPosition == dataList.size() - 1) {
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
