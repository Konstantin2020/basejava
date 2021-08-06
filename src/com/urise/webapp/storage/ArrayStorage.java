package com.urise.webapp.storage;


import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        /*
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
        */
        Arrays.fill(storage, null);
        size = 0;
    }

    public void update(Resume r) {
        if (get(r.getUuid()) != null) {
            System.out.println("Update обновляет резюме " + r.getUuid() + ".");
            r.setUuid(r.getUuid());
        }
    }

    public void save(Resume r) {
        if (size >= 10000) {
            System.out.println("Хранилище переполнено, количество занятых ячеек массива" + size + " .");
        } else {
            if (get(r.getUuid()) == null) {
                System.out.println("Save сохраняет резюме " + r.getUuid() + ".");
                storage[size] = r;
                size++;
            }
        }
    }

    public Resume get(String uuid) {
        Resume resumeForGet = null;
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                resumeForGet = storage[i];
                break;
            }
        }
        if (resumeForGet != null) {
            System.out.println("Резюме " + uuid + " в хранилище найдено!");
        } else {
            System.out.println("Резюме " + uuid + " в хранилище не найдено!");
        }
        return resumeForGet;
    }

    public void delete(String uuid) {
        int countForMove = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                storage[i] = null;
                countForMove = i;
                System.out.println("Резюме " + uuid + " в хранилище найдено и удалено!");
                break;
            }
        }
        if (countForMove == -1) {
            System.out.println("Резюме " + uuid + " в хранилище не найдено!");
        } else {
            for (int j = countForMove + 1; j < size; j++) {
                storage[j - 1] = storage[j];
            }
            storage[size - 1] = null;
            size--;
        }
    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] allResume = new Resume[size];
        for (int j = 0; j < allResume.length; j++) {
            allResume[j] = storage[j];
        }
        return allResume;
    }

    public int size() {
        return size;
    }
}