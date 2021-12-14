package com.example.finalapplication;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class SheduledItem implements Serializable{

    @Exclude private String id;
    String timeClass, eventNameClass, categoryClass, dateClass, descriptionClass;
    boolean isDoneClass;

    public SheduledItem(){

    }

    public SheduledItem(String timeClass, String eventNameClass, String categoryClass, String dateClass, String descriptionClass, boolean isDoneClass) {
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

    public String getEventNameClass() {

        return eventNameClass;
    }

    public void setTimeClass(String timeClass) {

        this.timeClass = timeClass;
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

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }
}
