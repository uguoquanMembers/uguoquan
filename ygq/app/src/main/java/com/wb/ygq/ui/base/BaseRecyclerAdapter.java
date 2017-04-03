package com.wb.ygq.ui.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;


import com.wb.ygq.callback.RecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zuoxian on 15/9/7.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected RecyclerViewItemClickListener itemClickListener;

    protected List<T> mItems;
    protected Context mContext;
    public LayoutInflater inflater;

    public BaseRecyclerAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        mItems = new ArrayList<>();
    }

    public void setItemClickListener(RecyclerViewItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public List<T> getList() {
        return mItems;
    }

    public void addItem(@Nullable T item) {
        if (item == null) return;
        mItems.add(mItems.size(), item);
        notifyItemInserted(mItems.size());
    }

    public void addItems(@Nullable List<T> items) {
        if (items == null) return;
        this.mItems.addAll(items);
        notifyDataSetChanged();
    }

    public boolean containsAll(@NonNull List<T> items) {
        return mItems.containsAll(items);
    }

    public void updateItem(@Nullable T tasks, int position) {
        if (tasks == null) return;
        mItems.set(position, tasks);
        notifyItemChanged(position);
    }

    public void updateItems(@Nullable List<T> items) {
        if (items == null) return;
        this.mItems.clear();
        this.mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void removeItem(int index) {
        mItems.remove(index);
        notifyItemRemoved(index);
    }

    public void getView(int position, RecyclerView.ViewHolder viewHolder, int type, T item) {
    }

    public T getItem(int location) {
        return mItems.get(location);
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

}
