package ru.pfr.contracts2.global;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ConverterDate {

    //дату в строку
    public static String datetostring_yyyyMMdd(Date dat) { //американский формат
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat1.format(dat);
    }

    public static String datetostring_ddMMyyyy(Date dat) { //русский формат
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat1.format(dat);
    }

    //Строку в дату
    public static Date stringToDate(String dat) {
        DateFormat format;
        Date date = new Date();
        format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(dat);
        } catch (ParseException e) {
            format = new SimpleDateFormat("dd.MM.yyyy");
            try {
                date = format.parse(dat);
            } catch (ParseException parseException) {
                return null;
            }
        }
        return date;
    }

    //прибавить к дате
    public static Date plusDay(Date date, int kol) {//день
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,kol);
        return calendar.getTime();
    }

    public static Date plusMonth(Date date, int kol) {//месяц
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,kol);
        return calendar.getTime();
    }

    public static Date plusYear(Date date, int kol) {//год
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR,kol);
        return calendar.getTime();
    }

    public static int differenceInDays(Date minuend, Date subtrahend ){
        long milliseconds = minuend.getTime() - subtrahend.getTime();
        return (int) (milliseconds / (24 * 60 * 60 * 1000));
    }

}
