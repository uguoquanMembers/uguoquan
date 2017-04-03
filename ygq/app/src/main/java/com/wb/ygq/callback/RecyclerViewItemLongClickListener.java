package com.wb.ygq.callback;

import android.view.View;

/**
 * Description：RecyclerView item 长按处理
 * Created on 2017/2/25
 */

public interface RecyclerViewItemLongClickListener<T> {

    void OnItemLongClick(View view, T t, int position);

}
