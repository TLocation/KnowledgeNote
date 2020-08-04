package com.example.customlayoutdemo;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TagLayoutView extends ViewGroup {
    List<Rect> childrenRounds = new ArrayList<>();

    public TagLayoutView(Context context) {
        this(context, null);
    }

    public TagLayoutView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthUsed = 0; //最终宽度
        int lineWidthUsed = 0; //每行的宽度
        int heightUsed = 0; //最终高度
        int lineHeightUsed = 0; //每行的高度
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            MarginLayoutParams layoutParams = (MarginLayoutParams) getChildAt(i).getLayoutParams();
            int childWidth = getChildAt(i).getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            int childHeight = getChildAt(i).getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            measureChildWithMargins(getChildAt(i), widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            if (widthMode != MeasureSpec.UNSPECIFIED && lineWidthUsed + childWidth > widthSize) {
                //换行
                lineWidthUsed = 0;
                heightUsed += lineHeightUsed;
                lineHeightUsed = 0;
                measureChildWithMargins(getChildAt(i), widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            }
            if (i >= childrenRounds.size()) {
                childrenRounds.add(new Rect());
            }
            Rect rect = childrenRounds.get(i);
            rect.set(lineWidthUsed, heightUsed, lineWidthUsed + getChildAt(i).getMeasuredWidth(), heightUsed + getChildAt(i).getMeasuredHeight());
            lineWidthUsed += childWidth;
            widthUsed = Math.max(widthUsed, lineWidthUsed);
            lineHeightUsed = Math.max(lineHeightUsed, childHeight);
        }
        setMeasuredDimension(widthUsed, heightUsed + lineHeightUsed);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            Rect childrenRect = childrenRounds.get(i);
            getChildAt(i).layout(childrenRect.left, childrenRect.top, childrenRect.right, childrenRect.bottom);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

}
