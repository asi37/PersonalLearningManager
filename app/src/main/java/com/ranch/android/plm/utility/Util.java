package com.ranch.android.plm.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Asitha on 4/12/2017.
 */

public class Util {

    public static String formatDates(Date date){
        String convertedDate;
        convertedDate = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss").format(date);
        return convertedDate;
    }

    public static Date parseDates(String textDate) throws ParseException {
        Date convertedDate;
        convertedDate = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss").parse(textDate);
        return convertedDate;
    }
}
