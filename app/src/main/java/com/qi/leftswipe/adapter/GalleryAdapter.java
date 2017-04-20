package com.qi.leftswipe.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.qi.leftswipe.R;

import java.util.List;

/**
 * Created by fengqi.sun on 2017/4/13.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private final Context ctx;
    private List<String> datas;

    public GalleryAdapter(Context context) {
        this.ctx = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_gallery_task, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.iconImg = (ImageView) view.findViewById(R.id.icon);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String url = datas.get(position);
        Glide.with(ctx).load(url).dontAnimate().into(holder.iconImg);
    }

    @Override
    public int getItemCount() {
        if (datas == null) {
            return 0;
        }
        return datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }
        ImageView iconImg;
    }

    public void addData(List<String> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }
}
