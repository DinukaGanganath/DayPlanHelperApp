package com.example.finalapplication;

public class EventClass {

    private String EventNameClass, DescriptionClass, CategoryClass, DateClass, TimeClass;
    private boolean isDoneClass;

    public EventClass(){

    }

    public EventClass(String EventNameClass, String TimeClass, String DateClass, String CategoryClass, String DescriptionClass, boolean isDoneClass){
        this.EventNameClass = EventNameClass;
        this.DescriptionClass = DescriptionClass;
        this.DateClass = DateClass;
        this.TimeClass = TimeClass;
        this.CategoryClass = CategoryClass;
        this.isDoneClass = isDoneClass;
    }

    public String getEventNameClass() {
        return EventNameClass;
    }

    public String getDescriptionClass() {
        return DescriptionClass;
    }

    public String getCategoryClass() {
        return CategoryClass;
    }

    public String getDateClass() {
        return DateClass;
    }

    public String getTimeClass() {
        return TimeClass;
    }

    public boolean getIsDoneClass() {
        return isDoneClass;
    }
}
