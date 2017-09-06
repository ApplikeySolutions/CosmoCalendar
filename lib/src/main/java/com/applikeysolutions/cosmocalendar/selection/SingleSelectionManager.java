package com.applikeysolutions.cosmocalendar.selection;

import android.support.annotation.NonNull;

import com.applikeysolutions.cosmocalendar.model.Day;

public class SingleSelectionManager extends BaseSelectionManager {

    private Day day;

    public SingleSelectionManager(OnDaySelectedListener onDaySelectedListener) {
        this.onDaySelectedListener = onDaySelectedListener;
    }

    @Override
    public void toggleDay(@NonNull Day day) {
        this.day = day;
        onDaySelectedListener.onDaySelected();
    }

    @Override
    public boolean isDaySelected(@NonNull Day day) {
        return day.equals(this.day);
    }

    @Override
    public void clearSelections() {
        day = null;
    }
}
