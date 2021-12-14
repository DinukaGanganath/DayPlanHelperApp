package com.example.finalapplication;

public class HistoryEvent {

    String timeClass, eventNameClass, categoryClass, dateClass, descriptionClass;
    boolean isDoneClass;

    public HistoryEvent(){

    }

    public HistoryEvent(String timeClass, String eventNameClass, String categoryClass, String dateClass, String descriptionClass, boolean isDoneClass) {
        this.timeClass = timeClass;
        this.eventNameClass = eventNameClass;
        this.categoryClass = categoryClass;
        this.dateClass = dateClass;
        this.descriptionClass = descriptionClass;
        this.isDoneClass = isDoneClass;
    }

    public String getTimeClass() {
        return timeClass;
    }

    public void setTimeClass(String timeClass) {
        this.timeClass = timeClass;
    }

    public String getEventNameClass() {
        return eventNameClass;
    }

    public void setEventNameClass(String eventNameClass) {
        this.eventNameClass = eventNameClass;
    }

    public String getCategoryClass() {
        return categoryClass;
    }

    public void setCategoryClass(String categoryClass) {
        this.categoryClass = categoryClass;
    }

    public String getDateClass() {
        return dateClass;
    }

    public void setDateClass(String dateClass) {
        this.dateClass = dateClass;
    }

    public String getDescriptionClass() {
        return descriptionClass;
    }

    public void setDescriptionClass(String descriptionClass) {
        this.descriptionClass = descriptionClass;
    }

    public boolean isDoneClass() {
        return isDoneClass;
    }

    public void setDoneClass(boolean doneClass) {
        isDoneClass = doneClass;
    }




}
