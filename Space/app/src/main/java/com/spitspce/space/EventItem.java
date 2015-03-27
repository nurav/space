package com.spitspce.space;

/**
 * Created by Sumeet on 27-06-2014.
 */
public class EventItem {

    private int eventUID,eventImageID;
    private String eventName,eventIntro,eventContact,eventMail
            ,eventCategory,eventVenue,eventPrizes,eventRules,eventDate,eventTime;



    private boolean favourite;

    public EventItem(int eventUID,int eventImageID,String eventName
            ,String eventIntro,String eventContact,String eventMail,String eventCategory
            ,String eventVenue,String eventPrizes,String eventRules,String eventDate,String eventTime,boolean favourite){

        this.eventUID=eventUID;
        this.eventImageID=eventImageID;
        this.eventName=eventName;
        this.eventIntro=eventIntro;
        this.eventContact=eventContact;
        this.eventMail=eventMail;
        this.eventCategory=eventCategory;
        this.eventVenue=eventVenue;
        this.eventPrizes=eventPrizes;
        this.eventRules=eventRules;
        this.eventDate=eventDate;
        this.eventTime=eventTime;
        this.favourite = favourite;

    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;

    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public int getEventUID() {
        return eventUID;
    }

    public void setEventUID(int eventUID) {
        this.eventUID = eventUID;
    }

    public int getEventImageID() {
        return eventImageID;
    }

    public void setEventImageID(int eventImageID) {
        this.eventImageID = eventImageID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventIntro() {
        return eventIntro;
    }

    public void setEventIntro(String eventIntro) {
        this.eventIntro = eventIntro;
    }

    public String getEventContact() {
        return eventContact;
    }

    public void setEventContact(String eventContact) {
        this.eventContact = eventContact;
    }

    public String getEventMail() {
        return eventMail;
    }

    public void setEventMail(String eventMail) {
        this.eventMail = eventMail;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }

    public String getEventPrizes() {
        return eventPrizes;
    }

    public void setEventPrizes(String eventPrizes) {
        this.eventPrizes = eventPrizes;
    }

    public String getEventRules() {
        return eventRules;
    }

    public void setEventRules(String eventRules) {
        this.eventRules = eventRules;
    }
}
