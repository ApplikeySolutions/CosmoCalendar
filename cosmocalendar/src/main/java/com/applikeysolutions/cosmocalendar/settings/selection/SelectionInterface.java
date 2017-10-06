package com.applikeysolutions.cosmocalendar.settings.selection;

import com.applikeysolutions.cosmocalendar.utils.SelectionTypeDef;

public interface SelectionInterface {

    @SelectionTypeDef.SelectionType
    int getSelectionType();

    void setSelectionType(@SelectionTypeDef.SelectionType int selectionType);
}
