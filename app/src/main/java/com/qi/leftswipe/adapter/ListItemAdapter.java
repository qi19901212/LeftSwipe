package com.qi.leftswipe.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.qi.SwipeLeftView;
import com.qi.leftswipe.R;
import com.qi.leftswipe.model.ItemModel;

import java.util.ArrayList;

/**
 * Created by fengqi.sun on 2017/4/20.
 */

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ViewHolder> {
    private final Context ctx;
    private ArrayList<ItemModel> datas;

    public ListItemAdapter(Context context) {
        this.ctx = context;
    }

    @Override
    public ListItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(ctx).inflate(R.layout.item_task,
                parent, false);
        ListItemAdapter.ViewHolder viewHolder = new ListItemAdapter.ViewHolder(convertView);
        viewHolder.refreshableView = (SwipeLeftView) convertView.findViewById(R.id
                .refreshable_view);
        viewHolder.horizontalRecyclerView = (RecyclerView) convertView.findViewById(R.id
                .horizontal_recyclerView);
        viewHolder.leftPullLayout = (LinearLayout) convertView.findViewById(R.id.left_pull_layout);

        LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        viewHolder.horizontalRecyclerView.setLayoutManager(layoutManager);
        viewHolder.gAdapter = new GalleryAdapter(ctx);
        viewHolder.horizontalRecyclerView.setAdapter(viewHolder.gAdapter);
        convertView.setTag(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListItemAdapter.ViewHolder holder, int position) {
        View view = holder.itemView;
        ItemModel data = datas.get(position);
        holder.gAdapter.addData(data.url);
    }

    @Override
    public int getItemCount() {
        if (datas == null) return 0;
        return datas.size();
    }

    public void addData(ArrayList<ItemModel> datasItemModel) {
        datas = datasItemModel;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public SwipeLeftView refreshableView;
        public RecyclerView horizontalRecyclerView;
        public LinearLayout leftPullLayout;
        public GalleryAdapter gAdapter;

        public ViewHolder(View convertView) {
            super(convertView);
        }
    }
}
