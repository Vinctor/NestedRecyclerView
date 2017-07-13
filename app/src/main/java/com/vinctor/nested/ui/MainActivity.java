package com.vinctor.nested.ui;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vinctor.nested.R;
import com.vinctor.nested.view.NestedRecyclerViewFinal;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    @Bind(R.id.recyclerview)
    NestedRecyclerViewFinal recyclerViewFinal;
    RecyclerView.Adapter<Holder> adapter;
    @Bind(R.id.swip)
    SwipeRefreshLayout swip;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            swip.setRefreshing(false);
        }
    };

    @Override
    protected void initView() {
        swip.setColorSchemeColors(0xff00ff00);
        recyclerViewFinal.setLayoutManager(new LinearLayoutManager(thisActivity));
        Header header = new Header(thisActivity, recyclerViewFinal);
        recyclerViewFinal.addHeaderView(header.getView());
        adapter = new RecyclerView.Adapter<Holder>() {
            @Override
            public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
                TextView textView = new TextView(thisActivity);
                return new Holder(textView);
            }

            @Override
            public void onBindViewHolder(Holder holder, int position) {
                holder.textView.setTextColor(0xffff2222);
                holder.textView.setPadding(0, 30, 0, 30);
                holder.textView.setText("外部RV" + position);
            }

            @Override
            public int getItemCount() {
                return 20;
            }
        };
        recyclerViewFinal.setAdapter(adapter);
        recyclerViewFinal.setHasLoadMore(false);

        swip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            Message msg = handler.obtainMessage();
                            handler.sendMessage(msg);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }


    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    public static class Holder extends RecyclerView.ViewHolder {

        TextView textView;

        public Holder(View itemView) {
            super(itemView);
            if (itemView instanceof TextView) {
                textView = (TextView) itemView;
            }
        }
    }
}
