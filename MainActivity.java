package com.ancientlore.calendar;

import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
    private Calendar calendar;
    private int currentMonth, currentYear;

    public static TextView textSelectedDate;
    public static TextView currentDate;
    private GridView gridViewCalendar;

    private CalendarAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendar = Calendar.getInstance(Locale.getDefault());
        currentMonth = calendar.get(Calendar.MONTH) + 1;
        currentYear = calendar.get(Calendar.YEAR);

        ImageButton buttonPrevMonth = (ImageButton) this.findViewById(R.id.button_prev);
        ImageButton buttonNextMonth = (ImageButton) this.findViewById(R.id.button_next);
        //ImageButton buttonEvents = (ImageButton) this.findViewById(R.id.imageButtonEvents);
        //ImageButton buttonAddEvent = (ImageButton) this.findViewById(R.id.imageButtonAddEvent);
        currentDate = (TextView) this.findViewById(R.id.textViewCurrentDate);
        textSelectedDate = (TextView) this.findViewById(R.id.textViewSelectedDate);
        gridViewCalendar = (GridView) this.findViewById(R.id.gridView_calendar);

        buttonPrevMonth.setOnClickListener(this);
        buttonNextMonth.setOnClickListener(this);
        currentDate.setText(DateFormat.format(getString(R.string.template_cur_date), calendar.getTime()));

        adapter = new CalendarAdapter(getApplicationContext(), currentMonth, currentYear);
        adapter.notifyDataSetChanged();
        gridViewCalendar.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_prev:
                if (currentMonth ==1){
                    currentYear--;
                    currentMonth =12;
                }else currentMonth--;
                break;
            case R.id.button_next:
                if (currentMonth ==12){
                    currentYear++;
                    currentMonth =1;
                }else currentMonth++;
                break;
        }
        onChangeSelectedDate(currentMonth, currentYear);
    }
    private void onChangeSelectedDate(int month, int year) {
        adapter = new CalendarAdapter(getApplicationContext(), month, year);
        calendar.set(year, month - 1, calendar.get(Calendar.DAY_OF_MONTH));
        currentDate.setText(DateFormat.format(getResources().getString(R.string.template_cur_date),
                calendar.getTime()));
        adapter.notifyDataSetChanged();
        gridViewCalendar.setAdapter(adapter);
    }


}
