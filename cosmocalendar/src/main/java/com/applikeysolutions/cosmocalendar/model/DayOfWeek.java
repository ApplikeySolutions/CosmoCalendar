package com.applikeysolutions.cosmocalendar.model;

import com.applikeysolutions.cosmocalendar.utils.Constants;

import java.util.Date;

public class DayOfWeek extends Day {
    public enum WeekdayFormat {
        SHORT,
        MEDIUM;

        public String getFormat() {
            switch (this) {
                case SHORT:
                    return "E";

                case MEDIUM:
                    return "EEE";
            }

            return Constants.DAY_NAME_FORMAT;
        }
    }

    public DayOfWeek(Date date) {
        super(date);
    }
}
