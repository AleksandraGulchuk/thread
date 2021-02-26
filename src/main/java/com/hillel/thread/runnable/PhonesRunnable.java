package com.hillel.thread.runnable;

import lombok.RequiredArgsConstructor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class PhonesRunnable implements Runnable {

    private final Object monitor;

    @Override
    public void run() {
        List<String> phones = getPhonesFromFile();
        synchronized (monitor) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("out.txt", true))) {
                for (String phone : phones) {
                    bufferedWriter.write(phone + ", ");
                }
                bufferedWriter.write("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<String> getPhonesFromFile() {
        List<String> phones = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("test.txt"))) {
            String current;
            Pattern pattern = Pattern.compile("\\+380\\d{9}");
            while ((current = bufferedReader.readLine()) != null) {
                Matcher matcher = pattern.matcher(current);
                while (matcher.find()) {
                    phones.add(matcher.group());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return phones;
    }

}
