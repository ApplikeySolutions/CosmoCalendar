package com.applikeysolutions.cosmocalendar.settings.lists;

import java.util.Set;

public interface CalendarListsInterface {

    Set<Long> getDisabledDays();

    Set<Long> getConnectedCalendarDays();

    Set<Long> getWeekendDays();

    void setDisabledDays(Set<Long> disabledDays);

    void setConnectedCalendarDays(Set<Long> connectedCalendarDays);

    void setWeekendDays(Set<Long> weekendDays);
}
