package com.applikeysolutions.cosmocalendar.view;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({ItemViewType.MONTH,
        ItemViewType.MONTH_DAY,
        ItemViewType.OTHER_MONTH_DAY,
        ItemViewType.DAY_OF_WEEK})
public @interface ItemViewType {
    int MONTH = 0;
    int MONTH_DAY = 1;
    int OTHER_MONTH_DAY = 2;
    int DAY_OF_WEEK = 3;
}
