package com.wb.ygq.widget;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Description: 空视图
 * 可用于RecyclerView适配器中类别占位  配合添加生成Header  或者 Footer
 * Created on 2017/2/24
 */
public class EmptyViewHolder extends RecyclerView.ViewHolder {
    public EmptyViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}
