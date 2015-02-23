package com.bolowrc.tutoringmanager.util;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat presentDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static String toDateString(Calendar date) {
        return simpleDateFormat.format(date.getTime());
    }

    public static String toPresentableDate(String date) {
        try {
            Date dateValue = simpleDateFormat.parse(date);
            return presentDateFormat.format(dateValue);
        } catch (Exception e) {
            return "";
        }
    }
}
