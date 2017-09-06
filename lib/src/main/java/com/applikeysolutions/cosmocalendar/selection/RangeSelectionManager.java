package com.applikeysolutions.cosmocalendar.selection;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import com.applikeysolutions.cosmocalendar.model.Day;
import com.applikeysolutions.cosmocalendar.utils.DateUtils;

public class RangeSelectionManager extends BaseSelectionManager {

    private Pair<Day, Day> days;
    private Day tempDay;

    public RangeSelectionManager(OnDaySelectedListener onDaySelectedListener) {
        this.onDaySelectedListener = onDaySelectedListener;
    }

    public Pair<Day, Day> getDays() {
        return days;
    }

    @Override
    public void toggleDay(@NonNull Day day) {
        if (days == null && tempDay == null || tempDay == null) {
            tempDay = day;
            days = null;
        } else {
            if (tempDay == day) {
                return;
            }
            if (tempDay.getCalendar().getTime().before(day.getCalendar().getTime())) {
                days = Pair.create(tempDay, day);
            } else {
                days = Pair.create(day, tempDay);
            }
            tempDay = null;
        }
        onDaySelectedListener.onDaySelected();
    }

    @Override
    public boolean isDaySelected(@NonNull Day day) {
        return isDaySelectedManually(day);
    }

    private boolean isDaySelectedManually(@NonNull Day day) {
        if (tempDay != null) {
            return day.equals(tempDay);
        } else if (days != null) {
            return DateUtils.isDayInRange(day, days.first, days.second);
        } else {
            return false;
        }
    }

    @Override
    public void clearSelections() {
        days = null;
        tempDay = null;
    }

    public SelectionState getSelectedState(Day day) {
        if (!isDaySelectedManually(day)) {
            return SelectionState.SINGLE_DAY;
        }

        if (days == null) {
            return SelectionState.START_RANGE_DAY_WITHOUT_END;
        } else if (days.first.equals(day)) {
            return SelectionState.START_RANGE_DAY;
        } else if (days.second.equals(day)) {
            return SelectionState.END_RANGE_DAY;
        } else if (DateUtils.isDayInRange(day, days.first, days.second)) {
            return SelectionState.RANGE_DAY;
        } else {
            return SelectionState.SINGLE_DAY;
        }
    }
}
