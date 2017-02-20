package com.zzh.reader.fragment;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zzh.reader.R;
import com.zzh.reader.adapter.CatalogueAdapter;
import com.zzh.reader.base.BaseReaderFragment;
import com.zzh.reader.dao.CatalogueDao;
import com.zzh.reader.model.Catalogue;
import com.zzh.reader.util.GreenDaoManager;

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
 * @Date: 2017/2/20 11:46
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description: CatalogueFragment.java 目录列表
 */
public class CatalogueFragment extends BaseReaderFragment implements AdapterView.OnItemClickListener {
    private ListView catalogueListView;
    private String mArgument;
    private int catalogueStartPos;
    private String bookPath;
    public static final String ARGUMENT = "argument";
    private OnClickCatalogListener mListener;
    private List<Catalogue> catalogueList;

    public void setOnClickCatalogListener(OnClickCatalogListener mListener) {
        this.mListener = mListener;
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_catalogue;
    }

    @Override
    protected void initView(View view) {
        catalogueListView = (ListView)view.findViewById(R.id.catalogue);
        catalogueListView.setOnItemClickListener(this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mArgument = bundle.getString(ARGUMENT);
        }

    }

    @Override
    protected void initData() {
        CatalogueDao catalogueDao = GreenDaoManager.getCatalogueDao();
        catalogueList = catalogueDao.queryRawCreate(" where bookPath = ? ", mArgument).list();
        CatalogueAdapter catalogueAdapter = new CatalogueAdapter(getActivity(),catalogueList);
        catalogueListView.setAdapter(catalogueAdapter);
    }

    @Override
    public void setViewListener() {

    }

    @Override
    protected void handlerMessage(Message message) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mListener != null){
            catalogueStartPos = catalogueList.get(position).getPosition();
            bookPath = catalogueList.get(position).getBookPath();
            mListener.onClickCatalog(catalogueStartPos, bookPath);
        }
    }

    public static CatalogueFragment newInstance(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, argument);
        CatalogueFragment catalogueFragment = new CatalogueFragment();
        catalogueFragment.setArguments(bundle);
        return  catalogueFragment;
    }

    public interface OnClickCatalogListener{
        void onClickCatalog(int position, String path);
    }
}
