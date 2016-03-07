package com.adamoverflow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) throws IOException {
        validateArgs(args);

        String configXml;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(args[0]));
        try {
            final StringBuilder stringBuilder = new StringBuilder();
            for (String line = bufferedReader.readLine(); !line.trim().startsWith("</app>"); line = bufferedReader.readLine()) {
                stringBuilder.append(line + "\n");
            }
            configXml = stringBuilder.toString();
        } finally {
            bufferedReader.close();
        }

        String structureXml;
        bufferedReader = new BufferedReader(new FileReader(args[1]));
        try {
            final StringBuilder stringBuilder = new StringBuilder();
            String line;
            for (line = bufferedReader.readLine(); !line.trim().startsWith("<struct "); line = bufferedReader.readLine()) {
                //skip to structure definitions
            }
            for (; line != null; line = bufferedReader.readLine()) {
                stringBuilder.append(line + "\n");
            }
            structureXml = stringBuilder.toString();
        } finally {
            bufferedReader.close();
        }

        File file = new File(args[2]);
        if (!file.exists() && !file.createNewFile()) {
            throw new RuntimeException("Could not create file that does not exist");
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
        try {
            bufferedWriter.write(configXml);
            bufferedWriter.write(structureXml);
        } finally {
            bufferedWriter.close();
        }
        System.out.print("Wrote to file: " + args[2]);
    }

    private static void validateArgs(String[] args) {
        if (args == null) {
            throw new RuntimeException("null args");
        }
        if (args.length != 3) {
            ArrayList<String> argsList = new ArrayList<>(args.length);
            Collections.addAll(argsList, args);
            final StringBuilder builder = new StringBuilder();
            argsList.forEach(a -> builder.append(a).append(" "));
            throw new RuntimeException("Invalid args format" + builder.toString());
        }
        System.out.println("Config From: " + args[0] + ", Structure From:" + args[1] + ", To: " + args[2]);
    }
}
