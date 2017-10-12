package com.applikeysolutions.cosmocalendar.sample;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.applikeysolutions.cosmocalendar.selection.MultipleSelectionManager;
import com.applikeysolutions.cosmocalendar.selection.criteria.BaseCriteria;
import com.applikeysolutions.cosmocalendar.selection.criteria.WeekDayCriteria;
import com.applikeysolutions.cosmocalendar.selection.criteria.month.CurrentMonthCriteria;
import com.applikeysolutions.cosmocalendar.selection.criteria.month.NextMonthCriteria;
import com.applikeysolutions.cosmocalendar.selection.criteria.month.PreviousMonthCriteria;
import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.applikeysolutions.cosmocalendar.view.CalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DefaultCalendarActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private CalendarView calendarView;

    private List<BaseCriteria> threeMonthsCriteriaList;
    private WeekDayCriteria fridayCriteria;

    private boolean fridayCriteriaEnabled;
    private boolean threeMonthsCriteriaEnabled;

    private MenuItem menuFridays;
    private MenuItem menuThreeMonth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_calendar);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        initViews();
        createCriterias();
    }

    private void initViews() {
        calendarView = (CalendarView) findViewById(R.id.calendar_view);
        ((RadioGroup) findViewById(R.id.rg_orientation)).setOnCheckedChangeListener(this);
        ((RadioGroup) findViewById(R.id.rg_selection_type)).setOnCheckedChangeListener(this);
    }

    private void createCriterias() {
        fridayCriteria = new WeekDayCriteria(Calendar.FRIDAY);

        threeMonthsCriteriaList = new ArrayList<>();
        threeMonthsCriteriaList.add(new CurrentMonthCriteria());
        threeMonthsCriteriaList.add(new NextMonthCriteria());
        threeMonthsCriteriaList.add(new PreviousMonthCriteria());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_default_calendar_activity, menu);
        menuFridays = menu.findItem(R.id.select_all_fridays);
        menuThreeMonth = menu.findItem(R.id.select_three_months);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.select_all_fridays:
                fridayMenuClick();
                return true;

            case R.id.select_three_months:
                threeMonthsMenuClick();
                return true;

            case R.id.clear_selections:
                clearSelectionsMenuClick();
                return true;

            case R.id.log_selected_days:
                logSelectedDaysMenuClick();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void fridayMenuClick() {
        if (fridayCriteriaEnabled) {
            menuFridays.setTitle(getString(R.string.select_all_fridays));
            unselectAllFridays();
        } else {
            menuFridays.setTitle(getString(R.string.unselect_all_fridays));
            selectAllFridays();
        }
        fridayCriteriaEnabled = !fridayCriteriaEnabled;
    }

    private void threeMonthsMenuClick() {
        if (threeMonthsCriteriaEnabled) {
            menuThreeMonth.setTitle(getString(R.string.select_three_months));
            unselectThreeMonths();
        } else {
            menuThreeMonth.setTitle(getString(R.string.unselect_three_months));
            selectThreeMonths();
        }
        threeMonthsCriteriaEnabled = !threeMonthsCriteriaEnabled;
    }

    private void selectAllFridays() {
        if (calendarView.getSelectionManager() instanceof MultipleSelectionManager) {
            ((MultipleSelectionManager) calendarView.getSelectionManager()).addCriteria(fridayCriteria);
        }
        calendarView.update();
    }

    private void unselectAllFridays() {
        if (calendarView.getSelectionManager() instanceof MultipleSelectionManager) {
            ((MultipleSelectionManager) calendarView.getSelectionManager()).removeCriteria(fridayCriteria);
        }
        calendarView.update();
    }

    private void selectThreeMonths() {
        if (calendarView.getSelectionManager() instanceof MultipleSelectionManager) {
            ((MultipleSelectionManager) calendarView.getSelectionManager()).addCriteriaList(threeMonthsCriteriaList);
        }
        calendarView.update();
    }

    private void unselectThreeMonths() {
        if (calendarView.getSelectionManager() instanceof MultipleSelectionManager) {
            ((MultipleSelectionManager) calendarView.getSelectionManager()).removeCriteriaList(threeMonthsCriteriaList);
        }
        calendarView.update();
    }

    private void clearSelectionsMenuClick() {
        calendarView.clearSelections();

        fridayCriteriaEnabled = false;
        threeMonthsCriteriaEnabled = false;
        menuFridays.setTitle(getString(R.string.select_all_fridays));
        menuThreeMonth.setTitle(getString(R.string.select_three_months));
    }


    private void logSelectedDaysMenuClick() {
        Toast.makeText(this, "Selected " + calendarView.getSelectedDays().size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        clearSelectionsMenuClick();
        switch (checkedId) {
            case R.id.rb_horizontal:
                calendarView.setCalendarOrientation(OrientationHelper.HORIZONTAL);
                break;

            case R.id.rb_vertical:
                calendarView.setCalendarOrientation(OrientationHelper.VERTICAL);
                break;

            case R.id.rb_single:
                calendarView.setSelectionType(SelectionType.SINGLE);
                menuFridays.setVisible(false);
                menuThreeMonth.setVisible(false);
                break;

            case R.id.rb_multiple:
                calendarView.setSelectionType(SelectionType.MULTIPLE);
                menuFridays.setVisible(true);
                menuThreeMonth.setVisible(true);
                break;

            case R.id.rb_range:
                calendarView.setSelectionType(SelectionType.RANGE);
                menuFridays.setVisible(false);
                menuThreeMonth.setVisible(false);
                break;

            case R.id.rb_none:
                calendarView.setSelectionType(SelectionType.NONE);
                menuFridays.setVisible(false);
                menuThreeMonth.setVisible(false);
                break;
        }
    }
}

