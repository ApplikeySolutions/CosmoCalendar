package com.applikeysolutions.cosmocalendar.settings.selection;

import com.applikeysolutions.cosmocalendar.utils.SelectionTypeDef;

public class SelectionModel implements SelectionInterface {

    //Selecton type SINGLE, MULTIPLE, RANGE
    @SelectionTypeDef.SelectionType
    private int selectionType;

    @Override
    @SelectionTypeDef.SelectionType
    public int getSelectionType() {
        return selectionType;
    }

    @Override
    public void setSelectionType(@SelectionTypeDef.SelectionType int selectionType) {
        this.selectionType = selectionType;
    }
}
