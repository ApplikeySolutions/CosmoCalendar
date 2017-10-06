package com.applikeysolutions.cosmocalendar.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SelectionTypeDef {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SINGLE, MULTIPLE, RANGE, NONE})
    public @interface SelectionType{}

    public static final int SINGLE = 0;
    public static final int MULTIPLE = 1;
    public static final int RANGE = 2;
    public static final int NONE = 3;
}
