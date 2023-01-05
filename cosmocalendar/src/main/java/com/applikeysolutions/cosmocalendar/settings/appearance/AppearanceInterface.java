package com.applikeysolutions.cosmocalendar.settings.appearance;

import android.support.annotation.StyleRes;

public interface AppearanceInterface {

    int getCalendarBackgroundColor();

    int getMonthTextColor();

    int getOtherDayTextColor();

    int getDayTextColor();

    int getWeekendDayTextColor();

    int getWeekDayTitleTextColor();

    int getSelectedDayTextColor();

    int getSelectedDayBackgroundColor();

    int getSelectedDayBackgroundStartColor();

    int getSelectedDayBackgroundEndColor();

    int getCurrentDayTextColor();

    int getCurrentDayIconRes();

    int getCurrentDaySelectedIconRes();

    int getCalendarOrientation();

    int getConnectedDayIconRes();

    int getConnectedDaySelectedIconRes();

    int getConnectedDayIconPosition();

    int getDisabledDayTextColor();

    int getSelectionBarMonthTextColor();

    int getPreviousMonthIconRes();

    int getNextMonthIconRes();

    boolean getOtherDayVisibility();

    @StyleRes
    int getDayTextAppearance();

    @StyleRes
    int getWeekTextAppearance();

    @StyleRes
    int getMonthTextAppearance();

    boolean isShowDaysOfWeek();

    boolean isShowDaysOfWeekTitle();

    void setCalendarBackgroundColor(int calendarBackgroundColor);

    void setMonthTextColor(int monthTextColor);

    void setOtherDayTextColor(int otherDayTextColor);

    void setDayTextColor(int dayTextColor);

    void setWeekendDayTextColor(int weekendDayTextColor);

    void setWeekDayTitleTextColor(int weekDayTitleTextColor);

    void setSelectedDayTextColor(int selectedDayTextColor);

    void setSelectedDayBackgroundColor(int selectedDayBackgroundColor);

    void setSelectedDayBackgroundStartColor(int selectedDayBackgroundStartColor);

    void setSelectedDayBackgroundEndColor(int selectedDayBackgroundEndColor);

    void setCurrentDayTextColor(int currentDayTextColor);

    void setCurrentDayIconRes(int currentDayIconRes);

    void setCurrentDaySelectedIconRes(int currentDaySelectedIconRes);

    void setCalendarOrientation(int calendarOrientation);

    void setConnectedDayIconRes(int connectedDayIconRes);

    void setConnectedDaySelectedIconRes(int connectedDaySelectedIconRes);

    void setConnectedDayIconPosition(int connectedDayIconPosition);

    void setDisabledDayTextColor(int disabledDayTextColor);

    void setSelectionBarMonthTextColor(int selectionBarMonthTextColor);

    void setPreviousMonthIconRes(int previousMonthIconRes);

    void setNextMonthIconRes(int nextMonthIconRes);

    void setShowDaysOfWeek(boolean showDaysOfWeek);

    void setShowDaysOfWeekTitle(boolean showDaysOfWeekTitle);

    void setDayTextAppearance(@StyleRes int dayTextAppearance);

    void setWeekTextAppearance(@StyleRes int weekTextAppearance);

    void setMonthTextAppearance(@StyleRes int monthTextAppearance);

    void setOtherDayVisibility(boolean isVisible);
}
