package com.bolowrc.tutoringmanager.model;


public class Lesson {

    private long id;
    private long studentId;
    private String date;
    private double hour;
    private double amount;
    private boolean paid;


    public long getId() {
        return id;
    }

    public String getTextId() {
        return Long.toString(id);
    }

    public long getStudentId() {
        return studentId;
    }

    public String getDate() {
        return date;
    }

    public double getHour() {
        return hour;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isPaid() {
        return paid;
    }
}
