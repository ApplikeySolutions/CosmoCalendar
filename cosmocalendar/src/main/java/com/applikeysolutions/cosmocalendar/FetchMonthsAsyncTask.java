package com.applikeysolutions.cosmocalendar;

import android.os.AsyncTask;

import com.applikeysolutions.cosmocalendar.adapter.MonthAdapter;
import com.applikeysolutions.cosmocalendar.model.Month;
import com.applikeysolutions.cosmocalendar.settings.SettingsManager;
import com.applikeysolutions.cosmocalendar.utils.CalendarUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by leonardo on 08/10/17.
 */

public class FetchMonthsAsyncTask extends AsyncTask<FetchMonthsAsyncTask.FetchParams, Void, List<Month>> {

    private boolean future;
    private MonthAdapter monthAdapter;
    private int defaultMonthCount;

    @Override
    protected List<Month> doInBackground(FetchParams... fetchParams) {
        FetchParams params = fetchParams[0];
        Month month = params.month;
        future = params.future;
        SettingsManager settingsManager = params.settingsManager;
        monthAdapter = params.monthAdapter;
        defaultMonthCount = params.defaultMonthCount;

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(month.getFirstDay().getCalendar().getTime());
        final List<Month> result = new ArrayList<>();
        Integer monthsToLoad = SettingsManager.DEFAULT_MONTH_COUNT;
        if(future && params.maxDate != null){
            Integer difference = CalendarUtils.getDifferenceBetweenMonths(month.getFirstDay().getCalendar(), params.maxDate);
            if (difference < monthsToLoad)
                monthsToLoad = difference;
        }
        if (!future && params.minDate != null){
            Integer difference = CalendarUtils.getDifferenceBetweenMonths(params.minDate, month.getFirstDay().getCalendar());
            if (difference < monthsToLoad)
                monthsToLoad = difference;
        }
        for (int i = 0; i < monthsToLoad; i++) {
            if(isCancelled())
                break;

            calendar.add(Calendar.MONTH, future ? 1 : -1);
            Month newMonth = CalendarUtils.createMonth(calendar.getTime(), settingsManager);
            if (future) {
                result.add(newMonth);
            } else {
                result.add(0, newMonth);
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(List<Month> months) {
        if (!months.isEmpty()) {
            if (future) {
                monthAdapter.getData().addAll(months);
                monthAdapter.notifyItemRangeInserted(monthAdapter.getData().size() - 1, defaultMonthCount);
            } else {
                monthAdapter.getData().addAll(0, months);
                monthAdapter.notifyItemRangeInserted(0, defaultMonthCount);
            }
        }
    }

    public static class FetchParams {
        private final boolean future;
        private final Month month;
        private final SettingsManager settingsManager;
        private final MonthAdapter monthAdapter;
        private final int defaultMonthCount;
        private final Calendar minDate;
        private final Calendar maxDate;

        public FetchParams(boolean future, Month month, SettingsManager settingsManager, MonthAdapter monthAdapter, int defaultMonthCount, Calendar minDate, Calendar maxDate) {
            this.future = future;
            this.month = month;
            this.settingsManager = settingsManager;
            this.monthAdapter = monthAdapter;
            this.defaultMonthCount = defaultMonthCount;
            this.minDate = minDate;
            this.maxDate = maxDate;
        }
    }
}
