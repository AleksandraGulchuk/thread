package com.hillel.thread.runnable;

import lombok.RequiredArgsConstructor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FibonacciRunnable implements Runnable {

    private final Object monitor;

    @Override
    public void run() {
        List<Long> elements = getFibonacciElementsWithStep(15, 90);
        synchronized (monitor) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("out.txt", true))) {
                for (int i = 0; i < elements.size(); i++) {
                    bufferedWriter.write(String.format("%s%d%s%d%s", "фибоначи: ", (i + 1) * 15, "-й елемент - ", elements.get(i), "\n"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Long> getFibonacciElementsWithStep(int step, int limit) {
        List<Long> elements = new ArrayList<>();
        for (int i = step; i <= limit; i += step) {
            elements.add(getFibonacciElem(i));
        }
        return elements;
    }

    private long getFibonacciElem(int index) {
        if (index == 0) return 0;
        if (index == 1 || index == -1) return 1;
        long a = 0;
        long b = 1;
        long c = 1;
        if (index > 0) {
            for (int i = 2; i <= index; i++) {
                c = a + b;
                a = b;
                b = c;
            }
        } else {
            for (int i = -2; i >= index; i--) {
                c = a - b;
                a = b;
                b = c;
            }
        }
        return c;
    }

    public void printFibonacciNumbersInFile(int index) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("numbers.txt"))) {
            for (int i = 1; i <= index; i++) {
                bufferedWriter.write(getFibonacciElem(i) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
