package com.Telgram.Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class myDate {
    private static DateFormat dateFormat;
    private static Date date;

    public static String getDate() {
        dateFormat = new SimpleDateFormat("MM/dd HH:mm:ss");
        date = new Date();
        return dateFormat.format(date);
    }
}
