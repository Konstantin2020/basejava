package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("..\\basejava\\src\\com\\urise\\webapp");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        File frf = new File("..\\basejava\\src\\com\\urise\\webapp");
        System.out.println("Обработка в рекурсивном методе с курса");
        printDirectory(frf, "");


        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //реализация на курсе
    public static void printDirectory(File dir, String space) {
        System.out.println(space + dir.getName());
        File[] files = dir.listFiles();
        if (dir.isDirectory() && files != null) {
            for (File file : files) {
                printDirectory(file, space + "\u0020");
            }

        }
    }
}

