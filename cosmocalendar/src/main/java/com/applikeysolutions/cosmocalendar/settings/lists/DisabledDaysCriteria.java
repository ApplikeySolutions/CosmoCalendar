package com.applikeysolutions.cosmocalendar.settings.lists;

import java.util.Set;
import java.util.TreeSet;

public class DisabledDaysCriteria {

    private DisabledDaysCriteriaType criteriaType = DisabledDaysCriteriaType.DAYS_OF_MONTH;
    private Set<Integer> days;

    private static final int MAX_DAYS_COUNT_IN_MONTH = 31;
    private static final int MAX_DAYS_COUNT_IN_WEEK = 7;

    public DisabledDaysCriteria(int startRange, int endRange, DisabledDaysCriteriaType criteriaType) {
        setDays(startRange, endRange, criteriaType);
    }

    public DisabledDaysCriteria(Set<Integer> days, DisabledDaysCriteriaType criteriaType) {
        setDays(days, criteriaType);
    }

    public DisabledDaysCriteriaType getCriteriaType() {
        return criteriaType;
    }

    public Set<Integer> getDays() {
        return days;
    }

    public void setDays(Set<Integer> days, DisabledDaysCriteriaType criteriaType) {
        this.criteriaType = criteriaType;
        validateDays(days);
        this.days = days;
    }

    /**
     * Sets range of disabled days
     * @param startRange
     * @param endRange
     */
    public void setDays(int startRange, int endRange, DisabledDaysCriteriaType criteriaType){
        if(criteriaType == DisabledDaysCriteriaType.DAYS_OF_MONTH && startRange >= endRange){
            throw new IllegalArgumentException("startRange must be less than endRange");
        }
        if(startRange < 1){
            throw new IllegalArgumentException("startRange must be more than 0");
        }
        if(endRange < 1){
            throw new IllegalArgumentException("endRange must be more than 0");
        }

        this.criteriaType = criteriaType;

        Set<Integer> days = new TreeSet<>();
        int start, end;
        if(startRange >= endRange){
            start = endRange;
            end = startRange;
        } else {
            start = startRange;
            end = endRange;
        }

        for(int i = start; i<end+1; i++){
            days.add(i);
        }
        validateDays(days);
        this.days = days;
    }

    private void validateDays(Set<Integer> days){
        int maxPossibleValue;
        switch (criteriaType){
            case DAYS_OF_MONTH:
                maxPossibleValue = MAX_DAYS_COUNT_IN_MONTH;
                break;

            default:
                maxPossibleValue = MAX_DAYS_COUNT_IN_WEEK;
                break;
        }

        for(int day : days){
            if(day > maxPossibleValue){
                throw new IllegalArgumentException("Invalid day:"+day);
            }
        }
    }
}
