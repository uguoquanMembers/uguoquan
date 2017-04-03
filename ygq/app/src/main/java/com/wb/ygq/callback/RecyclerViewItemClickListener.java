package com.wb.ygq.callback;

import android.view.View;

/**
 * Description：RecyclerView item 点击处理
 * Created on 2017/2/25
 */

public interface RecyclerViewItemClickListener<T> {

    void onItemClick(View view, T t, int position, int eventType);

}
