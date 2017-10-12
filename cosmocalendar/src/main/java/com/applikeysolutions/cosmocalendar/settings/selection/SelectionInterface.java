package com.applikeysolutions.cosmocalendar.settings.selection;

import com.applikeysolutions.cosmocalendar.utils.SelectionType;

public interface SelectionInterface {

    @SelectionType
    int getSelectionType();

    void setSelectionType(@SelectionType int selectionType);
}
