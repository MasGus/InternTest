package com.returnonintelligence.test;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Masha on 01.03.2016.
 */
public class TableFromCSV {
    public static final int len = 4;

    public static List<List<String>> read (File file) throws IOException, ParseException {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        List<List<String>> list = new ArrayList<>();

        try {
            br = new BufferedReader(new FileReader(file));
            int j = 0;
            while ((line = br.readLine()) != null) {
                String[] log = line.split(cvsSplitBy);
                List<String> loglist = new ArrayList<>();
                for (int i = 0; i < len; i++){
                    loglist.add(i, log[i]);
                }
                list.add(0, loglist);
            }
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
        return list;
    }
}
