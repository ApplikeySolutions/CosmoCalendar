# CosmoCalendar
* [Usage](#usage)
* [Customization](#customization)
   * [Common](#common)
   * [Selection](#selection)
   * [Current day](#current-day)
   * [Navigation buttons](#navigation-buttons)
   * [Weekend days](#weekend-days)
   * [Connected days](#connected-days)
   * [Disabled days](#disabled-days)
   * [Calendar dialog](#calendar-dialog)
* [License](#license)

# Usage

# Customization

  ### Common
  * calendarOrientation - Possible values: HORIZONTAL, VERTICAL
  * calendarBackgroundColor
  * monthTextColor
  * otherDayTextColor
  * dayTextColor
  * firstDayOfTheWeek
  * weekDayTitleTextColo
  * showDaysOfWeek - Defines if we need to display week day titles for every month
  * showDaysOfWeekTitle - Defines if we need to display week day title for whole calendar
  
  ### Selection
  * selectionType - Possible values: SINGLE, MULTIPLE, RANGE
  * selectedDayTextColor
  * selectedDayBackgroundColor
  * selectedDayBackgroundStartColor - Background color of START day from selected range
  * selectedDayBackgroundEndColor - Background color of END day from selected range
  * selectionBarMonthTextColor
  
  ### Current day
  * currentDayTextColor
  * currentDayIconRes
  * currentDaySelectedIconRes
  
  ### Navigation buttons
  * previousMonthIconRes
  * nextMonthIconRes
  
  ### Weekend days
  * weekendDays
  ```java
  calendarView.setWeekendDays(new HashSet(){{
            add(Calendar.THURSDAY);
            add(Calendar.TUESDAY);
  }});
  ```
  
  * weekendDayTextColor
  
  ### Connected days
  You can add some days for example holidays:
  ```java
  Set<Long> connectedDaysSet = new HashSet<>();
  connectedDaysSet.add(System.currentTimeMillis());
  calendarView.setConnectedCalendarDays(connectedDaysSet);
  ```
  and customize them:
  * connectedDayTextColor
  * connectedDaySelectedTextColor
  
  ### Disabled days
  You can add days so that you can not select them:
  ```java
  Set<Long> disabledDaysSet = new HashSet<>();
  disabledDaysSet.add(System.currentTimeMillis());
  calendarView.setDisabledDays(disabledDaysSet);
  ```
  * disabledDayTextColor - Text color of disabled day

  ### Calendar dialog
   ```java
   new CalendarDialog(this, new OnDaysSelectionListener() {
            @Override
            public void onDaysSelected(List<Day> selectedDays) {
                
            }
        }).show();
   ```

# License

    Copyright 2017, Applikey Solutions

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
