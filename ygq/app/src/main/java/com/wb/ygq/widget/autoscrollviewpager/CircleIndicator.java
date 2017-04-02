package com.wb.ygq.widget.autoscrollviewpager;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wb.ygq.R;


/**
 * Banner 圆点 指示器
 */
public class CircleIndicator extends View
{

	/**
	 * 选中状态
	 */
	private int mDrawableResSelected;

	/**
	 * 未选中
	 */
	private int mDrawableResNormal;

	/**
	 * 指示器
	 */
	private ImageView[] indicator;

	@Nullable
	private ViewPager viewPager;

	/**
	 * 指示器外层布局
	 */
	@Nullable
	private LinearLayout pageDotGroup;

	private int itemsCount;

	public CircleIndicator(Context context)
	{
		super(context);
	}

	/**
	 * 设置选中状态背景 R.drawable.xxx
	 */
	public void setmDrawableResSelected(int mDrawableResSelected)
	{
		this.mDrawableResSelected = mDrawableResSelected;

	}

	/**
	 * 设置未选中态背景 R.drawable.xxx
	 */
	public void setmDrawableResNormal(int mDrawableResNormal)
	{
		this.mDrawableResNormal = mDrawableResNormal;
	}

	public void setViewPager(@Nullable ViewPager viewPager)
	{
		this.viewPager = viewPager;
		if (viewPager == null || viewPager.getAdapter() == null) { throw new IllegalArgumentException(
				" CircleIndicator setViewpager viewPager or viewpager.getAdapter can not be null "); }
	}

	public void setItemsCount(int itemsCount)
	{
		this.itemsCount = itemsCount;
	}

	/**
	 * 指示器外层布局
	 */
	public void setPageDotGroup(@Nullable LinearLayout pageDotGroup)
	{
		if (pageDotGroup == null) { throw new IllegalArgumentException(" CircleIndicator setPageDotGroup pageDotGroup can not be null "); }
		this.pageDotGroup = pageDotGroup;
	}

	/**
	 * 必须先调用{@link #setViewPager(ViewPager)}
	 * {@link #setPageDotGroup(LinearLayout)} {@link #setmDrawableResNormal(int)}
	 * {@link #setmDrawableResSelected(int)}
	 */
	public void init()
	{
		if (viewPager == null || pageDotGroup == null) { throw new IllegalArgumentException("CircleIndicator init params error"); }

		if (itemsCount <= 1)
		{ // 少于一个元素 不作处理
			return;
		}

		indicator = new ImageView[itemsCount];
		for (int i = 0; i < itemsCount; i++)
		{
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			ImageView imageView = new ImageView(getContext());
			lp.setMargins(4, 0, 4, 0);
			lp.gravity = Gravity.CENTER_HORIZONTAL;
			imageView.setLayoutParams(lp);

			indicator[i] = imageView;
			pageDotGroup.addView(imageView);
		}
		setIndicatorSelected(0);

		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	public class MyOnPageChangeListener implements OnPageChangeListener
	{

		@Override
		public void onPageSelected(int position)
		{
			if (itemsCount == 0) { return; }
			setIndicatorSelected((position) % itemsCount);
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
		{}

		@Override
		public void onPageScrollStateChanged(int arg0)
		{}
	}

	/**
	 * 设置指示器选中状态
	 */
	public void setIndicatorSelected(int index)
	{
		if (mDrawableResNormal == 0)
		{
			mDrawableResNormal = R.mipmap.page_dot;
		}
		if (mDrawableResSelected == 0)
		{
			mDrawableResSelected = R.mipmap.page_dot_focus;
		}
		if (indicator == null) { return; }
		for (int i = 0; i < indicator.length; i++)
		{
			ImageView img = indicator[i];
			if (img != null) img.setBackgroundResource(i == index ? mDrawableResSelected : mDrawableResNormal);
		}
	}

}
