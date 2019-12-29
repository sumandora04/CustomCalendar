package com.notepoint.customcalendar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CalenderEventAdapter extends RecyclerView .Adapter<CalenderEventAdapter.CalendarEventHolder> {

    private Context context;
    private ArrayList<CalendarEventsModel> calendarEventList;

    public CalenderEventAdapter(Context context, ArrayList<CalendarEventsModel> calendarEventList) {
        this.context = context;
        this.calendarEventList = calendarEventList;
        Log.d("calendarEventList", "" + calendarEventList);
    }


    @NonNull
    @Override
    public CalendarEventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.calendar_event_cell, parent, false);
        return new CalendarEventHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarEventHolder holder, int position) {
        Log.d("calendarEventList", "_bind_view:" + calendarEventList);
        holder.patApplicationTv.setText(calendarEventList.get(position).getPa_application_title());
        holder.appliedDateTv.setText(calendarEventList.get(position).getApplied_date());
        holder.jobNumTv.setText(calendarEventList.get(position).getJob_number());
        holder.applicationNameTv.setText(calendarEventList.get(position).getApp_title());
        holder.applicationNumTv.setText(calendarEventList.get(position).getApplication_number());

        switch (calendarEventList.get(position).getEventColor().trim()) {
            case "orange":
                holder.calendarEventLayout.setBackground(context.getResources().getDrawable(R.drawable.card_view_boarder_orange));
                break;

            case "blue":
                holder.calendarEventLayout.setBackground(context.getResources().getDrawable(R.drawable.border_blue));
                break;

            case "red":
                holder.calendarEventLayout.setBackground(context.getResources().getDrawable(R.drawable.border_red));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return calendarEventList.size();
    }

    public class CalendarEventHolder extends RecyclerView.ViewHolder {
        TextView patApplicationTv, appliedDateTv, jobNumTv, applicationNameTv, applicationNumTv;
        LinearLayout calendarEventLayout;

        public CalendarEventHolder(View itemView) {
            super(itemView);

//            patApplicationTv = itemView.findViewById(R.id.pat_application_tv);
//            appliedDateTv = itemView.findViewById(R.id.applied_date_tv);
//            jobNumTv = itemView.findViewById(R.id.job_num_tv);
//            applicationNameTv = itemView.findViewById(R.id.app_title_tv);
            applicationNumTv = itemView.findViewById(R.id.app_number_tv);
            calendarEventLayout = itemView.findViewById(R.id.layout_calendar_event);
        }
    }
}