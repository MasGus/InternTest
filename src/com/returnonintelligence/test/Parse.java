package com.returnonintelligence.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

/**
 * Created by Masha on 28.02.2016.
 */
public class Parse {

    public static String dateFromUnixDate(Long unixDateLong){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = Date.from(Instant.ofEpochSecond(unixDateLong));
        return sdf.format(date).toUpperCase();
    }

    public static long unixDateFromDate (String dateString) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = sdf.parse(dateString);
        long unixDateLong = date.getTime();

        return unixDateLong/1000L;
    }

    public static TreeMap<String, String> treeMapForDate (String key, String valueString, TreeMap<String, String> treeMap){
        long value = Long.valueOf(valueString);
        if (treeMap.containsKey(key)){
            long oldvalue = Long.valueOf(treeMap.get(key));
            treeMap.put(key, String.valueOf((value + oldvalue) / 2));
        }
        else {
            treeMap.put(key, String.valueOf(value));
        }

        return treeMap;
    }

    public static String treeMapToString (TreeMap<String, String> treeMap){
        String s = "";
        for (HashMap.Entry entry : treeMap.entrySet()) {
            s += entry.getKey() + ", " + entry.getValue() + "\n";
        }
        return s;
    }
}
