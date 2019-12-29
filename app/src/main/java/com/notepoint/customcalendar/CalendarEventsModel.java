package com.notepoint.customcalendar;

public class CalendarEventsModel {
   // @SerializedName("title")
    private String pa_application_title;
   // @SerializedName("start")
    private String start_date;
  //  @SerializedName("appl_no")
    private String application_number;
   // @SerializedName("atitle")
    private String app_title;
  //  @SerializedName("adate")
    private String applied_date;
   // @SerializedName("jno")
    private String job_number;
  //  @SerializedName("end")
    private String end_date;
   // @SerializedName("color")
    private String eventColor;


    public CalendarEventsModel(String pa_application_title, String start_date, String application_number,
                               String app_title, String applied_date, String job_number, String end_date) {
        this.pa_application_title = pa_application_title;
        this.start_date = start_date;
        this.application_number = application_number;
        this.app_title = app_title;
        this.applied_date = applied_date;
        this.job_number = job_number;
        this.end_date = end_date;
    }

    public CalendarEventsModel() {
    }

    public String getPa_application_title() {
        return pa_application_title;
    }

    public void setPa_application_title(String pa_application_title) {
        this.pa_application_title = pa_application_title;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getApplication_number() {
        return application_number;
    }

    public void setApplication_number(String application_number) {
        this.application_number = application_number;
    }

    public String getApp_title() {
        return app_title;
    }

    public void setApp_title(String app_title) {
        this.app_title = app_title;
    }

    public String getApplied_date() {
        return applied_date;
    }

    public void setApplied_date(String applied_date) {
        this.applied_date = applied_date;
    }

    public String getJob_number() {
        return job_number;
    }

    public void setJob_number(String job_number) {
        this.job_number = job_number;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getEventColor() {
        return eventColor;
    }

    public void setEventColor(String eventColor) {
        this.eventColor = eventColor;
    }
}
