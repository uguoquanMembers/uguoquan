package com.wb.ygq.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.wb.ygq.R;

/**
 * Created by xiaohe on 2017/4/13 0013.
 */

public class HorizontalProgressBar extends ProgressBar  {
    /**
     * 默认字体大小
     */
    private static final int DEFAULT_TEXT_SIZE = 10;//sp
    /**
     * 默认字体颜色
     */
    private static final int DEFAULT_TEXT_COLOR = 0xFFC00D1;
    /**
     * 默认未到达的颜色
     */
    private static final int DEFAULT_COLOR_UNREACH = 0xffD3d6da;
    /**
     * 未到达的线高
     */
    private static final int DEFAULT_HEIGHT_UNREACH = 2;//dp
    /**
     * 到达的线颜色
     */
    private static final int DEFAULT_COLOR_REACH = DEFAULT_TEXT_COLOR;
    /**
     * 到达的线高度
     */
    private static final int DEFAULT_HEIGHT_REACH = 2;//dp
    /**
     * 文字与两边进度条的间距
     */
    private static final int DEFAULT_TEXT_OFFSET = 0;//dp
    /**
     * 文字背景颜色
     */
    private static final int DEFAULT_TEXT_BG_COLOR = 0xffff4081;
    /**
     * 开始的数字
     */
    private static final String DEFAULT_TEXT_START = "0";
    /**
     * 结束的数字
     */
    private static final String DEFAULT_TEXT_END = "10";


    private int mTextSize = sp2px(DEFAULT_TEXT_SIZE);
    private int mTextColor = DEFAULT_TEXT_COLOR;
    private int mUnReachColor = DEFAULT_COLOR_UNREACH;
    private int mUnReachHeight = dp2px(DEFAULT_HEIGHT_UNREACH);
    private int mReachColor = DEFAULT_COLOR_REACH;
    private int mReachHeight = dp2px(DEFAULT_HEIGHT_REACH);
    private int mTextOffset = dp2px(DEFAULT_TEXT_OFFSET);
    private int mTextBgColor = DEFAULT_TEXT_BG_COLOR;
    private String mTextStart = DEFAULT_TEXT_START;
    private String mTextEnd = DEFAULT_TEXT_END;

    private Paint mPaint = new Paint();

    /**
     * 真正的宽度
     */
    private int mRealWhith;

    public HorizontalProgressBar(Context context) {
        this(context, null);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainStyledAttrs(attrs);
    }

    /**
     * 获取自定义属性
     *
     * @param attrs
     */
    private void obtainStyledAttrs(AttributeSet attrs) {

        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.HorizontalProgressBar);

        mTextSize = (int) ta.getDimension(R.styleable.HorizontalProgressBar_progress_text_size, mTextSize);

        mTextColor = ta.getColor(R.styleable.HorizontalProgressBar_progress_text_color, mTextColor);

        mTextOffset = (int) ta.getDimension(R.styleable.HorizontalProgressBar_progress_text_offset, mTextOffset);

        mReachColor = ta.getColor(R.styleable.HorizontalProgressBar_progress_reach_color, mReachColor);

        mReachHeight = (int) ta.getDimension(R.styleable.HorizontalProgressBar_progress_reach_height, mReachHeight);

        mUnReachColor = ta.getColor(R.styleable.HorizontalProgressBar_progress_unreach_color, mUnReachColor);

        mUnReachHeight = (int) ta.getDimension(R.styleable.HorizontalProgressBar_progress_unreach_height, mUnReachHeight);

        mTextBgColor = ta.getColor(R.styleable.HorizontalProgressBar_progress_text_bg_color, mTextBgColor);

        mTextStart = ta.getString(R.styleable.HorizontalProgressBar_progress_text_start);

        mTextEnd = ta.getString(R.styleable.HorizontalProgressBar_progress_text_end);

        ta.recycle();

        mPaint.setTextSize(mTextSize);
    }


    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthVal = MeasureSpec.getSize(widthMeasureSpec);

        int height = measureHeight(heightMeasureSpec);

        setMeasuredDimension(widthVal, height);

        mRealWhith = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }

    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            int textHeight = (int) (mPaint.descent() - mPaint.ascent());
            result = getPaddingTop() + getPaddingBottom()
                    + Math.max(Math.max(mReachHeight, mUnReachHeight), Math.abs(textHeight));
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }

        return result;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {

        canvas.save();

        canvas.translate(getPaddingLeft(), getHeight() / 2);

        //是否需要绘制unreachbar
        boolean noNeedUnReach = false;

        //draw ReachBar
        float radio = getProgress() * 1.0f / getMax();
        String text = (getProgress()+1) + "";
        int textWidth = (int) mPaint.measureText(text);
        float progressX = radio * mRealWhith;
        if (progressX + mPaint.getTextSize() * 3 / 4 > mRealWhith) {
            noNeedUnReach = true;
            progressX = mRealWhith - mPaint.getTextSize() * 3 / 8;
        }
        //mPaint.getTextSize()

        //reachbar的长度

        float endX = progressX - mTextOffset / 2;
        if (endX > 0) {
            mPaint.setColor(mReachColor);
            mPaint.setStrokeWidth(mReachHeight);
            canvas.drawLine(mPaint.getTextSize() * 3 / 4, 0, endX - mPaint.getTextSize() * 3 / 8, 0, mPaint);
        }


        //draw unreachBar
        if (!noNeedUnReach) {
            float start = progressX + mTextOffset / 2 + textWidth;
            mPaint.setColor(mUnReachColor);
            mPaint.setStrokeWidth(mUnReachHeight);
            canvas.drawLine(start, 0, mRealWhith, 0, mPaint);

        }

        //draw textbackground

        mPaint.setColor(mTextBgColor);
        mPaint.setAntiAlias(true);
        canvas.drawCircle(progressX + mPaint.getTextSize() * 1 / 4, 0, mPaint.getTextSize(), mPaint);

        //开始的圆
        canvas.drawCircle(mPaint.getTextSize() * 1 / 4, 0, mPaint.getTextSize(), mPaint);


        //draw text
        mPaint.setColor(mTextColor);
        int y = (int) (-(mPaint.descent() + mPaint.ascent()) / 2);
        if (!noNeedUnReach) {
            canvas.drawText(text, progressX, y, mPaint);
        } else {
            canvas.drawText(text, progressX - mPaint.getTextSize() * 3 / 8, y, mPaint);
        }
        //开始的字
        canvas.drawText(mTextStart + "", 0, y, mPaint);

        //结束的圆
        mPaint.setColor(mTextBgColor);
        canvas.drawCircle(mRealWhith - mPaint.getTextSize() * 1 / 8, 0, mPaint.getTextSize(), mPaint);

        //结束的字
        mPaint.setColor(mTextColor);
        canvas.drawText(mTextEnd + "", mRealWhith - mPaint.getTextSize() * 6 / 8, y, mPaint);

        canvas.restore();

    }

    private int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, getResources().getDisplayMetrics());
    }

    private int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, getResources().getDisplayMetrics());
    }

    public void setText(String start,String end){
        this.mTextStart=start;
        this.mTextEnd=end;
        invalidate();
    }

}
