package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int size = 0;

    public void clear() {
        /*
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
        */
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        Resume resumeForUpdate = checkInStorage(resume.getUuid());
        int indexForUpdate = java.util.Arrays.asList(storage).indexOf(resumeForUpdate);
        if (resumeForUpdate != null) {
            System.out.println("Update обновляет резюме " + resume.getUuid() + ".");
            storage[indexForUpdate].setUuid(resume.getUuid());
        }
    }

    public void save(Resume resume) {
        if (size >= storage.length) {
            System.out.println("Хранилище переполнено, количество занятых ячеек массива" + size + " .");
        } else {
            if (checkInStorage(resume.getUuid()) == null) {
                System.out.println("Save сохраняет резюме " + resume.getUuid() + ".");
                storage[size] = resume;
                size++;
            }
        }
    }

    public Resume get(String uuid) {
        Resume resumeForGet = checkInStorage(uuid);
        return resumeForGet;
    }

    public void delete(String uuid) {
        Resume resumeForDelete = checkInStorage(uuid);
        int countForMove = java.util.Arrays.asList(storage).indexOf(resumeForDelete);
        if (resumeForDelete != null) {
            storage[countForMove] = null;
            System.out.println("Резюме " + uuid + " удалено!");
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
        System.arraycopy(storage, 0, allResume, 0, allResume.length);
        return allResume;
    }

    public int size() {
        return size;
    }

    public Resume checkInStorage(String uuid) {
        Resume resumeForCheck = null;
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                resumeForCheck = storage[i];
                break;
            }
        }
        if (resumeForCheck != null) {
            System.out.println("Резюме " + uuid + " в хранилище найдено!");
        } else {
            System.out.println("Резюме " + uuid + " в хранилище не найдено!");
        }
        return resumeForCheck;
    }
}

