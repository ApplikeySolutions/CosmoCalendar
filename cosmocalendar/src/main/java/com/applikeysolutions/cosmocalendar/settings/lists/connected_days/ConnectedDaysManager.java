package com.applikeysolutions.cosmocalendar.settings.lists.connected_days;

import com.applikeysolutions.cosmocalendar.model.Day;
import com.applikeysolutions.cosmocalendar.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ConnectedDaysManager {

    private static ConnectedDaysManager mInstance = null;

    private List<ConnectedDays> connectedDaysList;

    private ConnectedDaysManager(){

    }

    public static ConnectedDaysManager getInstance(){
        if(mInstance == null)
        {
            mInstance = new ConnectedDaysManager();
        }
        return mInstance;
    }

    public List<ConnectedDays> getConnectedDaysList() {
        return connectedDaysList;
    }

    public void setConnectedDaysList(List<ConnectedDays> connectedDaysList) {
        this.connectedDaysList = connectedDaysList;
    }

    public void addConnectedDays(ConnectedDays connectedDays){
        if(connectedDaysList == null){
            connectedDaysList = new ArrayList<>();
        }
        connectedDaysList.add(connectedDays);
    }

    public boolean isAnyConnectedDays(){
        return !(connectedDaysList == null || connectedDaysList.isEmpty());
    }

    public void applySettingsToDay(Day day){
        for (ConnectedDays connectedDays : connectedDaysList){
            for (long dayLong : connectedDays.getDays()){
                Calendar disabledDayCalendar = DateUtils.getCalendar(dayLong);
                if (day.getCalendar().get(Calendar.YEAR) == disabledDayCalendar.get(Calendar.YEAR)
                        && day.getCalendar().get(Calendar.DAY_OF_YEAR) == disabledDayCalendar.get(Calendar.DAY_OF_YEAR)) {
                    day.setFromConnectedCalendar(true);
                    day.setConnectedDaysTextColor(connectedDays.getTextColor());
                    day.setConnectedDaysSelectedTextColor(connectedDays.getSelectedTextColor());
                    day.setConnectedDaysDisabledTextColor(connectedDays.getDisabledTextColor());
                }
            }
        }
    }
}