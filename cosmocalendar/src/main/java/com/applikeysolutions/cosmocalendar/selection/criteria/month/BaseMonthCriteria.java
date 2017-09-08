package com.applikeysolutions.cosmocalendar.selection.criteria.month;

import com.applikeysolutions.cosmocalendar.model.Day;
import com.applikeysolutions.cosmocalendar.selection.criteria.BaseCriteria;

import java.util.Calendar;

public abstract class BaseMonthCriteria extends BaseCriteria {

    protected abstract int getMonth();

    protected abstract int getYear();

    @Override
    public boolean isCriteriaPassed(Day day) {
        return day.getCalendar().get(Calendar.MONTH) == getMonth()
                && day.getCalendar().get(Calendar.YEAR) == getYear();
    }
}
