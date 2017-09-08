package com.applikeysolutions.cosmocalendar.settings.date;

public class DateModel implements DateInterface {

    //Defines day from which the week begins
    private int firstDayOfWeek;

    @Override
    public int getFirstDayOfWeek() {
        return firstDayOfWeek;
    }

    @Override
    public void setFirstDayOfWeek(int firstDayOfWeek) {
        this.firstDayOfWeek = firstDayOfWeek;
    }
}
