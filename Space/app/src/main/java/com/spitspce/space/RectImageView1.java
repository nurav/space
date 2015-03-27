package com.spitspce.space;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by abbas on 1/29/2015.
 */
public class RectImageView1 extends ImageView {
    public RectImageView1(Context context) {
        super(context);
    }

    public RectImageView1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RectImageView1(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()*354/500); //Snap to width
    }
}

