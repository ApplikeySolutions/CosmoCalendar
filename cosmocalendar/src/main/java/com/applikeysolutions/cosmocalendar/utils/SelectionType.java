package com.applikeysolutions.cosmocalendar.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({SelectionType.SINGLE, SelectionType.MULTIPLE, SelectionType.RANGE, SelectionType.NONE})
public @interface SelectionType {
    int SINGLE = 0;
    int MULTIPLE = 1;
    int RANGE = 2;
    int NONE = 3;
}
