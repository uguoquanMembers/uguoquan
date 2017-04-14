package com.wb.ygq.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author mei
 *         在Android开发当中经常会用到了需要ScrollView嵌套GridView或者是ListView的情况，由于这两款控件都自带滚动条
 *         ，当他们一起使用的时候就会出现GridView或ListView会显示不全的问题 自定义GridView处理此问题
 */
public class MyGridView extends GridView {
	public MyGridView(Context context) {
		super(context);
	}

	public MyGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
