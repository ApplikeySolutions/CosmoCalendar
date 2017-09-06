package com.applikeysolutions.cosmocalendar.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

public class SlowdownRecyclerView extends RecyclerView {

    // Change pow to control speed.
    // Bigger = faster. RecyclerView default is 5.
    private static final int POW = 2;

    private Interpolator interpolator;

    public SlowdownRecyclerView(Context context) {
        super(context);
        createInterpolator();
    }

    public SlowdownRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        createInterpolator();
    }

    public SlowdownRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        createInterpolator();
    }

    private void createInterpolator() {
        interpolator = new Interpolator() {
            @Override
            public float getInterpolation(float t) {
                t = Math.abs(t - 1.0f);
                return (float) (1.0f - Math.pow(t, POW));
            }
        };
    }

    @Override
    public void smoothScrollBy(int dx, int dy) {
        super.smoothScrollBy(dx, dy, interpolator);
    }
}
