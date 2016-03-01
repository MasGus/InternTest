package com.returnonintelligence.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Masha on 26.02.2016.
 */
public class WriteCSV {
    public static void write(File file, boolean append, String logLine) {
        try {
            FileWriter writer = new FileWriter(file, append);
            writer.write(logLine);
            writer.append('\n');
            writer.flush();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
