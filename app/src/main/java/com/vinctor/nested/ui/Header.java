package com.vinctor.nested.ui;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vinctor.nested.R;
import com.vinctor.nested.utils.ToastUtil;
import com.vinctor.nested.view.NestedRecyclerViewFinal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinctor on 2017/7/10.
 */

public class Header {
    Context context;
    LayoutInflater inflater;
    View view;
    ViewPager viewPager;
    NestedRecyclerViewFinal recyclerViewFinal;

    public Header(Context context, NestedRecyclerViewFinal recyclerViewFinal) {
        this.context = context;
        this.recyclerViewFinal = recyclerViewFinal;
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.header, null);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        init();
    }

    private void init() {
        //recyclerview
        View first = inflater.inflate(R.layout.first, null);
        RecyclerView recyclerView = (RecyclerView) first.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new RecyclerView.Adapter<MainActivity.Holder>() {
            @Override
            public MainActivity.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MainActivity.Holder(new TextView(context));
            }

            @Override
            public void onBindViewHolder(MainActivity.Holder holder, int position) {
                holder.textView.setTextColor(0xff2222ff);
                holder.textView.setPadding(0, 30, 0, 30);
                holder.textView.setText("内部RV" + position);
            }

            @Override
            public int getItemCount() {
                return 15;
            }
        });

        //second
        View second = inflater.inflate(R.layout.second, null);
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("click");
            }
        });
        TextView content = (TextView) second.findViewById(R.id.text);
        content.setText("tab2");
        content.setTextSize(50);

        final List<View> viewList = new ArrayList<>();
        viewList.add(first);
        viewList.add(second);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return viewList.size();
            }


            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {//必须实现，实例化
                container.addView(viewList.get(position));
                return viewList.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {//必须实现，销毁
                container.removeView(viewList.get(position));
            }
        });
    }

    public View getView() {
        return view;
    }
}
