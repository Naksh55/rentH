package com.naksh.renth.Models;

public class UserTripDetailsModel {
    private String fromDate;
    private int slots,guests;

    public UserTripDetailsModel() {
    }

    public UserTripDetailsModel(String fromDate, int slots, int guests) {
        this.fromDate = fromDate;
        this.slots = slots;
        this.guests = guests;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }
}
