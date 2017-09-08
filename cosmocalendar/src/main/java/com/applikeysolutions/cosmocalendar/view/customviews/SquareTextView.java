package com.applikeysolutions.cosmocalendar.view.customviews;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;

public class SquareTextView extends AppCompatTextView {

    public SquareTextView(Context context) {
        super(context);
    }

    //Square view
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
