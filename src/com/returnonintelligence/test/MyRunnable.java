package com.returnonintelligence.test;

import java.io.File;
import java.util.ArrayDeque;

/**
 * Created by Masha on 28.02.2016.
 */
public class MyRunnable implements Runnable {
    private ArrayDeque<File> queue;

    public MyRunnable(ArrayDeque<File> queue) {
        super();
        this.queue = queue;
    }

    @Override
    public void run() {
        File data = null;
        while(!queue.isEmpty()) {
            synchronized (queue) {
                data = queue.pollFirst();
            }
            try {
                ReadCSV.read(data);
            } catch (Exception ex) {
            }

        }
    }
}