package com.applikeysolutions.cosmocalendar.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.applikeysolutions.cosmocalendar.model.Day;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Calendar getCalendar(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        return calendar;
    }

    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = getCalendar(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = getCalendar(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    public static Date getFirstDayOfWeek(@NonNull Date date, int firstDayOfWeek) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.clear(Calendar.HOUR_OF_DAY);
        calendar.clear(Calendar.HOUR);
        while (calendar.get(Calendar.DAY_OF_WEEK) != firstDayOfWeek) {
            calendar.add(Calendar.DATE, -1);
        }
        return calendar.getTime();
    }

    public static Date getLastDayOfWeek(@Nullable Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.clear(Calendar.HOUR_OF_DAY);
        calendar.clear(Calendar.HOUR);
        if (calendar.get(Calendar.DAY_OF_MONTH) == calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
                && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return calendar.getTime();
        }
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMaximum(Calendar.DAY_OF_WEEK));
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, 1);
        }
        return calendar.getTime();
    }

    public static boolean isSameMonth(Calendar calendar1, Calendar calendar2) {
        return calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH);
    }

    public static boolean isSameDayOfMonth(Calendar calendar1, Calendar calendar2) {
        return calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
    }

    public static Calendar addMonth(Calendar calendar) {
        calendar.add(Calendar.MONTH, 1);
        return calendar;
    }

    public static Calendar addDay(Calendar calendar) {
        calendar.add(Calendar.DATE, 1);
        return calendar;
    }

    public static boolean isCurrentDate(Date date) {
        return date != null && android.text.format.DateUtils.isToday(date.getTime());
    }

    public static boolean isDayInRange(Day day, Day dayStart, Day dayEnd) {
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(dayStart.getCalendar().getTime());
        setCalendarToStartOfDay(calendarStart);

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(dayEnd.getCalendar().getTime());
        setCalendarToEndOfDay(calendarEnd);

        return day.getCalendar().getTimeInMillis() >= calendarStart.getTimeInMillis()
                && day.getCalendar().getTimeInMillis() <= calendarEnd.getTimeInMillis();
    }

    private static void setCalendarToStartOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private static void setCalendarToEndOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 59);
    }
}
