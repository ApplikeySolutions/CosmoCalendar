package com.applikeysolutions.cosmocalendar.adapter.viewholder;

import android.support.v4.widget.TextViewCompat;
import android.view.View;
import android.widget.TextView;

import com.applikeysolutions.customizablecalendar.R;
import com.applikeysolutions.cosmocalendar.model.Day;
import com.applikeysolutions.cosmocalendar.view.CalendarView;

public class OtherDayHolder extends BaseDayHolder {

    public OtherDayHolder(View itemView, CalendarView calendarView) {
        super(itemView, calendarView);
        tvDay = (TextView) itemView.findViewById(R.id.tv_day_number);
    }

    public void bind(Day day) {
        TextViewCompat.setTextAppearance(tvDay, calendarView.getDayTextAppearance());
        tvDay.setText(String.valueOf(day.getDayNumber()));
        tvDay.setTextColor(calendarView.getOtherDayTextColor());
        tvDay.setVisibility(calendarView.getOtherDayVisibility() ? View.VISIBLE : View.INVISIBLE);
    }
}
