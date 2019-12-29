package com.notepoint.customcalendar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class CalendarGridAdapter extends BaseAdapter {

    private Activity context;

    private java.util.Calendar month;
    private GregorianCalendar pmonth;

    // calendar instance for previous month for getting complete view

    private GregorianCalendar pmonthmaxset;
    private GregorianCalendar selectedDate;
    private int firstDay;
    private int maxWeeknumber;
    private int maxP;
    private int calMaxP;
    private int mnthlength;
    private String itemvalue, currentDateString, todayDate;
    private DateFormat df;

    private ArrayList<String> items;
    static List<String> day_string;
    private ArrayList<CalendarEventsModel> date_collection_arr;
    private String gridvalue;
    private RecyclerView eventRecyclerView;
    private TextView noEventTv;
    private ArrayList<CalendarEventsModel> eventsModel;

    public CalendarGridAdapter(Activity context, GregorianCalendar monthCalendar, ArrayList<CalendarEventsModel> date_collection_arr, RecyclerView eventRecyclerView, TextView noEventTv) {
        this.date_collection_arr = date_collection_arr;
        CalendarGridAdapter.day_string = new ArrayList<String>();
        Locale.setDefault(Locale.US);
        month = monthCalendar;
        selectedDate = (GregorianCalendar) monthCalendar.clone();
        this.context = context;
        month.set(GregorianCalendar.DAY_OF_MONTH, 1);

        this.items = new ArrayList<String>();
        Date date = new Date();
        df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        currentDateString = df.format(selectedDate.getTime());
        this.eventRecyclerView = eventRecyclerView;
        this.noEventTv = noEventTv;
        this.todayDate = df.format(date);
        refreshDays();

    }

    public int getCount() {
        return day_string.size();
    }

    public Object getItem(int position) {
        final SimpleDateFormat dformate = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        return dformate.format(day_string.get(position));
    }

    public long getItemId(int position) {
        return 0;
    }


    // create a new view for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (convertView == null) { // if it's not recycled, initialize some attributes
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (layoutInflater != null) {
                v = layoutInflater.inflate(R.layout.calendar_item, null);
            }

        }
        TextView dayView;
        dayView = v.findViewById(R.id.date);

        String[] separatedTime = day_string.get(position).split("-");


        gridvalue = separatedTime[2].replaceFirst("^0*", "");// removing leading zero
        if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
            dayView.setTextColor(Color.parseColor("#aaaaaa"));
            dayView.setClickable(false);
            dayView.setFocusable(false);
        } else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {
            dayView.setTextColor(Color.parseColor("#aaaaaa"));
            dayView.setClickable(false);
            dayView.setFocusable(false);
        } else {
            // setting curent month's days in blue color.
            dayView.setTextColor(Color.parseColor("#000000"));
            //dayView.setBackgroundResource(R.drawable.circle_blue);
        }


        if (day_string.get(position).equals(currentDateString)) {

            v.setBackgroundColor(Color.parseColor("#ffffff"));
            //  dayView.setTextColor(context.getResources().getColor(R.color.green));
            // dayView.setBackgroundResource(R.drawable.border);
        } else {
            v.setBackgroundColor(Color.parseColor("#ffffff"));
            //dayView.setBackgroundResource(R.drawable.border);
            // dayView.setTextColor(context.getResources().getColor(R.color.yellow));
        }


        dayView.setText(gridvalue);

        // create date string for comparison
        String date = day_string.get(position);

        if (date.length() == 1) {
            date = "0" + date;
        }
        String monthStr = "" + (month.get(GregorianCalendar.MONTH) + 1);
        if (monthStr.length() == 1) {
            monthStr = "0" + monthStr;
        }

        setEventView(v, position, dayView);

        return v;
    }

    public void notifyCalendarEventDataList(ArrayList<CalendarEventsModel> eventList) {
        Log.d("notifyCalendarEventList", "" + eventList.size());
        this.date_collection_arr = eventList;
        // day_string.clear();
        notifyDataSetChanged();
    }

    public void refreshDays() {
        // clear items
        items.clear();
        day_string.clear();
        Locale.setDefault(Locale.US);
        pmonth = (GregorianCalendar) month.clone();
        // month start day. ie; sun, mon, etc
        firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);
        // finding number of weeks in current month.
        maxWeeknumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
        // allocating maximum row number for the gridview.
        mnthlength = maxWeeknumber * 7;
        maxP = getMaxP(); // previous month maximum day 31,30....
        calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...
        pmonthmaxset = (GregorianCalendar) pmonth.clone();

        pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);


        for (int n = 0; n < mnthlength; n++) {

            itemvalue = df.format(pmonthmaxset.getTime());
            pmonthmaxset.add(GregorianCalendar.DATE, 1);
            day_string.add(itemvalue);

        }
    }

    private int getMaxP() {
        int maxP;
        if (month.get(GregorianCalendar.MONTH) == month
                .getActualMinimum(GregorianCalendar.MONTH)) {
            pmonth.set((month.get(GregorianCalendar.YEAR) - 1),
                    month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            pmonth.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) - 1);
        }
        maxP = pmonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

        return maxP;
    }


    public void setEventView(View v, int pos, TextView txt) {

        int len = date_collection_arr.size();
        for (int i = 0; i < len; i++) {
            String date = date_collection_arr.get(i).getStart_date();

            int len1 = day_string.size();
            if (len1 > pos) {

                // Making today bold and bigger even when there is no event.
                if (day_string.get(pos).equals(todayDate)) {
                    txt.setText(gridvalue);
                    txt.setTypeface(null, Typeface.BOLD);
                    txt.setTextColor(context.getResources().getColor(R.color.colorAccent));
                    txt.setTextSize(20);
                    break;
                }

                if (day_string.get(pos).equals(date)) {

                    if (((Integer.parseInt(gridvalue) <= 1) || (pos >= firstDay)) && ((Integer.parseInt(gridvalue) >= 7) || (pos <= 28))) {
                        txt.setTextColor(Color.parseColor("#ffffff"));
                        // txt.setBackgroundResource(R.drawable.circle_blue);
                        Log.d("events_color:", "" + date_collection_arr.get(i).getEventColor().trim());
                        switch (date_collection_arr.get(i).getEventColor().trim()) {
                            case "orange":
                                txt.setBackgroundResource(R.drawable.circle_orange);
                                break;

                            case "blue":
                                txt.setBackgroundResource(R.drawable.circle_blue);
                                break;

                            case "red":
                                txt.setBackgroundResource(R.drawable.circle_red);
                                break;

                            default:
                                txt.setBackgroundResource(R.drawable.circle_blue);
                        }


                        break;
                    }

                } else {
                    if (((Integer.parseInt(gridvalue) <= 1) || (pos >= firstDay)) && ((Integer.parseInt(gridvalue) >= 7) || (pos <= 28))) {
                        txt.setTextColor(Color.parseColor("#000000"));
                        txt.setBackgroundResource(0);
                        txt.setTypeface(null, Typeface.NORMAL);
                        txt.setTextSize(12);
                        //  txt.setPaintFlags(txt.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
                    } else {
                        txt.setTextColor(Color.parseColor("#aaaaaa"));
                        txt.setBackgroundResource(0);
                        txt.setTypeface(null, Typeface.NORMAL);
                        txt.setTextSize(12);
                        // txt.setPaintFlags(txt.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
                    }
                }
            }
        }
    }

    public void getPositionList(String date) {

        int len = date_collection_arr.size();
        JSONArray jbarrays = new JSONArray();
        for (int j = 0; j < len; j++) {
            if (date_collection_arr.get(j).getStart_date().equals(date)) {
                HashMap<String, String> maplist = new HashMap<String, String>();
                maplist.put("pa_application_title", date_collection_arr.get(j).getPa_application_title());
                maplist.put("app_title", date_collection_arr.get(j).getApp_title());
                maplist.put("application_number", date_collection_arr.get(j).getApplication_number());
                maplist.put("applied_date", date_collection_arr.get(j).getApplied_date());
                maplist.put("job_number", date_collection_arr.get(j).getJob_number());
                maplist.put("start_date", date_collection_arr.get(j).getStart_date());
                maplist.put("end_date", date_collection_arr.get(j).getEnd_date());
                maplist.put("event_color", date_collection_arr.get(j).getEventColor());
                JSONObject json1 = new JSONObject(maplist);
                jbarrays.put(json1);
            }
        }
        if (jbarrays.length() != 0) {
            noEventTv.setVisibility(View.GONE);
            eventRecyclerView.setVisibility(View.VISIBLE);

            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            eventRecyclerView.setLayoutManager(layoutManager);
            eventRecyclerView.setAdapter(new CalenderEventAdapter(context, getMatchList(jbarrays + "")));


        } else {
            eventRecyclerView.setVisibility(View.GONE);
            noEventTv.setVisibility(View.VISIBLE);
            // Toast.makeText(context, "No events today", Toast.LENGTH_SHORT).show();
        }

    }

    private ArrayList<CalendarEventsModel> getMatchList(String detail) {
        try {
            JSONArray jsonArray = new JSONArray(detail);
            eventsModel = new ArrayList<CalendarEventsModel>();
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.optJSONObject(i);

                CalendarEventsModel pojo = new CalendarEventsModel();

                pojo.setPa_application_title(jsonObject.optString("pa_application_title"));
                pojo.setApp_title(jsonObject.optString("app_title"));
                pojo.setApplication_number(jsonObject.optString("application_number"));
                pojo.setApplied_date(jsonObject.optString("applied_date"));
                pojo.setJob_number(jsonObject.optString("job_number"));
                pojo.setStart_date(jsonObject.optString("start_date"));
                pojo.setEnd_date(jsonObject.optString("end_date"));
                pojo.setEventColor(jsonObject.optString("event_color"));

                eventsModel.add(pojo);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return eventsModel;
    }
}
