package com.ancientlore.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.List;

public class CalendarAdapter extends BaseAdapter implements View.OnClickListener {
    private final Context context;
    private final int[] daysOfMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    private final String[] days;
    private final String[] months;
    private int currentDay;
    private int currentDayNum;

    private final List<String> list;

    public CalendarAdapter(Context context, int month, int year) {
        super();
        this.context = context;

        days = context.getResources().getStringArray(R.array.str_array_days);
        months = context.getResources().getStringArray(R.array.str_array_months);

        this.list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        setCurrentDayNum(calendar.get(Calendar.DAY_OF_MONTH));
        setCurrentDay(calendar.get(Calendar.DAY_OF_WEEK));

        printPage(month, year);
    }

    private void printPage(int month, int year) {
        int trailingSpaces;
        int daysInPrevMonth;
        int prevMonth;
        int prevYear;

        int currentMonth = month - 1;
        int daysInMonth = getNumberOfDaysOfMonth(currentMonth);

        GregorianCalendar cal = new GregorianCalendar(year, currentMonth, 1);

        if (currentMonth == 11) {
            prevMonth = currentMonth - 1;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            prevYear = year;
        } else if (currentMonth == 0) {
            prevMonth = 11;
            prevYear = year - 1;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
        } else {
            prevMonth = currentMonth - 1;
            prevYear = year;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
        }

        trailingSpaces = cal.get(Calendar.DAY_OF_WEEK) - 1;

        if (cal.isLeapYear(cal.get(Calendar.YEAR)))
            if (month == 2) daysInMonth++;
            else if (month == 3) daysInPrevMonth++;

        // Trailing Month days
        for (int i = 0; i < trailingSpaces; i++) {
            list.add(String.valueOf((daysInPrevMonth - trailingSpaces + 1) + i)
                     + "-" + getMonthAsString(prevMonth) + "-" + prevYear + "-Trailing month");
        }
        // Current Month Days
        for (int i = 1; i <= daysInMonth; i++) {
            if (i == getCurrentDayNum())
                list.add(String.valueOf(i) + "-" + getMonthAsString(currentMonth) + "-" + year + "-Selected day");
            else
                list.add(String.valueOf(i) + "-" + getMonthAsString(currentMonth) + "-" + year + "-Current month");
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.screen_gridcell, parent, false);
        }

        Button gridcell = (Button) row.findViewById(R.id.calendar_cell);
        gridcell.setOnClickListener(this);

        String[] day_color = list.get(position).split("-");
        String theday = day_color[0];
        String themonth = day_color[1];
        String theyear = day_color[2];

        gridcell.setText(theday);
        gridcell.setTag(theday + " " + themonth + " " + theyear);

        if (day_color[3].equals("Trailing month"))
            gridcell.setTextColor(context.getResources().getColor(R.color.date_inmonth));
        if (day_color[3].equals("Current month"))
            gridcell.setTextColor(context.getResources().getColor(R.color.date_outmonth));
        if (day_color[3].equals("Selected day"))
            gridcell.setTextColor(context.getResources().getColor(R.color.date_current));
        return row;
    }

    @Override
    public void onClick(View view) {
        String date_month_year = (String) view.getTag();
        MainActivity.textSelectedDate.setText(date_month_year);
    }
    //GETTERS
    @Override
    public int getCount() {return list.size();}
    @Override
    public long getItemId(int position) {return position;}
    private String getMonthAsString(int i) {return months[i];}
    private int getNumberOfDaysOfMonth(int i) {return daysOfMonth[i];}
    public String getItem(int position) {return list.get(position);}
    public int getCurrentDayNum() {return currentDayNum;}
    //SETTERS
    private void setCurrentDayNum(int currentDayNum) {this.currentDayNum = currentDayNum;}
    public void setCurrentDay(int currentDay) {this.currentDay = currentDay;}
}