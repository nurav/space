package com.spitspce.space;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Mustafa on 14/09/2014.
 */
public class VerticalImageView2 extends ImageView {
    public VerticalImageView2(Context context) {
        super(context);
    }

    public VerticalImageView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalImageView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()*354/265); //Snap to width
    }
}