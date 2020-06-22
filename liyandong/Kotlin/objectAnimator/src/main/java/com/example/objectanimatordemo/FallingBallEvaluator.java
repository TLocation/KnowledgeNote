package com.example.objectanimatordemo;

import android.animation.TypeEvaluator;
import android.graphics.Point;

public class FallingBallEvaluator implements TypeEvaluator<Point> {
    //蹦蹦求返回值
    private Point mPoint = new Point();

    @Override
    public Point evaluate(float fraction, Point startValue, Point endValue) {
        int x = (int) (startValue.x + (fraction * (endValue.x - startValue.x)));
        mPoint.x = x;
        if (fraction * 2 <= 1) {
            int y = (int) (startValue.y + (fraction * 2 * (endValue.y - startValue.y)));
            mPoint.y = y;
        } else {
            mPoint.y = endValue.y;
        }
        return mPoint;
    }
}
