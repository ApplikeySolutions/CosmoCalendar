package com.applikeysolutions.cosmocalendar.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.view.WindowManager;

import com.applikeysolutions.cosmocalendar.settings.SettingsManager;
import com.applikeysolutions.cosmocalendar.model.Day;
import com.applikeysolutions.cosmocalendar.model.DayOfWeek;
import com.applikeysolutions.cosmocalendar.model.Month;
import com.applikeysolutions.cosmocalendar.selection.selectionbar.SelectionBarContentItem;
import com.applikeysolutions.cosmocalendar.selection.selectionbar.SelectionBarItem;
import com.applikeysolutions.cosmocalendar.selection.selectionbar.SelectionBarTitleItem;
import com.applikeysolutions.cosmocalendar.settings.lists.DisabledDaysCriteria;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public final class CalendarUtils {

    public static Month createMonth(Date date, SettingsManager settingsManager) {
        final List<Day> days = new ArrayList<>();

        final Calendar firstDisplayedDayCalendar = Calendar.getInstance();
        final Calendar firstDayOfMonthCalendar = Calendar.getInstance();

        //First day that belongs to month
        final Date firstDayOfMonth = DateUtils.getFirstDayOfMonth(date);
        firstDayOfMonthCalendar.setTime(firstDayOfMonth);
        firstDayOfMonthCalendar.get(Calendar.MONTH);
        int targetMonth = firstDayOfMonthCalendar.get(Calendar.MONTH);

        //First displayed day, can belong to previous month
        final Date firstDisplayedDay = DateUtils.getFirstDayOfWeek(firstDayOfMonth, settingsManager.getFirstDayOfWeek());
        firstDisplayedDayCalendar.setTime(firstDisplayedDay);

        final Calendar end = Calendar.getInstance();
        end.setTime(DateUtils.getLastDayOfWeek(DateUtils.getLastDayOfMonth(date)));

        //Create week day titles
        if (settingsManager.isShowDaysOfWeek()) {
            days.addAll(createDaysOfWeek(firstDisplayedDay));
        }

        //Create first day of month
        days.add(createDay(firstDisplayedDayCalendar, settingsManager, targetMonth));

        //Create other days in month
        do {
            DateUtils.addDay(firstDisplayedDayCalendar);
            days.add(createDay(firstDisplayedDayCalendar, settingsManager, targetMonth));
        } while (!DateUtils.isSameDayOfMonth(firstDisplayedDayCalendar, end)
                || !DateUtils.isSameMonth(firstDisplayedDayCalendar, end));

        return new Month(createDay(firstDayOfMonthCalendar, settingsManager, targetMonth), days);
    }

    private static Day createDay(Calendar calendar, SettingsManager settingsManager, int targetMonth) {
        Day day = new Day(calendar);
        day.setBelongToMonth(calendar.get(Calendar.MONTH) == targetMonth);
        CalendarUtils.setDay(day, settingsManager);
        return day;
    }

    private static List<DayOfWeek> createDaysOfWeek(Date firstDisplayedDay) {
        final List<DayOfWeek> daysOfTheWeek = new ArrayList<>();

        final Calendar calendar = DateUtils.getCalendar(firstDisplayedDay);
        final int startDayOfTheWeek = calendar.get(Calendar.DAY_OF_WEEK);
        do {
            daysOfTheWeek.add(new DayOfWeek(calendar.getTime()));
            DateUtils.addDay(calendar);
        } while (calendar.get(Calendar.DAY_OF_WEEK) != startDayOfTheWeek);
        return daysOfTheWeek;
    }

    public static List<String> createWeekDayTitles(int firstDayOfWeek) {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DAY_NAME_FORMAT, Locale.getDefault());
        final List<String> titles = new ArrayList<>();

        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);
        do {
            titles.add(sdf.format(calendar.getTime()));
            DateUtils.addDay(calendar);
        } while (calendar.get(Calendar.DAY_OF_WEEK) != firstDayOfWeek);
        return titles;
    }

    public static List<Month> createInitialMonths(SettingsManager settingsManager) {
        final List<Month> months = new ArrayList<>();

        final Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < SettingsManager.DEFAULT_MONTH_COUNT / 2; i++) {
            calendar.add(Calendar.MONTH, -1);
        }

        for (int i = 0; i < SettingsManager.DEFAULT_MONTH_COUNT; i++) {
            months.add(createMonth(calendar.getTime(), settingsManager));
            DateUtils.addMonth(calendar);
        }
        return months;
    }

    /**
     * Returns selected Days grouped by month/year
     *
     * @param selectedDays
     * @return
     */
    public static List<SelectionBarItem> getSelectedDayListForMultipleMode(List<Day> selectedDays) {
        List<SelectionBarItem> result = new ArrayList<>();

        Calendar tempCalendar = Calendar.getInstance();
        int tempYear = -1;
        int tempMonth = -1;
        for (Day day : selectedDays) {
            tempCalendar.setTime(day.getCalendar().getTime());
            if (tempCalendar.get(Calendar.YEAR) != tempYear || tempCalendar.get(Calendar.MONTH) != tempMonth) {
                result.add(new SelectionBarTitleItem(getYearNameTitle(day)));
                tempYear = tempCalendar.get(Calendar.YEAR);
                tempMonth = tempCalendar.get(Calendar.MONTH);
            }
            result.add(new SelectionBarContentItem(day));
        }
        return result;
    }

    public static String getYearNameTitle(Day day) {
        return new SimpleDateFormat("MMM''yy").format(day.getCalendar().getTime());
    }

    /**
     * Returns width of circle
     *
     * @return
     */
    public static int getCircleWidth(Context context) {
        return getDisplayWidth(context) / Constants.DAYS_IN_WEEK;
    }

    public static int getDisplayWidth(Context context) {
        return ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
    }

    /**
     * Sets variables(isWeekend, isDisabled, isFromConnectedCalendar) to day
     */
    public static void setDay(Day day, SettingsManager settingsManager) {
        if (settingsManager.getWeekendDays() != null) {
            day.setWeekend(settingsManager.getWeekendDays().contains(day.getCalendar().get(Calendar.DAY_OF_WEEK)));
        }

        if (settingsManager.getDisabledDays() != null) {
            day.setDisabled(isDayInSet(day, settingsManager.getDisabledDays()));
        }

        if (settingsManager.getDisabledDaysCriteria() != null) {
            if(!day.isDisabled()){
                day.setDisabled(isDayDisabledByCriteria(day, settingsManager.getDisabledDaysCriteria()));
            }
        }

        if (settingsManager.getConnectedDaysManager().isAnyConnectedDays()) {
            settingsManager.getConnectedDaysManager().applySettingsToDay(day);
        }
    }

    public static boolean isDayInSet(Day day, Set<Long> daysInSet) {
        for (long disabledTime : daysInSet) {
            Calendar disabledDayCalendar = DateUtils.getCalendar(disabledTime);
            if (day.getCalendar().get(Calendar.YEAR) == disabledDayCalendar.get(Calendar.YEAR)
                    && day.getCalendar().get(Calendar.DAY_OF_YEAR) == disabledDayCalendar.get(Calendar.DAY_OF_YEAR)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDayDisabledByCriteria(Day day, DisabledDaysCriteria criteria) {
        int field = -1;
        switch (criteria.getCriteriaType()){
            case DAYS_OF_MONTH:
                field = Calendar.DAY_OF_MONTH;
                break;

            case DAYS_OF_WEEK:
                field = Calendar.DAY_OF_WEEK;
                break;
        }

        for(int dayInt : criteria.getDays()){
            if(dayInt == day.getCalendar().get(field)){
                return true;
            }
        }
        return false;
    }

    public static int getIconHeight(Resources resources, int iconResId){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, iconResId, options);
        return options.outHeight;
    }
}