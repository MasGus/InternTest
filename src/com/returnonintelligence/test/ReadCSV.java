package com.returnonintelligence.test;

import java.io.*;
import java.text.ParseException;
import java.util.TreeMap;

/**
 * Created by Masha on 26.02.2016.
 */
public class ReadCSV {
    public static final int len = 4;

    public static void read (File file) throws IOException, ParseException{
        File newFile = new File(file.getParent()  + "/avg_"+ file.getName());
        if(!newFile.exists()){
            newFile.createNewFile();
        }
        else {
            newFile.delete();
            newFile.createNewFile();
        }

        BufferedReader br = null;
        String line = "";
        String newLine = "";
        String cvsSplitBy = ",";
        TreeMap<String, String> treeMapForOneDate = new TreeMap<String, String>();
        long oldUnixDateLong = 0;

        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                String[] log = line.split(cvsSplitBy);
                long unixDateLong = Long.parseLong(log[0]);
                long secNumber = Long.parseLong(log[len-1]);
                long sum = unixDateLong + secNumber;
                String endDateOfSession = Parse.dateFromUnixDate(sum);
                String date = Parse.dateFromUnixDate(unixDateLong);
                String oldDate = Parse.dateFromUnixDate(oldUnixDateLong);

                if (Parse.dateFromUnixDate(oldUnixDateLong).equals(endDateOfSession)){
                    Parse.treeMapForDate(log[1] + ", " + log[2], log[3], treeMapForOneDate);
                }
                else if(!endDateOfSession.equals(date) && oldDate.equals(date)){
                    long secDay1 = Parse.unixDateFromDate(endDateOfSession) - unixDateLong;
                    long secDay2 = secNumber - secDay1;

                    Parse.treeMapForDate(log[1] + ", " + log[2], String.valueOf(secDay1), treeMapForOneDate);
                    newLine += Parse.treeMapToString(treeMapForOneDate);
                    treeMapForOneDate.clear();

                    newLine += "\n" + endDateOfSession + "\n\n";
                    Parse.treeMapForDate(log[1] + ", " + log[2], String.valueOf(secDay2), treeMapForOneDate);
                }

                else{
                    newLine += Parse.treeMapToString(treeMapForOneDate);
                    newLine += "\n" + date + "\n\n";
                    treeMapForOneDate.clear();
                    Parse.treeMapForDate(log[1] + ", " + log[2], log[3], treeMapForOneDate);
                }

                oldUnixDateLong = unixDateLong + secNumber;
            }
            if (!treeMapForOneDate.isEmpty()){
                newLine += Parse.treeMapToString(treeMapForOneDate);
            }
            WriteCSV.write(newFile, true, newLine);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
