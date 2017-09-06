package com.applikeysolutions.cosmocalendar.settings.selection;

public class SelectionModel implements SelectionInterface {

    //Selecton type SINGLE, MULTIPLE, RANGE
    private int selectionType;

    @Override
    public int getSelectionType() {
        return selectionType;
    }

    @Override
    public void setSelectionType(int selectionType) {
        this.selectionType = selectionType;
    }
}
