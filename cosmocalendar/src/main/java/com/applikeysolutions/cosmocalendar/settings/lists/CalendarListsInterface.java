package com.applikeysolutions.cosmocalendar.settings.lists;

import com.applikeysolutions.cosmocalendar.settings.lists.connected_days.ConnectedDays;
import com.applikeysolutions.cosmocalendar.settings.lists.connected_days.ConnectedDaysManager;

import java.util.Calendar;
import java.util.Set;

public interface CalendarListsInterface {

    Calendar getEnableMinDate();

    Calendar getEnableMaxDate();

    Calendar getVisibleMinDate();

    Calendar getVisibleMaxDate();

    Set<Long> getDisabledDays();

    ConnectedDaysManager getConnectedDaysManager();

    Set<Long> getWeekendDays();

    DisabledDaysCriteria getDisabledDaysCriteria();

    void setDisabledDays(Set<Long> disabledDays);

    void setEnableMinDate(Calendar minDate);

    void setEnableMaxDate(Calendar maxDate);

    void setVisibleMinDate(Calendar minDate);

    void setVisibleMaxDate(Calendar maxDate);

    void setWeekendDays(Set<Long> weekendDays);

    void setDisabledDaysCriteria(DisabledDaysCriteria criteria);


    void addConnectedDays(ConnectedDays connectedDays);
}
