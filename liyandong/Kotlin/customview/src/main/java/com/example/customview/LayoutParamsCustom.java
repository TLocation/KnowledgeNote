package com.example.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class LayoutParamsCustom extends ViewGroup {
    public LayoutParamsCustom(Context context) {
        super(context);
    }

    public LayoutParamsCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LayoutParamsCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
