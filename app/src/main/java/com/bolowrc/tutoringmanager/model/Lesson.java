package com.bolowrc.tutoringmanager.model;


import com.bolowrc.tutoringmanager.util.DateUtil;

public class Lesson {

    private long id;
    private long studentId;
    private String date;
    private double hour;
    private double amount;
    private boolean paid;

    public Lesson(long studentId, String date, double amount, double hour, boolean paid) {
        this.studentId = studentId;
        this.date = date;
        this.amount = amount;
        this.hour = hour;
        this.paid = paid;
    }


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

    public String getPrintableDate() {
        return DateUtil.toPresentableDate(date);
    }


    public double getHour() {
        return hour;
    }

    public String getPrintableHour() {
        return Double.toString(hour);

    }


    public double getAmount() {
        return amount;
    }

    public String getPrintableAmount() {
        return Double.toString(amount);
    }

    public boolean isPaid() {
        return paid;
    }

    @Override
    public String toString() {
        return "Lezione: studente=" + studentId +
                ", data='" + date + '\'' +
                ", ore=" + hour +
                ", costo=" + amount +
                ", ha pagato?=" + paid;
    }

}
