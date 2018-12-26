package com.applikeysolutions.cosmocalendar.adapter.viewholder;

import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.applikeysolutions.cosmocalendar.settings.SettingsManager;
import com.applikeysolutions.customizablecalendar.R;
import com.applikeysolutions.cosmocalendar.adapter.DaysAdapter;
import com.applikeysolutions.cosmocalendar.model.Month;
import com.applikeysolutions.cosmocalendar.view.MonthView;

public class MonthHolder extends RecyclerView.ViewHolder {

    private LinearLayout llMonthHeader;
    private TextView tvMonthName;
    private View viewLeftLine;
    private View viewRightLine;
    private View viewHorizontalDivLine;
    private MonthView monthView;
    private SettingsManager appearanceModel;

    public MonthHolder(View itemView, SettingsManager appearanceModel) {
        super(itemView);
        llMonthHeader = (LinearLayout) itemView.findViewById(R.id.ll_month_header);
        monthView = (MonthView) itemView.findViewById(R.id.month_view);
        tvMonthName = (TextView) itemView.findViewById(R.id.tv_month_name);
        viewLeftLine = itemView.findViewById(R.id.view_left_line);
        viewRightLine = itemView.findViewById(R.id.view_right_line);
        viewHorizontalDivLine = itemView.findViewById(R.id.view_div_line);
        this.appearanceModel = appearanceModel;

        if(appearanceModel.getMonthTextAppearance() != -1) {
            tvMonthName.setTextAppearance(tvMonthName.getContext(), appearanceModel.getMonthTextAppearance());
        }
    }

    public void setDayAdapter(DaysAdapter adapter) {
        getMonthView().setAdapter(adapter);
    }

    public void bind(Month month) {
        tvMonthName.setText(month.getMonthName());
        tvMonthName.setTextColor(appearanceModel.getMonthTextColor());

        viewLeftLine.setBackgroundColor(appearanceModel.getBorderColor());
        viewRightLine.setBackgroundColor(appearanceModel.getBorderColor());
        viewHorizontalDivLine.setBackgroundColor(appearanceModel.getBorderColor());

        boolean isLineVisible = appearanceModel.getCalendarOrientation() == OrientationHelper.VERTICAL && appearanceModel.isMonthHorizontalLinesVisible();
        boolean isTitleDivVisible = appearanceModel.isMonthTitleBottomDivVisible();

        viewLeftLine.setVisibility(isLineVisible ? View.VISIBLE : View.INVISIBLE);
        viewRightLine.setVisibility(isLineVisible ? View.VISIBLE : View.INVISIBLE);
        viewHorizontalDivLine.setVisibility(isTitleDivVisible ? View.VISIBLE : View.GONE);

        llMonthHeader.setBackgroundResource(appearanceModel.getCalendarOrientation() == OrientationHelper.HORIZONTAL ? R.drawable.border_top_bottom : 0);

        monthView.initAdapter(month);
    }

    public MonthView getMonthView() {
        return monthView;
    }
}
