package com.wb.ygq.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.wb.ygq.R;


/**
 * 圆角ImageView
 * <p/>
 * Created by heinoc on 14-8-20.
 */
public class RoundCornerImageView extends ImageView {

    private final RectF roundRect = new RectF();
    protected int rect_adius = 20;
    private final Paint maskPaint = new Paint();
    private final Paint zonePaint = new Paint();

    private boolean roundCornerLeftTop = true;
    private boolean roundCornerLeftBottom = true;
    private boolean roundCornerRightTop = true;
    private boolean roundCornerRightBottom = true;

    public RoundCornerImageView(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    public RoundCornerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundCornerImageView);

        roundCornerLeftTop = mTypedArray.getBoolean(R.styleable.RoundCornerImageView_roundCornerLeftTop, true);
        roundCornerLeftBottom = mTypedArray.getBoolean(R.styleable.RoundCornerImageView_roundCornerLeftBottom, true);
        roundCornerRightTop = mTypedArray.getBoolean(R.styleable.RoundCornerImageView_roundCornerRightTop, true);
        roundCornerRightBottom = mTypedArray.getBoolean(R.styleable.RoundCornerImageView_roundCornerRightBottom, true);

        rect_adius = mTypedArray.getDimensionPixelSize(R.styleable.RoundCornerImageView_roundCornerRadius,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));

        mTypedArray.recycle();

        init();
    }

    public RoundCornerImageView(Context context) {
        super(context);
        init();
    }

    private void init() {
        maskPaint.setAntiAlias(true);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        zonePaint.setAntiAlias(true);
        zonePaint.setColor(Color.WHITE);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int w = getWidth();
        int h = getHeight();
        roundRect.set(0, 0, w, h);
    }

    @Override
    public void draw(Canvas canvas) {

        canvas.saveLayer(roundRect, zonePaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawRoundRect(roundRect, rect_adius, rect_adius, zonePaint);
        if (!roundCornerLeftTop) {
            // 屏蔽左上方圆角
            Rect leftTopRect = new Rect(0, 0, getWidth() / 2, getHeight() / 2);
            canvas.drawRect(leftTopRect, zonePaint);
        }
        if (!roundCornerRightTop) {
            // 屏蔽右上方圆角
            Rect rightTopRect = new Rect(getWidth() / 2, 0, getWidth(), getHeight() / 2);
            canvas.drawRect(rightTopRect, zonePaint);
        }
        if (!roundCornerLeftBottom) {
            // 屏蔽左下方圆角
            Rect leftBottomRect = new Rect(0, getHeight() / 2, getWidth() / 2, getHeight());
            canvas.drawRect(leftBottomRect, zonePaint);
        }
        if (!roundCornerRightBottom) {
            // 屏蔽右下方圆角
            Rect rightBottomRect = new Rect(getWidth() / 2, getHeight() / 2, getWidth(), getHeight());
            canvas.drawRect(rightBottomRect, zonePaint);
        }

        canvas.saveLayer(roundRect, maskPaint, Canvas.ALL_SAVE_FLAG);
        super.draw(canvas);
        canvas.restore();
    }

}