package com.applikeysolutions.cosmocalendar.selection.criteria;

import com.applikeysolutions.cosmocalendar.model.Day;

import java.util.Calendar;

public class WeekDayCriteria extends BaseCriteria {

    private int weekDay = -1;

    public WeekDayCriteria(int weekDay) {
        if (weekDay >= 1 && weekDay <= 7) {
            this.weekDay = weekDay;
        } else {
            throw new IllegalArgumentException("Weekday must be from 1 to 7");
        }
    }

    @Override
    public boolean isCriteriaPassed(Day day) {
        return day.getCalendar().get(Calendar.DAY_OF_WEEK) == weekDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeekDayCriteria that = (WeekDayCriteria) o;
        return weekDay == that.weekDay;
    }

    @Override
    public int hashCode() {
        return weekDay;
    }
}
