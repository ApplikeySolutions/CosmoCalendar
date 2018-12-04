package com.applikeysolutions.cosmocalendar.adapter.viewholder;

import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.applikeysolutions.cosmocalendar.settings.SettingsManager;
import com.applikeysolutions.customizablecalendar.R;
import com.applikeysolutions.cosmocalendar.adapter.DaysAdapter;
import com.applikeysolutions.cosmocalendar.model.Month;
import com.applikeysolutions.cosmocalendar.view.MonthView;

public class MonthHolder extends RecyclerView.ViewHolder {

    private TextView tvMonthName;
    private MonthView monthView;
    private View monthHeaderBorder;
    private SettingsManager appearanceModel;

    public MonthHolder(View itemView, SettingsManager appearanceModel) {
        super(itemView);
        monthView = (MonthView) itemView.findViewById(R.id.month_view);
        tvMonthName = (TextView) itemView.findViewById(R.id.tv_month_name);
        monthHeaderBorder = itemView.findViewById(R.id.month_header_border);
        this.appearanceModel = appearanceModel;
    }

    public void setDayAdapter(DaysAdapter adapter) {
        getMonthView().setAdapter(adapter);
    }

    public void bind(Month month) {
        tvMonthName.setText(month.getMonthName());
        tvMonthName.setTextColor(appearanceModel.getMonthTextColor());
        TextViewCompat.setTextAppearance(tvMonthName, appearanceModel.getMonthTextAppearance());

        tvMonthName.setBackgroundResource(appearanceModel.getCalendarOrientation() == OrientationHelper.HORIZONTAL ? R.drawable.border_top_bottom : 0);
        monthHeaderBorder.setVisibility(appearanceModel.getCalendarOrientation() == OrientationHelper.HORIZONTAL ? View.GONE : View.VISIBLE);
        monthView.initAdapter(month);
    }

    public MonthView getMonthView() {
        return monthView;
    }
}
