package com.example.yvime.e_parker.news;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yvime.e_parker.R;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */


    public class news extends Fragment {

        private RecyclerView mRecyclerView;
        private List<DataBean> dataBeanList;
        private DataBean dataBean;
        private RecyclerAdapter mAdapter;


        public news() {
            // Required empty public constructor
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_news, container, false);
            mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            initData();
            return view;


        }


        private void initData() {
            dataBeanList = new ArrayList<>();
            for (int i = 1; i <= 1; i++) {
                dataBean = new DataBean();
                dataBean.setID(i + "");
                dataBean.setType(0);
                dataBean.setParentLeftTxt("Parker 路邊機車停車位APP上限瞜!");
                dataBean.setParentRightTxt("20190707");
                dataBean.setChildLeftTxt("即日起至2019/7/30，使用Parker來自動扣款車費，可享9折優惠喲!快邀好友一同加入Parker吧!" );
                dataBean.setChildBean(dataBean);
                dataBeanList.add(dataBean);
            }
            setData();
        }


    private void setData(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new RecyclerAdapter(getActivity(), dataBeanList);
        mRecyclerView.setAdapter(mAdapter);
        //滚动监听
        mAdapter.setOnScrollListener(new RecyclerAdapter.OnScrollListener() {
            @Override
            public void scrollTo(int pos) {
                mRecyclerView.scrollToPosition(pos);
            }
        });
    }


    }