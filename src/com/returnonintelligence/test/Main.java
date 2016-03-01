package com.returnonintelligence.test;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Masha on 26.02.2016.
 */
public class Main {
    public static void main(String args[]) throws IOException, ParseException, InterruptedException, URISyntaxException{
        //Task1
        /*ArrayDeque<File> queue = Queue.queue(); //the creation of a queue of files
        List<Thread> threads = new ArrayList<Thread>();
        MyRunnable runnable = new MyRunnable(queue);
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(runnable));
        }
        for (Thread thread: threads) {
            thread.start();
        }*/



        //Task2
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        UILogs mainFrame = new UILogs();
        mainFrame.setLocation(screenSize.width / 2 - mainFrame.getSize().width / 2,
                screenSize.height / 2 - mainFrame.getSize().height / 2);
        mainFrame.setVisible( true );
    }
}
