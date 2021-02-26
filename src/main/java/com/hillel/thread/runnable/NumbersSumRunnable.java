package com.hillel.thread.runnable;

import lombok.RequiredArgsConstructor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class NumbersSumRunnable implements Runnable {

    private final Object monitor;

    @Override
    public void run() {
        List<Long> sum = getSumFromFile();
        synchronized (monitor) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("out.txt", true))) {
                for (int i = 0; i < sum.size() - 1; i++) {
                    bufferedWriter.write(String.format("%s%d%s%d%s", "сума: ", i + 1, "-й десяток - ", sum.get(i), "\n"));
                }
                bufferedWriter.write(String.format("%s%d%s", "сума: остаток - ", sum.get(sum.size() - 1), "\n"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Long> getSumFromFile() {
        List<Long> sumNumbers = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("numbers.txt"))) {
            boolean isLine = true;
            String current;
            while (isLine) {
                long sum = 0;
                for (int i = 0; i < 10; i++) {
                    if ((current = bufferedReader.readLine()) != null && !current.isEmpty()) {
                        sum += Long.parseLong(current);
                    } else isLine = false;
                }
                sumNumbers.add(sum);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sumNumbers;
    }

}
