package com.hillel.thread;

import com.hillel.thread.runnable.FibonacciRunnable;
import com.hillel.thread.runnable.NumbersSumRunnable;
import com.hillel.thread.runnable.PhonesRunnable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class Main {

    public static void main(String[] args) {

        Object monitor = new Object();


        Thread phonesThread = new Thread(new PhonesRunnable(monitor));
        Thread sumThread = new Thread(new NumbersSumRunnable(monitor));
        Thread fibonacciThread = new Thread(new FibonacciRunnable(monitor));

        phonesThread.start();
        fibonacciThread.start();
        sumThread.start();

        try {
            fibonacciThread.join();
            sumThread.join();
            phonesThread.join();

            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("out.txt", true))) {
                bufferedWriter.write("Все операции завершены\n");
            }
            System.out.println("Все операции завершены");
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

    }

}


