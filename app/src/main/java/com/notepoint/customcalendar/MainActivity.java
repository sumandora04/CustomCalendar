package com.notepoint.customcalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public GregorianCalendar cal_month, cal_month_copy;
    private TextView tv_month, noEventTv;
    private ImageView colorConventionInfo;
    private CalendarGridAdapter calendarGridAdapter;

    ArrayList<CalendarEventsModel> calendarEventsModelArrayList;
    private RecyclerView calendarEventRecView;
    GridView gridview;
    ImageView previous, next;
    TextView todayDateTv;
    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void initialiseViews() {
        tv_month = findViewById(R.id.tv_month);
        previous = findViewById(R.id.month_previous);
        next = findViewById(R.id.month_next);
        gridview = findViewById(R.id.gv_calendar);
        calendarEventRecView = findViewById(R.id.recycler_view_calendar_detail);
        todayDateTv = findViewById(R.id.today_event_tv);
        noEventTv = findViewById(R.id.no_event_tv);
//        colorConventionInfo = findViewById(R.id.color_convention_info);

        cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
        cal_month_copy = (GregorianCalendar) cal_month.clone();


        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));

        //For showing today's date
        Date date = new Date();
        String modifiedDate = format2.format(date);
        todayDateTv.setText(modifiedDate);

        calendarEventsModelArrayList = new ArrayList<>();

        getCalendarEventDetails();

        previous.setOnClickListener(this);
        next.setOnClickListener(this);
        colorConventionInfo.setOnClickListener(this);

    }

    private void getCalendarEventDetails() {

    }

    protected void setNextMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month.getActualMaximum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1), cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH,
                    cal_month.get(GregorianCalendar.MONTH) + 1);
        }
    }

    protected void setPreviousMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month.getActualMinimum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1), cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH, cal_month.get(GregorianCalendar.MONTH) - 1);
        }
    }

    public void refreshCalendar() {
        calendarGridAdapter.refreshDays();
        calendarGridAdapter.notifyDataSetChanged();
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.month_previous:
                setPreviousMonth();
                refreshCalendar();
                break;

            case R.id.month_next:
                setNextMonth();
                refreshCalendar();
                break;

//            case R.id.color_convention_info:
//                //show dialog here.
//                final Dialog dialog = new Dialog(this);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.calendar_event_color_convention_dialog);
//
//                DisplayMetrics metrics = this.getResources().getDisplayMetrics();
//                int width = metrics.widthPixels;
//                Objects.requireNonNull(dialog.getWindow()).setLayout((6 * width) / 7, RelativeLayout.LayoutParams.WRAP_CONTENT);
//
//                dialog.show();
//
//                Button ok_btn;
//
//                ok_btn = dialog.findViewById(R.id.color_conv_ok);
//                ok_btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.dismiss();
//                    }
//                });
//
//                break;
        }
    }

}
