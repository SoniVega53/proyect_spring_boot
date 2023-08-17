package com.example.demo;

import java.util.Calendar;
import java.util.Date;

public class UtilsTools {
    public static final String URL_ANGULAR = "http://localhost:4200/";

    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_TIME = "HH:mm";
    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm";


    public static final String STATUS_ACTIVE = "Activo";
    public static final String STATUS_CANCEL = "cancelado";
    public static final String STATUS_EXPIRATION = "expirado";

    public static Calendar getDateForCalendar(Date init){
        Calendar calInit = Calendar.getInstance();
        if (init != null)calInit.setTime(init);
        return calInit;
    }
}
