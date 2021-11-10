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
        System.out.println("Обработка в рекурсивном методе");
        recurseFind(frf);


        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void recurseFind(File forRecurseFile) {
        if (forRecurseFile.isDirectory()) {
            String[] list = forRecurseFile.list();
            if (list != null) {
                for (String name : list) {
                    if (name.endsWith(".java")) {
                        System.out.println("Файл: " + name);
                    } else {
                        System.out.println("Папка: " + name);
                    }
                    recurseFind(new File(forRecurseFile + "\\" + name));
                }
            }
        }
    }
}
