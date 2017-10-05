package com.applikeysolutions.cosmocalendar.adapter.viewholder;

import android.graphics.BitmapFactory;
import android.view.View;

import com.applikeysolutions.customizablecalendar.R;
import com.applikeysolutions.cosmocalendar.model.Day;
import com.applikeysolutions.cosmocalendar.selection.BaseSelectionManager;
import com.applikeysolutions.cosmocalendar.selection.RangeSelectionManager;
import com.applikeysolutions.cosmocalendar.selection.SelectionState;
import com.applikeysolutions.cosmocalendar.view.CalendarView;
import com.applikeysolutions.cosmocalendar.view.customviews.CircleAnimationTextView;

public class DayHolder extends BaseDayHolder {

    private CircleAnimationTextView ctvDay;
    private BaseSelectionManager selectionManager;

    public DayHolder(View itemView, CalendarView calendarView) {
        super(itemView, calendarView);
        ctvDay = (CircleAnimationTextView) itemView.findViewById(R.id.tv_day_number);
    }

    public void bind(Day day, BaseSelectionManager selectionManager) {
        this.selectionManager = selectionManager;
        ctvDay.setText(String.valueOf(day.getDayNumber()));

        boolean isSelected = selectionManager.isDaySelected(day);
        if (isSelected && !day.isDisabled()) {
            select(day);
        } else {
            unselect(day);
        }

        if (day.isCurrent()) {
            ctvDay.setCompoundDrawablePadding(getCurrentDayIconHeight(isSelected) * -1);
            ctvDay.setCompoundDrawablesWithIntrinsicBounds(0, isSelected
                    ? calendarView.getCurrentDaySelectedIconRes()
                    : calendarView.getCurrentDayIconRes(), 0, 0);
        } else {
            ctvDay.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }

        if(day.isDisabled()){
            ctvDay.setTextColor(calendarView.getDisabledDayTextColor());
        }
    }

    private int getCurrentDayIconHeight(boolean isSelected){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        if (isSelected) {
            BitmapFactory.decodeResource(calendarView.getContext().getResources(), calendarView.getCurrentDaySelectedIconRes(), options);
        } else {
            BitmapFactory.decodeResource(calendarView.getContext().getResources(), calendarView.getCurrentDayIconRes(), options);
        }
        return options.outHeight;
    }

    private void select(Day day) {
        if (day.isFromConnectedCalendar()) {
            ctvDay.setTextColor(calendarView.getConnectedDaySelectedTextColor());
        } else {
            ctvDay.setTextColor(calendarView.getSelectedDayTextColor());
        }

        SelectionState state;
        if (selectionManager instanceof RangeSelectionManager) {
            state = ((RangeSelectionManager) selectionManager).getSelectedState(day);
        } else {
            state = SelectionState.SINGLE_DAY;
        }
        animateDay(state, day);
    }

    private void animateDay(SelectionState state, Day day) {
        if (day.getSelectionState() != state) {
            if (day.isSelectionCircleDrawed() && state == SelectionState.SINGLE_DAY) {
                ctvDay.showAsSingleCircle(calendarView);
            } else if (day.isSelectionCircleDrawed() && state == SelectionState.START_RANGE_DAY) {
                ctvDay.showAsStartCircle(calendarView, false);
            } else if (day.isSelectionCircleDrawed() && state == SelectionState.END_RANGE_DAY) {
                ctvDay.showAsEndCircle(calendarView, false);
            } else {
                ctvDay.setSelectionStateAndAnimate(state, calendarView, day);
            }
        } else {
            switch (state) {
                case SINGLE_DAY:
                    if (day.isSelectionCircleDrawed()) {
                        ctvDay.showAsSingleCircle(calendarView);
                    } else {
                        ctvDay.setSelectionStateAndAnimate(state, calendarView, day);
                    }
                    break;

                case RANGE_DAY:
                    ctvDay.setSelectionStateAndAnimate(state, calendarView, day);
                    break;

                case START_RANGE_DAY_WITHOUT_END:
                    if (day.isSelectionCircleDrawed()) {
                        ctvDay.showAsStartCircleWithouEnd(calendarView, false);
                    } else {
                        ctvDay.setSelectionStateAndAnimate(state, calendarView, day);
                    }
                    break;

                case START_RANGE_DAY:
                    if (day.isSelectionCircleDrawed()) {
                        ctvDay.showAsStartCircle(calendarView, false);
                    } else {
                        ctvDay.setSelectionStateAndAnimate(state, calendarView, day);
                    }
                    break;

                case END_RANGE_DAY:
                    if (day.isSelectionCircleDrawed()) {
                        ctvDay.showAsEndCircle(calendarView, false);
                    } else {
                        ctvDay.setSelectionStateAndAnimate(state, calendarView, day);
                    }
                    break;
            }
        }
    }

    private void unselect(Day day) {
        int textColor;
        if (day.isFromConnectedCalendar()) {
            textColor = calendarView.getConnectedDayTextColor();
        } else if (day.isWeekend()) {
            textColor = calendarView.getWeekendDayTextColor();
        } else {
            textColor = calendarView.getDayTextColor();
        }
        day.setSelectionCircleDrawed(false);
        ctvDay.setTextColor(textColor);
        ctvDay.clearView();
    }
}
