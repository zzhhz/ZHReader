package com.zzh.reader.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzh.reader.R;

import androidx.fragment.app.Fragment;

/**
 * Created by ZZH on 17/1/23
 *
 * @Date: 17/1/23 15:23
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description: 笔记
 */
public class NotesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }
}
