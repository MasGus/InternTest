package com.returnonintelligence.test;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Scanner;

/**
 * Created by Masha on 26.02.2016.
 */
public class Queue {
    public static ArrayDeque<File> queue() {
        System.out.println("Please write the path of a folder");
        try{
            Scanner in = new Scanner(System.in);
            String path = in.next();
            File file = new File(path);
            ArrayDeque<File> queue = new ArrayDeque<File>();
            filesQueue(file, queue);
            return queue;
        }
        catch(Exception e){
            System.out.println("The path is incorrect");

            return null;
        }
    }

    public static ArrayDeque<File> filesQueue(File f,  ArrayDeque<File> queue) {
        if (f.isDirectory()) {
            for (File file : f.listFiles()) {
                filesQueue(file, queue);
            }
        } else {
            queue.addLast(f);
        }

        return queue;
    }
}
